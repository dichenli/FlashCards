/**
 * @author Linjie Peng
 */

package flashCards;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	
	DefaultListModel listItems;
	JMenuBar menuBar1;
	JMenu fileMenu1;
	JMenuItem load;
	JMenuItem save;
	JMenuItem saveAs;
	JMenu fileMenu2;
	JMenuItem help;
	
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
        
        menuBar1 = new JMenuBar();
        fileMenu1 = new JMenu("File");
        fileMenu2 = new JMenu("Help");
        load = new JMenuItem("Load the file");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save as..");
        help = new JMenuItem("help");
        
        this.setJMenuBar(menuBar1);
        menuBar1.add(fileMenu1);
        fileMenu1.add(load);
        fileMenu1.add(save);
        fileMenu1.add(saveAs);
        menuBar1.setBackground(Color.GRAY);
        menuBar1.setSize(100, 100);
        this.setJMenuBar(menuBar1);
        menuBar1.add(fileMenu2);
        fileMenu2.add(help);
           
    	p1 = new JPanel();
    	p1.setLayout(new GridLayout(1, 1));
    	p1.add(list1);
    	jsp = new JScrollPane(list1);
    	p1.add(jsp);
    	
    	p2 = new JPanel();
    	p2.setLayout(new GridLayout(5, 1, 0, 15));
    	button1 = new JButton("Add");
    	p2.add(button1);
    	button2 = new JButton("Find");
    	p2.add(button2);
    	button3 = new JButton("Edit");
    	p2.add(button3);
    	button4 = new JButton("Delete");
    	p2.add(button4);
    	button5 = new JButton("Quit");
    	p2.add(button5);
    	
    	
    	button1.addActionListener(new ButtonAddListener());
    	button2.addActionListener(new ButtonFindListener());
    	button3.addActionListener(new ButtonEditListener());
    	button4.addActionListener(new ButtonDeleteListener());
    	button5.addActionListener(new ButtonQuitListener());
    	
    	load.addActionListener(new BarLoadListener());
    	save.addActionListener(new BarSaveListener());
    	saveAs.addActionListener(new BarSaveAsListener());
    	
    	help.addActionListener(new BarHelpListener());
 
    	setLayout(new FlowLayout());
    	add(p1);
    	add(p2);
    	
    	p1.setPreferredSize(new Dimension(300, 250));
    	p2.setPreferredSize(new Dimension(80, 250));
    	
    	setTitle("Editor");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(400,300);
    }
    
    class ButtonAddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String stimulusToAdd = JOptionPane.showInputDialog(p1, "what is the stimulus: ?");
				String responseToAdd = JOptionPane.showInputDialog(p1, "what is the response: ?");
				Item itemToAdd = new Item(stimulusToAdd, responseToAdd);
				sl.add(itemToAdd);
				String lineTOAdd = "" + itemToAdd.getStimulus() + " || " + itemToAdd.getResponse() + " || " + itemToAdd.getTimesCorrect();
				lines.put(itemToAdd, lineTOAdd);
				relines.put(lineTOAdd, itemToAdd);
				listItems.addElement(lineTOAdd);
			} catch (NullPointerException e2) {
				//do nothing
			} catch (IllegalArgumentException e3) {
				JOptionPane.showMessageDialog(p1, e3.toString());
			}
		}
    }
    
    class ButtonFindListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    try {
		    	String search_word = JOptionPane.showInputDialog(p1, "which one do you want to find(stimulus or response):?");
		    	Item search_item = sl.find(search_word);
		    	if (search_item == null || !lines.containsKey(search_item)) {
		    		JOptionPane.showMessageDialog(p1, "Can't find such an item!");
		    	}
		    	else {
		    		int index = listItems.indexOf(lines.get(search_item));
		    		list1.setSelectedIndex(index);
		    	}
		    } catch (NullPointerException e1) {
		    	//do nothing
		    } 
			
		} 
    }
    
    class ButtonEditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
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
				if (index == -1) {
					JOptionPane.showMessageDialog(p1, "Please select an item to edit!");
				}
			} catch (NullPointerException e2) {
				//do nothing
			} catch (IllegalArgumentException e3) {
				JOptionPane.showMessageDialog(p1, e3.toString());
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
			if (index == -1) {
				JOptionPane.showMessageDialog(p1, "Please select an item to delete!");
			}
		}
			
		}
  
    
    class BarLoadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				sl.clear();
				sl.load();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(p1, "Cannot open this file!");
			} catch (NullPointerException e2) {
	        	JOptionPane.showMessageDialog(p1, "File open cancelled!");
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
    
    class BarSaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 try {
		          sl.save();
		        } catch (IOException e1) {
		            JOptionPane.showMessageDialog(p1, "File save failed!");
		        } catch (NullPointerException e2) {
		        	JOptionPane.showMessageDialog(p1, "File save failed!");
		        }
		}
    }
    
    class BarSaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 try {
		          sl.saveAs();
		        } catch (IOException e1) {
		            JOptionPane.showMessageDialog(p1, "File save failed!");
		        } catch (NullPointerException e2) {
		        	JOptionPane.showMessageDialog(p1, "File save failed!");
		        }
		}
    }
    
    class BarHelpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(p1, 
					"Display: you can display the contents of the list.\n"
					+ "Add: you can add a new item(stimulus/response pair).\n"
					+ "Find: you can find an item, given either the stimulus or reponse.\n"
					+ "Edit: you can edit the existing item.\n"
					+ "Delete: you can delete a item.\n"
					+ "Quit: you can quit this program.\n"
					+ "Load: you can load a file of study list.\n" 
					+ "Save: you can save a file. \n" 
					+ "Save As: you can save this list as another file. \n"
					);
		}
    	
    }
    
    class ButtonQuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int yesNo = JOptionPane.showConfirmDialog(p1, "Do you want to save before you exit?", "Exit Application", JOptionPane.YES_NO_CANCEL_OPTION);
		    if (yesNo == JOptionPane.YES_OPTION) {
		    	try {
			          sl.save();
			        } catch (IOException e1) {
			            JOptionPane.showMessageDialog(p1, "File save failed!");
			        } catch (NullPointerException e2) {
			        	JOptionPane.showMessageDialog(p1, "File save failed!");
			        }
		    	System.exit(0);
		    }
		    else if (yesNo == JOptionPane.NO_OPTION) {
		    	System.exit(0);
		    }
		    else {
		    	// do nothing;
		    }
			
		}
    }
    

}
