//Firas Aboushamalah
//250920750
//This class stores all the data that each entry of HashDictionary contains.

public class Configuration {  
	
	//instance variables to use in constructor
	public String config;
	public int score;
	
	public Configuration(String config, int score) {	//constructor which returns a new config object with specified configuration string and score
		this.config = config;
		this.score = score;
	}
	
	//returns the configuration string stored in the config object
	public String getStringConfiguration() {
		return config;
	}
		
	//returns the score that is stored in the config object
	public int getScore() {
		return score;
	}
}
