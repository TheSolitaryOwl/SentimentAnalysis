//=================================================================================================
// Program		: Sentiment Analysis
// Class		: GUI.java
// Developer	: Elias Nyantakanya, Zachary Rowton
// Abstract		: GUI class for interacting with the classifier. This is the main class, so
// 				  it also loads and runs all other classes as it builds the GUI.
//=================================================================================================
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.Label;

public class GUI {

	private JFrame frame;
	private final Action action = new SwingAction();
	JTextPane textPane = new JTextPane();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		
	        
	        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public GUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 734, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Naive Bayes Classifier");
		Label label = new Label("Results ");
		
		DataSeparator dataSeparator = new DataSeparator();
        NaiveBayes naiveBayes = new NaiveBayes();

        LinkedList<String> testingData = new LinkedList<String>();
        
        FileManager.readIntoLinkedList("positiveTesting.txt", testingData);
        naiveBayes.classifyDocuments(testingData, 0);
        
        FileManager.readIntoLinkedList("negativeTesting.txt", testingData);
        naiveBayes.classifyDocuments(testingData, 1);
        
        FileManager.readIntoLinkedList("neutralTesting.txt", testingData);
        naiveBayes.classifyDocuments(testingData, 2);
        naiveBayes.control = -1;
        
        label.setText(naiveBayes.metrics());
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		
		JButton button_1 = new JButton("Reset");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textPane.setText(null);
				label.setText(naiveBayes.metrics());
			}
		});
		button_1.setForeground(SystemColor.textText);
		button_1.setBackground(SystemColor.scrollbar);
		button_1.setActionCommand("Reset");
		button_1.setBounds(524, 249, 184, 40);
		frame.getContentPane().add(button_1);
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(10, 0, 205, 443);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		Button button = new Button("Rate Sentiment");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
								
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText("Naive Bayes: " + naiveBayes.classify(textPane.getText()));
				
			}
		});
		button.setActionCommand("Rate Sentiment");
		button.setForeground(SystemColor.desktop);
		button.setBackground(SystemColor.scrollbar);
		button.setBounds(263, 249, 184, 40);
		frame.getContentPane().add(button);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.controlDkShadow);
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(709, 279, -476, -5);
		frame.getContentPane().add(separator);
		
		JLabel lblDocument = new JLabel("Document");
		lblDocument.setBounds(413, 36, 87, 14);
		frame.getContentPane().add(lblDocument);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		lblX.setForeground(Color.RED);
		lblX.setBackground(Color.BLUE);
		lblX.setBounds(678, 0, 30, 24);
		frame.getContentPane().add(lblX);
		
		textPane = new JTextPane();
		textPane.setBounds(263, 61, 445, 162);
		frame.getContentPane().add(textPane);
		
		label.setBackground(SystemColor.scrollbar);
		label.setBounds(263, 356, 445, 50);
		frame.getContentPane().add(label);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}