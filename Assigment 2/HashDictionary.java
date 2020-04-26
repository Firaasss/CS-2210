//Firas Aboushamalah
//250920750
//This class implements a dictionary using a hash table of given size with separate chaining.

public class HashDictionary implements DictionaryADT {

	//Instance Variables for size of dictionary & establishing dictionary node
	private Node<Configuration> [] dict;
	private int size;
	
	//constructor to create the HashDictionary of specified size;
	public HashDictionary(int size) {
		dict = (Node<Configuration>[]) new Node[size];
		this.size = size;
		}
	
	//Hash function to convert string into a number and store it in hashSum
	private int hash(String s) {
		int hashSum = 0;
		int EXPONENT = 31;
		for (int i = 0; i < s.length(); i++) {
			hashSum = Math.abs(EXPONENT * hashSum + s.charAt(i)); //math.abs to prevent negative output
		}
		hashSum = hashSum % size; //compression of the ID tof it the size of our dictionary
		return hashSum;	
	}
	

	//Inserts the Configuration object referenced by data in the dictionary & throws exception if already in the dictionary
	public int put(Configuration data) throws DictionaryException {
		int hashIndex = hash(data.getStringConfiguration());  //creating a hash key for our String data object
		Node<Configuration> configNode = dict[hashIndex];  //referencing the node that is already present where we are searching
		Node<Configuration> newNode = new Node<Configuration>(data);  //creating a new node to insert the data object into
		
		if (configNode == null) {  //if the node is empty then create the new node and return successful
			dict[hashIndex] = newNode;
			return 0;
		} 
		
		else {
			while (configNode != null) {  
				if (configNode.getData().getStringConfiguration().equals(data.getStringConfiguration())) {  //check to see if our configNode is already in the dictionary but comparing both strings
					throw new DictionaryException("This entry already exists.");
				}
				else if (configNode.getNext() == null) {  //if it is not already in the dictionary,  set it as the next node.
					configNode.setNext(newNode);
					break;
				}
				else {  
					configNode = configNode.getNext();
				}
			}
		}
		return 1;  //return unsuccessful as there was a collision.
	}
	

	/* Removes the entry with configuration string config from the dictionary. 
	Will throw a DictionaryException (see below) if the configuration is not in the dictionary. */
	public void remove(String config) throws DictionaryException {
		int searchNum = hash(config);  //uses the hash function to store the hash index for the key we want to remove
		Node<Configuration> removeMe = dict[searchNum];
		if(removeMe == null) {  //if the key is empty or non existence
			throw new DictionaryException("This configuration cannot be removed.");
		}
		else {
			while(removeMe != null) { 
				if(removeMe.getData().getStringConfiguration().equals(config)) {  //if we have found a match
					if (removeMe.getPrev() == null && removeMe.getNext() == null) {  //if key before it and key after it are both null
						dict[searchNum] = null;
						break;
					}
					else if (removeMe.getPrev() == null && removeMe.getNext() != null) {  //iff only the key after is null
						dict[searchNum] = removeMe.getNext();
						removeMe.getNext().setPrev(null);
						break;
					}
					else {  //otherwise if there are keys prev + next
						removeMe.getPrev().setNext(removeMe.getNext());
						removeMe.getNext().setPrev(removeMe.getPrev());
						while (removeMe.getPrev() != null) {
							if (removeMe.getPrev().getPrev() == null) {
								dict[searchNum] = removeMe.getPrev();
								break;
							}
						}
					}
				}
				else { removeMe = removeMe.getPrev(); }
			}
		}
	}
	
	/* Returns the score in the Configuration object with configuration string configuration stored 
    in the dictionary, or -1 if the configuration string configuration is not in the dictionary. */
	public int getScore(String config){
		int hashKey = hash(config);
		Node<Configuration> requiredNode = dict[hashKey];
		if(requiredNode == null) {
			return -1;
		}else {
			return requiredNode.getData().getScore();
		}
	}
	
}
