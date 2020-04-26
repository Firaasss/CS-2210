//Firas Aboushamalah
//250-920-750
//Implements ordered dictionary using a binary search tree
//Uses Record object to store data contained in each INTERNAL node of tree
//Leaves will store null Record objects
//Key for an internal node will be the Pair object from the Record stored in that node

public class OrderedDictionary implements OrderedDictionaryADT {

	//initiating the root node variable
	private Node rootNode;
	public OrderedDictionary(){ //creating the constructor
		rootNode = new Node(null);  //making the root null
	}
	
	//this is a private method which will help the get method find the pair K by traversing through the BST
	private Node findRoot(Pair k) {
		Node r = rootNode;
		int check;
		while (r.getRecord() != null) {  //while root is not a leaf
			check = r.getRecord().getKey().compareTo(k);  //if the root == desired object, return the object node
			if (check == 0) {
				return r;
			}
			else if (check == -1) {  //if it is less than the desired object, go to the right of the root
				r = r.getRight();
			}
			else if (check == 1)  //if it is greater than the desired object, go to the left 
				r = r.getLeft();
		}
		return r;
	}

	//Returns the Record object with key k, or it returns null if such a record is not in the dictionary. 
	public Record get(Pair k) {
		Node current = findRoot(k);  //use the findRoot method to extract k
		if (current.getRecord() != null) {  //if the answer from the findRoot method is not null (not a leaf/empty) then return the element in k as a Record
			return current.getRecord();
		}
		return null;
	}
	
	/* Inserts r into the ordered dictionary. It throws a DictionaryException if a record with 
	 * the same key as r is already in the dictionary. */
	public void put(Record r) throws DictionaryException {
		Pair nodeKey = r.getKey();  //getting key of r Record
		Node current = findRoot(nodeKey);
		if (current.getRecord() != null) {  //if the record is not empty/has children
			throw new DictionaryException("The key you are trying to enter is already in the dictionary.");
		}
		else {
			current.setRoot(r);  //update the root variable 
		}
	}

	/* Removes the record with key k from the dictionary. It throws a DictionaryException if the 
	 * record is not in the dictionary. */
	public void remove(Pair k) throws DictionaryException {
		Node current = findRoot(k);  //initializing the key that is to be found in a variable
		Node leftCh = current.getLeft();  //left child of node k
		Node rightCh = current.getRight();  //right child of node k
		Record record;
		if (get(k) == null) {  //if the key is inexistent
			throw new DictionaryException("The key you are trying to enter is not in the dictionary.");
		}
		
		else {  //if the node is in the dictionary
			
			//case 1: if the node has no children
			if (leftCh.getRecord() == null && rightCh.getRecord() == null) {
				current.setnullLeft();
				current.setnullRight();
				current.setRoot(null);
			}
			
			//case 2: if the node has 1 child and it is on the right side
			else if (leftCh.getRecord() == null && rightCh.getRecord() != null) {
				record = rightCh.getRecord();
				remove(record.getKey());
				current.setRoot(record);
			}
			
			//case 3: if the node has 1 child and it is on the left side
			else if (leftCh.getRecord() != null && rightCh.getRecord() == null) {
				record = leftCh.getRecord();
				remove(record.getKey());
				current.setRoot(record);
			}
			
			//case 4: if the node has 2 children 
			else if (leftCh.getRecord() != null && rightCh.getRecord() != null) {
				record = successor(current.getRecord().getKey());
				remove(record.getKey());
				current.setRoot(record);
			}
		}
	}

	/* Returns the successor of k (the record from the ordered dictionary with smallest key larger 
	 * than k); it returns null if the given key has no successor. The given key DOES NOT need to 
	 * be in the dictionary. */
	public Record successor(Pair k) {
		Node root = rootNode;
		Record rootRecord = root.getRecord(); //Record of the root node
		if (rootRecord == null) {return null;}  //if the root is not an empty node
		else if(root.getLeft().getRecord() == null && root.getRight().getRecord() == null) {return null;}  //if the root is not a leaf (no children)
		
		Pair rootKey = rootRecord.getKey();
		int i = 0;
		
		Record[] recordList = new Record[1000];  //initialize a new record
		while (rootKey.compareTo(k) != 0) {  //while rootKey does not equal k
			if (rootKey.compareTo(k) < 0) {  //if the rootKey is lexicographically smaller than k
				recordList[i] = rootRecord;  //set the element in position i in recordList to the root Record
				i++;  
				root = root.getRight();  //update the root to the right child
				rootRecord = root.getRecord();
			}
			else if (rootKey.compareTo(k) > 0) {  //if the rootKey is lexicographically larger than k
				recordList[i] = rootRecord;
				i++;
				root = root.getLeft();//update the root to the left child
				rootRecord = root.getRecord();
			}	
		}
		if (root.getRight().getRecord() != null) {  //if the root has a right child, make that its successor
			return root.getRight().getRecord();
		}
		else {
			for (int j=i-1; j>=0; j++) {  //loop through the recordlist and compare each element to see if it is greater larger than element k
				if (recordList[j].getKey().compareTo(k) > 0) {
					return recordList[j];
				}
			}
			return null;
		
		}
	}
	
	/* Returns the predecessor of k (the record from the ordered dictionary with largest key smaller 
	 * than k; it returns null if the given key has no predecessor. The given key DOES NOT need to be 
	 * in the dictionary. */
	public Record predecessor(Pair k) {
		Node root = rootNode;
		Record rootRecord = root.getRecord(); //Record of the root node
		if (rootRecord == null) {return null;}
		else if(root.getLeft().getRecord() == null && root.getRight().getRecord() == null) {return null;}
		else if (get(k) == null) {return null;}
		Pair rootKey = rootRecord.getKey();
		int i = 0;
		
		Record[] recordList = new Record[1000];
		while (rootKey.compareTo(k) != 0) {  //while rootKey does not equal k
			if (rootKey.compareTo(k) < 0) {  //if the rootKey is lexicographically smaller than k
				recordList[i] = rootRecord;  //set the element in position i in recordList to the root Record
				i++;  
				root = root.getRight();  //update the root to the right child
				rootRecord = root.getRecord();
			}
			else if (rootKey.compareTo(k) > 0) {  //if the rootKey is lexicographically larger than k
				recordList[i] = rootRecord;
				i++;
				root = root.getLeft();//update the root to the left child
				rootRecord = root.getRecord();
			}	
		}
		if (root.getLeft().getRecord() != null) {  //if the root has a left child that will be the new successor
			return root.getLeft().getRecord();
		}
		else {
			for (int j=i-1; j>=0; j++) {  //loop through the contents of record list and compare position i with Pair object k
				if (recordList[j].getKey().compareTo(k) > 0) {
					return recordList[j];
				}
			}
			return null;	
		}
	}


	//Returns the record with smallest key in the ordered dictionary. Returns null if the dictionary is empty.
	public Record smallest() {
		Node r = rootNode;
		if (r.getRecord() == null) {  //if the root is a leaf return null
			return null;
		}
		else {  //while not a leaf
			while (r.getLeft().getRecord() != null) {
				r = r.getLeft();  //keep traversing to the furthermost left element (smallest node in tree)
			}
		return r.getRecord();
		}
	}

	//Returns the record with largest key in the ordered dictionary. Returns null if the dictionary is empty. 
	public Record largest() {
		Node r = rootNode;
		if (r.getRecord() == null) {  //if the root is a leaf return null
			return null;
		}
		else {  //while not a leaf
			while (r.getRight().getRecord() != null) {
				r = r.getRight();  //keep traversing to the furthermost right element (largest node)
			}
		return r.getRecord();
		}
	}

}
