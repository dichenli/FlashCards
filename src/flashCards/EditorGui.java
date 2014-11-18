/**
 * 
 */
package flashCards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Linjie Peng
 */
public class EditorGui extends JFrame {
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JList list1;
	JScrollPane jsp;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;
	JButton button6;
	JButton button7;
	JButton button8;
	DefaultListModel listItems;
	
	StudyList sl = new StudyList();
	Hashtable<Item, String> lines = new Hashtable<Item, String>();
	Hashtable<String, Item> relines = new Hashtable<String, Item>();
    
    public static void main(String[] args) {
        new EditorGui().createGui();

    }
    
    public void createGui() {
        
    	listItems = new DefaultListModel();
    	for (String s: lines.values()) {
    		listItems.addElement(s);		
    	}
        list1 = new JList(listItems);
        
    	p1 = new JPanel();
    	p1.setLayout(new GridLayout(1, 1));
    	p1.add(list1);
    	jsp = new JScrollPane(list1);
    	p1.add(jsp);
    	
    	p2 = new JPanel();
    	p2.setLayout(new GridLayout(8, 1));
    	button1 = new JButton("Add");
    	p2.add(button1);
    	button2 = new JButton("Find");
    	p2.add(button2);
    	button3 = new JButton("Edit");
    	p2.add(button3);
    	button4 = new JButton("Delete");
    	p2.add(button4);
    	button5 = new JButton("Load");
    	p2.add(button5);
    	button6 = new JButton("Save");
    	p2.add(button6);
    	button7 = new JButton("Save As");
    	p2.add(button7);
    	button8 = new JButton("Quit");
    	p2.add(button8);
    	
    	button1.addActionListener(new ButtonAddListener());
    	button2.addActionListener(new ButtonFindListener());
    	button3.addActionListener(new ButtonEditListener());
    	button4.addActionListener(new ButtonDeleteListener());
    	button5.addActionListener(new ButtonLoadListener());
    	button6.addActionListener(new ButtonSaveListener());
    	button7.addActionListener(new ButtonSaveAsListener());
    	button8.addActionListener(new ButtonQuitListener());
    	
    		
    	setLayout(new FlowLayout());
    	add(p1);
    	add(p2);
    	p1.setPreferredSize(new Dimension(350, 250));
    	p2.setPreferredSize(new Dimension(100, 250));
    	
    	setTitle("Editor");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,300);
    }
    
    class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String stimulusToAdd = JOptionPane.showInputDialog(p1, "what is the stimulus: ?");
			String responseToAdd = JOptionPane.showInputDialog(p1, "what is the response: ?");
			Item itemToAdd = new Item(stimulusToAdd, responseToAdd);
			sl.add(itemToAdd);
			String lineTOAdd = "" + itemToAdd.getStimulus() + " || " + itemToAdd.getResponse() + " || " + itemToAdd.getTimesCorrect();
            lines.put(itemToAdd, lineTOAdd);
            relines.put(lineTOAdd, itemToAdd);
			listItems.addElement(lineTOAdd);
		}
    }
    
    class ButtonFindListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String search_word = JOptionPane.showInputDialog(p1, "which one do you want to find(stimulus or response):?");
		Item search_item = sl.find(search_word);
		if (search_item == null || !lines.containsKey(search_item)) {
			JOptionPane.showMessageDialog(p1, "Can't find such an item!");
		}
		else {
			int index = listItems.indexOf(lines.get(search_item));
            list1.setSelectedIndex(index);
		}
			
		} 
    }
    
    class ButtonEditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    int index = list1.getSelectedIndex();	
		    if (index != -1) {
		    	String lineToEdit = listItems.get(index).toString();
		    	Item itemToEdit = relines.get(lineToEdit);
		    	String newStimulus = JOptionPane.showInputDialog(p1, "what is the new stimulus: ?");
		    	String newResponse = JOptionPane.showInputDialog(p1, "what is the new response: ?");
		    	String newLine = "" + newStimulus + " || " + newResponse + " || " + itemToEdit.getTimesCorrect();
		    	sl.modify(itemToEdit, newStimulus, newResponse);
		    	lines.remove(itemToEdit);
		    	relines.remove(lineToEdit);
		    	lines.put(new Item(newStimulus, newResponse), newLine);
		    	relines.put(newLine, new Item(newStimulus, newResponse));
		    	listItems.setElementAt(newLine, index);
		    }
	    }
    }
    
    class ButtonDeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = list1.getSelectedIndex();	
			if (index != -1) {
				String lineToDelete = listItems.get(index).toString();
				Item itemToDelete = relines.get(lineToDelete);
				String lineTODelete = "" + itemToDelete.getStimulus() + " || " + itemToDelete.getResponse() + " || " + itemToDelete.getTimesCorrect();
				sl.delete(itemToDelete);
				listItems.remove(index);
				lines.remove(itemToDelete);
				relines.remove(itemToDelete);
			}
		}
			
		}
  
    
    class ButtonLoadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				sl.clear();
				sl.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			listItems.clear();
			lines.clear();
			relines.clear();
		
            for(Item item : sl.getList()) {
                String line = "" + item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
                lines.put(item, line);
                relines.put(line, item);
            }
            
            
			for (String s: lines.values()) {
	    		listItems.addElement(s);		
	    	}
			
		}
    }
    
    class ButtonSaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				sl.save();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
    }
    
    class ButtonSaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				sl.saveAs();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
    }
    
    class ButtonQuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
    }
    

}