/* This class will be the user interface the user will interact with, to give commands that will produce output
 * based on each command given
 * @author Firas Aboushamalah
 * 250 920 750
 * Assigment 4
 */

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UI {
	static OrderedDictionary dict = new OrderedDictionary();
	public static void main(String[] args){
		ArrayList<String> fileRead = readFile(args+".txt");
	}
	
	/* readFile will read and return the files in a .txt file line by line
	 * param fileName is the fileName to read the text from
	 */
	private static ArrayList<String> readFile(String fileName){
		ArrayList<String> file = new ArrayList<String>();
		BufferedReader buffRead = null;
		try {
			String line;
			buffRead = new BufferedReader(new FileReader(fileName));
			while ((line = buffRead.readLine()) != null) {
				file.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffRead != null)
					buffRead.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return file;
	}
	/* addToRecord will add a supplied ArrayList of file lines from text file into the Records
	 * param fileLines is the file lines from the .txt files
	 */
	private void addToRecord(ArrayList<String> fileLines) throws DictionaryException{
		String type;
		for(int i=0;i<fileLines.size();i+=2){
			String key = fileLines.get(i);
			String definitions = fileLines.get(i+1);
			type=getType(definitions);
			Record dictRecord=new Record(new Pair(key,type),definitions);
			try{
				dict.put(dictRecord);
			}
			catch (DictionaryException e){
				System.err.println("Error");
			}
		}
	}
	/* getKeyType will get the keytype for a definitions
	 */
	private String getType(String definitions){
		String keyType;
		if(definitions.endsWith(".gif") || (definitions.endsWith(".jpg"))){
			keyType = "image";
		}
		else if(definitions.endsWith(".wav") || (definitions.endsWith(".mid"))){
			keyType = "audio";
		}
		else {
			keyType = "text";
		}
		return keyType;
	}
	/*
	 * readCommand will ask user for command followed by parameters for each command, then will split the string and parse individual parts.
	 * Will display picture if type 2, play music if type 3, display text if type 1
	 */
	private void readCommand(){
		StringReader reader = new StringReader();
		String fullCmd="";
		String cmd="";
		PictureViewer picView=new PictureViewer();
		SoundPlayer soundPlay=new SoundPlayer();
		
		//check if user inputs End command
		while(fullCmd!="End"){
			fullCmd=reader.read("Enter command: ");
			String[] cmdpiece = fullCmd.split(" ");
			cmd = cmdpiece[0];
			// check if command is insert
			if(cmd.equals("insert")){
				String keyWord=cmdpiece[1];
				String keyType=cmdpiece[2];
				String definitions=cmdpiece[3];
				try {
					dict.put(new Record(new Pair(keyWord,keyType),definitions));
				} catch (DictionaryException e) {
					System.err.print("Error in inserting");
				}
			}
			// check if command is search
			else if(cmd.equals("search")){
				String keyType = cmdpiece[2];
				String definitions=cmdpiece[3];
				if(definitions.endsWith(".jpg") || (definitions.endsWith(".gif"))){
					try {
						picView.show(definitions);
					} catch (MultimediaException e) {
						System.err.println("Error in opening file");
						}
					}
				else if(definitions.endsWith(".mid") || (definitions.endsWith(".wav"))){
					try {
						soundPlay.play(definitions);
					} catch (MultimediaException e) {
						System.err.println("Error in playing file");
						}
					}
				else{
					System.out.println(definitions);
				}
			}
			// check if command is remove
			else if(cmd.equals("remove")){
				String keyWord=cmdpiece[1];
				String keyType=cmdpiece[2];
				try {
					dict.remove(new Pair(keyWord,keyType));
				} catch (DictionaryException e) {
					System.err.print("Error in removing");
				}
			}
			// check if command is next
			else if(cmd.equals("next")){
				String keyWord=cmdpiece[1];
				String keyType=cmdpiece[2];
				dict.successor(new Pair(keyWord,keyType));
			}
			// check if command is prev
			else if(cmd.equals("prev")){
				String keyWord=cmdpiece[1];
				String keyType=cmdpiece[2];
				dict.predecessor(new Pair(keyWord,keyType));
			}
			// check if command is first
			else if(cmd.equals("first")){
				dict.smallest();
			}
			// check if command is last
			else if(cmd.equals("last")){
				dict.largest();
			}
			// if none of the top commands
			else {
				System.out.println("Invalid command");
			}
		}
	}
}
