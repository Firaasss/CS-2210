//Firas Aboushamalah
//250-920-750
//Represents a record in the ordered dictionary
//Each record has 2 parts: key + data associated with key

public class Record {

	private Pair key;
	private String data;
	
	public Record(Pair key, String data) {
		this.key = key;
		this.data = data;
	}
	
	public Pair getKey() {
		return key;
	}
	
	public String getData() {
		return data;
	}
}
