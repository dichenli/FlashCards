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

	private ArrayList<Item> items;

	public ArrayList<Item> getList() {
		return items;
	}

	public StudyList() {
		items = new ArrayList<Item>();
	}

	public void clear() {
		items.clear();
	}

	public void add(Item item) {
		if(item == null) {
			throw new IllegalArgumentException("The item is empty");
		}
		if(this.find(item.getStimulus()) == null) {
			items.add(item);
		} else {
			throw new IllegalArgumentException("The item is already in the list");
		}
	}

	public Item find(String stimulusOrResponse) {
		Item returnVal = null;
		stimulusOrResponse = stimulusOrResponse.trim();
		for(Item item : items) {
			if(item.getStimulus().trim().equals(stimulusOrResponse) || item.getResponse().trim().equals(stimulusOrResponse)) {
				returnVal = item;
				break;
			}
		}
		return returnVal;
	}

	public void delete(Item item) {
		if(items == null || items.size() == 0 || item == null || item.getClass() != Item.class) {
			return;
		}

		Item deleted = null;
		for(Item i : items) {
			if (i.getStimulus().trim().equals(item.getStimulus().trim()) && i.getResponse().trim().equals(item.getResponse().trim())) {
				deleted = i;
			}
		}
		items.remove(deleted);
	}

	public void modify(Item item, String newStimulus, String newResponse) {
		if(items == null || items.size() == 0 || item == null || item.getClass() != Item.class) {
			return;
		} else if (newStimulus == null || newResponse == null || newStimulus.getClass() != String.class || newResponse.getClass() != String.class) {
			return;
		} else if (newStimulus.trim().isEmpty() || newResponse.trim().isEmpty()) {
			return;
		}

		for(Item i : items) {
			if(i.getStimulus().trim().equals(item.getStimulus().trim()) || i.getResponse().trim().equals(item.getResponse().trim())) {
				i.setResponse(newResponse);
				i.setStimulus(newStimulus);
				break;
			}
		}
	}

	public void load() throws IOException {
		ArrayList<String> lines = SimpleIO.load();
		for(String line : lines) {

			//System.out.println(line);//for test

			String[] entries = line.split(" *\\|\\| *");

					if(entries.length == 2 || entries.length == 3) {
						entries[0] = entries[0].trim();
						entries[1] = entries[1].trim();
						//System.out.println(entries[0]);//for test
						//System.out.println(entries[1]);//for test
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

						items.add(item);
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

	public int saveAs() throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		for(Item item : items) {
			String line = "" + item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
			lines.add(line);
		}
		return SimpleIO.saveAs(lines);        
	}
}
