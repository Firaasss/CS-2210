//Firas Aboushamalah
//250-920-750
//represents key attribute of record in the ordered dictionary. Each key has 2 parts: word and type
//Attribute word is a string of one more letters, with letters converted to lower case
//Attribute type can be "text" or "audio" or "image"
public class Pair {
	
	private String word; 
	private String type;
	
	public Pair(String word, String type) {
		this.word = word.toLowerCase();
		this.type = type;
	}
	
	public String getWord() {
		return word;
	}
	
	public String getType() {
		return type;
	}
	
	public int compareTo(Pair k) {
		if (this.word.compareTo(k.getWord()) == 0) { //if word 1 = word 2
			if (this.type.compareTo(k.getType()) == 0) {  //if type 1 = type 2 then the keys are equal return 0
				return 0;
			}
			else if (this.type.compareTo(k.getType()) < 0) {  //if word 1 = word 2 but type 1 < type 2 then return -1
				return -1;  
			}
		}
		else if (this.word.compareTo(k.type) < 0) {  //if word 1 < word 2 then return -1
			return -1;
		}
		return 1;
	}
}

