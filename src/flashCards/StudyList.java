/**
 * 
 */
package flashCards;

import java.io.IOException;
import java.util.ArrayList;

import simpleIO.SimpleIO;

/**
 * @author David Matuszek
 */
public class StudyList {

    ArrayList<Item> items;
	
    public StudyList() {
        items = new ArrayList<Item>();
    }
    
    public void add(Item item) {
        items.add(item);
    }
    
    public Item find(String stimulusOrResponse) {
        Item returnVal = null;
    		for(Item item : items) {
        		if(item.getStimulus().equals(stimulusOrResponse) || item.getResponse().equals(stimulusOrResponse)) {
        			returnVal = item;
        			break;
        		}
        }
    		return returnVal;
    }
    
    public void delete(Item item) {
        for(Item i : items) {
        		if (i.getStimulus() == item.getStimulus() && i.getResponse() == item.getResponse()) {
        			items.remove(i);
        		}
        }
    }
    
    public void modify(Item item, String newStimulus, String newResponse) {
		for(Item i : items) {
			if(i.getStimulus().equals(item.getStimulus()) || i.getResponse().equals(item.getResponse())) {
				i.setResponse(newResponse);
				i.setStimulus(newStimulus);
				break;
			}
		}
    }
    
    public void load() throws IOException {
        ArrayList<String> lines = SimpleIO.load();
        for(String line : lines) {
        		String[] entries = line.split(" *\\|\\| *");
        		
        		if(entries.length == 2 || entries.length == 3) {
        			entries[0] = entries[0].trim();
        			entries[1] = entries[1].trim();
        			if(entries[0].equals("") || entries[1].equals("")) {
        				continue; //ignore item with empty entries
        			}
        			Item item = new Item(entries[0], entries[1]);
        			
        			//read in times
        			if(entries.length == 3) {
        				entries[2].trim();
        				try {
        					int times = Integer.parseInt(entries[2].trim());
        					item.setTimesCorrect(times);
        				} catch(NumberFormatException e) {
        					continue;
        				}
        			}
        		}
        }
    }
    
    public void save() throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
    		for(Item item : items) {
        		String line = "" + item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
        		lines.add(line);
        }
    		SimpleIO.save(lines);
    }
    
    public void saveAs() throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
    		for(Item item : items) {
        		String line = "" + item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
        		lines.add(line);
        }
    		SimpleIO.saveAs(lines);        
    }
}
