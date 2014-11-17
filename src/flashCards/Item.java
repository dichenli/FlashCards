/**
 * 
 */
package flashCards;

/**
 * @author 
 */
public class Item {

    private String stimulus;
    private String response;
    private int timesCorrect;
	
    public Item(String stimulus, String response) {
    	if(stimulus.equals("") || response.equals("") || stimulus == null || response == null) {
    			throw new IllegalArgumentException("The inputs can't be blank!");
    		}
    		
        this.stimulus = stimulus.trim();
        this.response = response.trim();
        this.timesCorrect = 0;
    }
    
    public String getStimulus() {
        return stimulus;
    }
    
    public void setStimulus(String stimulus) {
    		if(stimulus.equals("")) {
			throw new IllegalArgumentException("The inputs can't be blank!");
		}
        this.stimulus = stimulus.trim();
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
    		if(response.equals("")) {
			throw new IllegalArgumentException("The inputs can't be blank!");
		}
        this.response = response.trim();
    }
    
    public int getTimesCorrect() {
        return timesCorrect;
    }
    
    public void setTimesCorrect(int times) {
    		if(times < 0) {
			throw new IllegalArgumentException("The times correct can't be negative!");
		}
        this.timesCorrect = times;
    }
}