//Firas Aboushamalah
//250920750
//Board Game class implements all the algorithms needed by the other classes to play the game.

public class BoardGame {
	
	//instance variable
	private char[][] gameBoard;
	private int board_size, empty_positions, max_levels;

	//constructor for BoardGame class
	public BoardGame(int board_size, int empty_positions, int max_levels) {
		this.board_size = board_size;
		this.gameBoard = new char[board_size][board_size];  //creating the board gamee object as specified by size
		this.empty_positions = empty_positions;
		this.max_levels = max_levels;
		
		//looping through the board game object to make sure every entry stores a "g" for empty space.
		for(int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gameBoard[i][j] = 'g';	
			}
		}
	}

	//returns an empty hashDictionary of the given size
	public HashDictionary makeDictionary() {
		int size = 9907;
		HashDictionary dict = new HashDictionary(size);
		return dict;
	}
	
	//method which represents the content of gameBoard as a string
	private String getStringBoardGame() {
		String gameBoardString = "";
		for(int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				gameBoardString = gameBoardString + gameBoard[i][j];
			}
		}
		return gameBoardString;
	}
	
	//Using the game board as string representation, checks to see if the string is in dict, if it is then return score. If not, return -1.
	public int isRepeatedConfig(HashDictionary dict) {
		String gameBoardString = getStringBoardGame();
		if (dict.getScore(gameBoardString) != -1) {  //score of -1 = not in dict. Checks to see if the score IS in dictionary
			return dict.getScore(gameBoardString);  //return score
		} 
		else { //if the score is -1 it means the string is NOT in the dictionary, so return -1
			return -1; 
			}  
	}
	
	//First represents Board game as a string, then inserts it into dict with the corresponding score
	public void putConfig(HashDictionary dict, int score) {
		String gameBoardString = getStringBoardGame();
		Configuration config = new Configuration(gameBoardString, score);  //creating a new Configuration object 
		try {
			dict.put(config); 
		} catch (DictionaryException e) {
			e.printStackTrace();
		}
	}
	
	//This method stores symbol in gameBoard[row][col]
	public void savePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	//this method returns true if the position at [row][col] is empty ('g'), and false if it is not
	public boolean positionIsEmpty(int row, int col) {
				if (gameBoard[row][col] == 'g') {
					return true;
				}
				else {
					return false;
				}	
	}
	
	//this method returns true if the position at [row][col] is 'o', otherwise it returns false.
	public boolean tileOfComputer(int row, int col) {
		if (gameBoard[row][col] == 'o') {
			return true;
		}
		else {
			return false;
		}	
	}
	
	//this method returns true if the position at [row][col] is 'b', otherwise it returns false. 
	public boolean tileOfHuman(int row, int col) {
		if (gameBoard[row][col] == 'b') {
			return true;
		}
		else {
			return false;
		}	
	}
	
	//Check nearby tiles to see if there is at least one match between two colors HORIZONTALLY
	private boolean matchHorizontal(char symbol) {
		boolean matchWon = false;
		int matchMade = 0;  //count number of matches made horizontally
		int y_cord = 0;
		
		if (matchWon == false) {
			while (y_cord < board_size) {
				for (int i = 0; i < board_size; i++) {  //loop through the board horizontally
					if(symbol == gameBoard[i][y_cord]) {
						matchMade++;
					}
				}
				
				if (matchMade == board_size) {
					matchWon = true;
					break;
				}	
				else {
					matchMade = 0;
					y_cord++;
				}
			}
		}
		return matchWon;
	}

	//Check nearby tiles to see if there is ast least one match between two colors VERTICALLY
	private boolean matchVertical(char symbol) {
		boolean matchWon = false;
		int matchMade = 0;  //count number of matches made horizontally
		int x_cord = 0;
		
		if (matchWon == false) {
			while (x_cord < board_size) {
				for (int i = 0; i < board_size; i++) {  //loop through the board horizontally
					if (symbol == gameBoard[x_cord][i]) {
						matchMade++;
					}
				}
				if (matchMade == board_size) {
					matchWon = true;
					break;
				}	
				else {
					matchMade = 0;
					x_cord++;
				}
			}
		}
		return matchWon;
	}
	
	//check to see if diagonal win from left and right.
	private boolean matchDiagonal(char symbol) {
		boolean matchWon = false;
		int topLeftBottomRight = 0;  //from top left to bottom right match
		int topRightBottomLeft = 0;  //from top right to bottom left match
		int y_cord = (board_size - 1);
		
		for (int i = 0; i < board_size; i++) {
			if (gameBoard[i][i] == symbol) {
				topLeftBottomRight++;
			}
		}
		
		for (int j = 0; j < board_size; j++) {
			if (gameBoard[j][y_cord] == symbol) {
				topRightBottomLeft++;
			}
			y_cord--;
		}
		if (topLeftBottomRight == board_size || topRightBottomLeft == board_size) {
			matchWon = true;
		}
		return matchWon;
	}
	
	//Returns true if there are n adjacent tiles of type symbol in the same row, column, or diagonal of gameBoard, where n is the size of the game- board.
	public boolean wins(char symbol) {
		boolean matchWon = false;
		if (matchHorizontal(symbol) == true || matchVertical(symbol) == true || matchDiagonal(symbol) == true) {
			matchWon = true;
		}
		return matchWon;
	}
	
	/* Returns true if the game configuration corresponding to gameBoard
	 * is a draw assuming that the player that will perform the next move uses tiles of the type specified by symbol. 
	  The second parameter is the number of positions of the gameboard that must remain empty. */
	public boolean isDraw(char symbol, int empty_positions) {
		boolean draw = false;
		int emptySpots = 0;
		
		//while the game is NOT won yet and it is yet to be decided if draw (= false)
		while(wins(symbol) == false && draw == false) {
				for(int row = 0; row < board_size; row++) {  //loop through every tile in the board
					for (int col = 0; col < board_size; col++) {
						if (gameBoard[row][col] == 'g') {  //add up all the empty tiles
							emptySpots++;
						}
					}
				}
				if(emptySpots > empty_positions) {  //if there are more empty tiles then addressed in empty_positions, there are still spots to choose from.
					draw = false;
					break;
				}
				else if(emptySpots == empty_positions) {  //if the maximum number of tiles that can be reached are met
					if(empty_positions == 0) {  //if there can be 0 empty positions, it is a draw indefinitely
						draw = true;
						break;
					}
					else {  //loop through the board to check all the tiles adjacent to the empty one are of type "symbol". 
						for(int row = 0; row < board_size; row++) {
							for(int col = 0; col < board_size; col++) {
								if(gameBoard[row][col] == 'g') {
									
									if(((row + 1) < board_size) && (gameBoard[row + 1][col] == symbol)) {
										draw = false;
									}
									
									else if(((row + 1) < board_size) && ((col + 1) < board_size) && (gameBoard[row + 1][col+1] == symbol)) {
										draw = false;
									}
									
									else if(((col + 1) < board_size) && (gameBoard[row][col+1] == symbol)) {
										draw = false;
									}
									
									else if(((row - 1) >= 0) && ((col + 1) < board_size) && (gameBoard[row-1][col+1] == symbol)) {
										draw = false;
									}
									
									else if(((row - 1) >= 0) && (gameBoard[row-1][col] == symbol)) {
										draw = false;
									}
									
									else if(((row - 1) >= 0) && (gameBoard[row-1][col-1] == symbol)) {
										draw = false;
									}
									
									else if(((col - 1) >= 0) && (gameBoard[row][col-1] == symbol)) {
										draw = false;
									}
									
									else if(((row + 1) < board_size) && ((col-1) >= 0) && (gameBoard[row+1][col-1] == symbol)) {
										draw = false;
									}
								}  //if
							}  //inner for
						}  //outer for
					}  //else
				}  //else if
			}	//while loop
		return draw;
	}
	
	public int evalBoard (char symbol, int empty_positions) {
		char COMPUTER = 'o';
		char HUMAN = 'b';
		
		if (wins(COMPUTER)) {
			// Computer wins.
			return 3;
		} else if (wins(HUMAN)) {
			// Human wins.
			return 0;
		} else if (isDraw(symbol, empty_positions)) {
			// Draw.
			return 2;
		} else {
			// Undecided.
			return 1;
		}
	}



	
}