/**
 * 
 */
package flashCards;

import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

/**
 * @author Your Name Goes Here
 */
public class StudyGui extends JFrame {

	//menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem open = new JMenuItem("Open");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem saveAs = new JMenuItem("Save as..");
	private JMenuItem close = new JMenuItem("Close");

	//control bar
	private JPanel controlBar;
	private JButton nextButton;
	
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

	public static void main(String[] args) {
		new StudyGui().run();
	}

	public void run() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

		setMenuBar();
		setControlBar();
		setQuestionBar();
		setAnswerBar();
		//this.setBackground(Color.lightGray);
		
		//Do these in the last!
		pack();
		setVisible(true);
	}

	private void setControlBar() {
		nextButton = new JButton("Next Question");
		nextButton.setMaximumSize(new Dimension(10, 10));
		nextButton.addActionListener(new nextButtonListener());
		
		controlBar = new JPanel();
		controlBar.setLayout(new BorderLayout());
		controlBar.add(nextButton, BorderLayout.WEST);
		getContentPane().add(controlBar, BorderLayout.NORTH);
		
	}
	
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
		getContentPane().add(answer, BorderLayout.SOUTH);
		
		answerSubmit.addActionListener(new answerSubmitListener());
	}
	
	
	private void setQuestionBar() {
		questionLabel = new JLabel("Question:");
		
		questionText.setText("A string argument here for question!");
		questionText.setEditable(false);
		questionText.setPreferredSize(new Dimension(600, 100));
		questionText.setBackground(Color.WHITE);
		questionScroll = new JScrollPane(questionText);
		
		question.setLayout(new BorderLayout());
		question.add(questionLabel, BorderLayout.NORTH);
		question.add(questionScroll, BorderLayout.CENTER);
		getContentPane().add(question, BorderLayout.CENTER);
		//JScrollPane(questionScroll) contains JEditorPane(questionText), 
		//JPane(question) contains JScrollPane(questionScroll) and JLabel(questionLabel)
		
		
	}

	private void setMenuBar() {
		this.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(close);
		menuBar.setBackground(Color.GRAY);
		fileMenu.setBackground(Color.GRAY);
		
		open.addActionListener(new openListener());
		save.addActionListener(new saveListener());
		saveAs.addActionListener(new saveAsListener());
		close.addActionListener(new closeListener());
	}
	
	public class answerSubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
		}
	}
	
	public class nextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
		}
	}

	public class openListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
		}
	}
	public class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
		}
	}
	public class saveAsListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
		}
	}
	public class closeListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//do something
		}
	}

}
