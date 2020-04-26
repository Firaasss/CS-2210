/*Firas Aboushamalah
250-920-750
this class implements the class of exceptions thrown by the put and remove methods of OrderedDictionary*/

public class DictionaryException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DictionaryException (String msg){
		super(msg);
	}
}