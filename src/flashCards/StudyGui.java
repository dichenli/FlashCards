/**
 * Done by Dichen Li
 */
package flashCards;

import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
//import javax.swing.ButtonGroup;
import javax.swing.JButton;
//import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
//import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
//import javax.swing.JSeparator;
//import javax.swing.JSlider;
//import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
//import javax.swing.SpinnerModel;
//import javax.swing.SpinnerNumberModel;
//import javax.swing.UIManager;
//import javax.swing.border.LineBorder;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.Document;
//import javax.swing.text.EditorKit;

import sun.awt.DisplayChangedListener;

/**
 * @author Your Name Goes Here
 */
public class StudyGui extends JFrame {

	//Study list related
	private StudyList studyList;
	ArrayList<Item> currentItems = null;
	final int maxCount = 15; // show at most 15 questions at a time
	final int timesCorrectReq = 4; //question is not shown after 4 times correct
	int index; // current question in currentItems
	//Does the currently opened file need saving?
	private boolean needsSaving;

	//Master panel
	private JPanel wholePanel = new JPanel();

	//menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem open = new JMenuItem("Open");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem saveAs = new JMenuItem("Save as..");
	private JMenuItem close = new JMenuItem("Close");

	//question pane
	private JLabel questionLabel;
	private JEditorPane questionText = new JEditorPane(); // to put text, define size and background
	private JScrollPane questionScroll; // to enable scrolling
	private JPanel question = new JPanel(); 

	//answer pane
	private JLabel answerLabel;
	private JButton answerSubmit;
	private JPanel answerHead;
	private JTextArea answerText = new JTextArea();
	private JScrollPane answerScroll;
	private JPanel answer = new JPanel();

	//results pane
	private JPanel result = new JPanel();
	private JLabel resultLabel;
	private JEditorPane resultText = new JEditorPane();
	private JScrollPane resultScroll;



	public static void main(String[] args) {
		new StudyGui().run();
	}

	public void run() {
		studyList = new StudyList();

		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		closingWindowDialog();
		needsSaving = false;

		wholePanel.setLayout(new GridLayout(3, 1, 20, 0));
		getContentPane().add(wholePanel, BorderLayout.CENTER);
		wholePanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

		setMenuBar();
		setQuestionBar();
		setAnswerBar();
		setResultBar();

		//Do these in the last!
		pack();
		setVisible(true);
	}

	//set menu bar
	private void setMenuBar() {
		this.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(close);
		menuBar.setBackground(Color.GRAY);
		fileMenu.setBackground(Color.GRAY);

		open.addActionListener(new OpenListener());
		save.addActionListener(new SaveListener());
		saveAs.addActionListener(new SaveAsListener());
		close.addActionListener(new CloseListener());
	}

	//set question bar
	private void setQuestionBar() {
		questionLabel = new JLabel("Question:");

		questionText.setText("");
		questionText.setEditable(false);
		questionText.setPreferredSize(new Dimension(600, 100));
		questionText.setBackground(Color.WHITE);
		questionScroll = new JScrollPane(questionText);

		question.setLayout(new BorderLayout());
		question.add(questionLabel, BorderLayout.NORTH);
		question.add(questionScroll, BorderLayout.CENTER);
		wholePanel.add(question, 0);
		//JScrollPane(questionScroll) contains JEditorPane(questionText), 
		//JPane(question) contains JScrollPane(questionScroll) and JLabel(questionLabel)
	}

	//set answer bar
	private void setAnswerBar() {
		answerLabel = new JLabel("Type your answer here then click submit:");
		answerSubmit = new JButton("Submit");
		answerHead = new JPanel(new BorderLayout());
		answerHead.add(answerLabel, BorderLayout.WEST);
		answerHead.add(answerSubmit, BorderLayout.EAST);

		answerText.setEditable(true);
		answerText.setPreferredSize(new Dimension(600, 100));
		answerText.setBackground(Color.WHITE);
		answerScroll = new JScrollPane(answerText);

		answer.setLayout(new BorderLayout());
		answer.add(answerHead, BorderLayout.NORTH);
		answer.add(answerScroll, BorderLayout.CENTER);
		wholePanel.add(answer, 1);

		answerSubmit.addActionListener(new AnswerSubmitListener());
	}

	//set result bar
	private void setResultBar() {
		resultLabel = new JLabel("Note:");

		resultText.setText("Please open a file to start");
		resultText.setEditable(false);
		resultText.setPreferredSize(new Dimension(600, 100));
		resultText.setBackground(Color.WHITE);
		resultScroll = new JScrollPane(resultText);

		result.setLayout(new BorderLayout());
		result.add(resultLabel, BorderLayout.NORTH);
		result.add(resultScroll, BorderLayout.CENTER);
		wholePanel.add(result, 2);
	}
	
	//prepare a list of 15 questions for this study
	private void prepareList() {
		//preparations
		currentItems = new ArrayList<Item>();
		ArrayList<Item> availableItems = new ArrayList<Item>();
		if (studyList.getList().size() == 0) {
			return;
		}
		
		//make a copy of items list that has only items that are available
		for(Item item : studyList.getList()) {
			if (item.getTimesCorrect() < timesCorrectReq) { //timesCorrectReq == 4
				availableItems.add(item);
			}
		}
		
		//choose 15 questions randomly
		Random randomGenerator = new Random();
		for (int count = 0; count <= maxCount && !availableItems.isEmpty(); count++) {
			int i = randomGenerator.nextInt(availableItems.size());
			currentItems.add(availableItems.remove(i)); //remove a random item and push to currentItems
		}
		
	}
	
	//return true if a question is available
	private boolean hasQuestion() {
		if(currentItems != null && !currentItems.isEmpty() && index >= 0 && index <= currentItems.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	//display the i'th question in currentItems
	private void displayQuestion() {
		if(!hasQuestion()) {
			throw new IllegalArgumentException("No questions!");
		}
		questionText.setText(currentItems.get(index).getStimulus());
	}
	
	//shuffle current questions for next iteration
	private void shuffle() {
		Random randomGenerator = new Random();
		for(int i = 1; i < currentItems.size(); i++) {
			int j = randomGenerator.nextInt(i); //j is randomly chosen from 0 to (i - 1)
			Collections.swap(currentItems, i, j); // exchange i and j of currentItems
		}
	}
	
	//handle it when user submit a correct answer
	private void correctHandling() {
		if(!hasQuestion()) {
			throw new IllegalAccessError("No available questions for correct handling!");
		}
		
		answerText.setBackground(Color.GREEN);
		resultText.setText("correct!");
		Item item = currentItems.get(index);
		int n = item.getTimesCorrect() + 1;
		item.setTimesCorrect(n);
		
		if (n >= timesCorrectReq) { //if 4 times correct, remove this item
			currentItems.remove(item);
		} else {
			index++;
		}
		
		if(currentItems.isEmpty()) {
			questionText.setText("You have already passed the whole list!");
		} else if(index >= currentItems.size()) {
			shuffle();
			index = 0;
		}
		
		if(hasQuestion()) {
			displayQuestion();//show the next question!
			answerText.setText("");
		}
	}
	
	//handling when user gives a wrong answer
	private void wrongHandling(String userAnswer) {
		if(!hasQuestion()) {
			throw new IllegalAccessError("No available questions for correct handling!");
		}
		
		Item item = currentItems.get(index);
		item.setTimesCorrect(0);
		answerText.setBackground(Color.RED); // make answer block red
		resultText.setText("The correct answer is: " + item.getResponse()
				+ "\nYour answer is: " + userAnswer 
				+ "\nPlease enter the correct answer again");
	}
	
	//listeners
	//listener for answer submit button
	public class AnswerSubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
			
			if(hasQuestion()) {
				needsSaving = true; // file changed, so needs saving
				String correctAnswer = currentItems.get(index).getResponse();
				String userAnswer = answerText.getText().trim();
				
				if(correctAnswer.equals(userAnswer)) {
					correctHandling();
				} else {
					wrongHandling(userAnswer);
				}
			}
		}
	}
	
	//load file
	public class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			//save current file before loading new file
			if (needsSaving) {
				int yesNoCancel = saveDialog();
				if(yesNoCancel == JOptionPane.YES_OPTION) {
					save();
				} else if (yesNoCancel == JOptionPane.CANCEL_OPTION) {
					return;// stop file loading
				}
			}
			
			//load file
			try {
				studyList = new StudyList();
				studyList.load();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(wholePanel, "Cannot recognize this file!");
				return;
			} catch (NullPointerException e) {
				resultText.setText("File open cancelled");
				return;
			}
			
			//initializations
			index = 0;
			currentItems = null;
			needsSaving = false;
			resultText.setText("File loaded! You can start study now.");
			answerText.setText("");
			
			if(studyList.getList().size() == 0) { //empty file loaded
				questionText.setText("The file is empty..");
				resultText.setText("The file is empty..");
			} else {
				prepareList();
				if(currentItems.isEmpty()) {
					questionText.setText("You have already passed the whole list!");
					resultText.setText("You have already passed the whole list!");
				} else {
					index = 0;
					displayQuestion();
				}
			}
		}
	}

	//save file
	public class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				studyList.save();
				resultText.setText("Your study progress has been saved!");
				needsSaving = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(wholePanel, "File save failed!");
			} catch (NullPointerException e) {
				//do nothing
			} // needs more test here!
		}
	}

	//save file as
	public class SaveAsListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				studyList.saveAs();
				resultText.setText("Your study progress has been saved!");
				needsSaving = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(wholePanel, "File save failed!");
			} catch (NullPointerException e) {
				//do nothing
			}
			//problem: no error message if user cancels file save dialog Needs to alter simpleIO.java to fix it.
		}
	}

	//close program when user click close pane in menu
	public class CloseListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if(needsSaving) {
				int yesNoCancel = saveDialog();
				saveAndQuit(yesNoCancel);
			} else {
				System.exit(0);
			}
		}
	}
	

	
	//the behavior when user click close button
	public void closingWindowDialog() {
		this.addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame)e.getSource();
				if (!needsSaving) { // file was not changed, so no need for saving
					System.exit(0);
					//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else {
					int yesNoCancel = saveDialog();
					saveAndQuit(yesNoCancel);
				}
			}
		});
	}

	//Prompt user to save before quit or open new file
	private int saveDialog() {
		int yesNoCancel = JOptionPane.showConfirmDialog(
				wholePanel,
				"Save before you exit the application?",
				"Exit Application",
				JOptionPane.YES_NO_CANCEL_OPTION);
		return yesNoCancel;
	}
	
	//save file
	private void save() {
		try {
			studyList.save();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(wholePanel, "File save failed!");
		} catch (NullPointerException e) {
			//do nothing
		}
	}
	
	//save file and quit program
	private void saveAndQuit(int yesNoCancel) {
		if (yesNoCancel == JOptionPane.YES_OPTION) {
			try {
				studyList.save();
				System.exit(0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(wholePanel, "File save failed!");
				//show message and don't exit
			} catch (NullPointerException e) {
				//do nothing and don't exit when cancel at "save As" dialog
			}
		} else if (yesNoCancel == JOptionPane.NO_OPTION) {
			System.exit(0);
		} // do nothing and don't exit when CANCEL
	}
}
