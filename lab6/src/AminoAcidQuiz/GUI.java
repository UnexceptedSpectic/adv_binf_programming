package AminoAcidQuiz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int duration;
	private Thread timerThread;
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	private AminoAcidCodes aminoAcidCodes = new AminoAcidCodes();
	private String currentAA;
	
	// Top panel elements
	private JLabel correctAnswersJLabel = new JLabel("Correct Answers: 0");
	private JLabel incorrectAnswersJLabel = new JLabel("Incorrect Answers: 0");
	private JLabel timerTextLabel = new JLabel();
	
	// Center panel elements
	private JLabel questionJLabel = new JLabel(generateAA());
	private JTextField answerTextField = new JTextField();
	private JButton submitAnswerButton = new JButton("Submit");
	private JLabel feedbackLabel = new JLabel();
	
	// Bottom panel elements
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");

	private JPanel getTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3, 50, 0));
		panel.setBorder(new EmptyBorder(5, 15, 0, 15));
		panel.add(correctAnswersJLabel);
		panel.add(timerTextLabel);
		panel.add(incorrectAnswersJLabel);
		return panel;
	}

	private JPanel getCenterPannel() {
		JPanel answerPanel = new JPanel();
		answerPanel.setLayout(new GridLayout(0, 2, 0, 0));
		answerPanel.add(answerTextField);
		answerPanel.add(submitAnswerButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
		centerPanel.add(questionJLabel);
		centerPanel.add(answerPanel);
		centerPanel.add(feedbackLabel);
		
		submitAnswerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String answer = answerTextField.getText();				
				String correctAnswer = aminoAcidCodes.MAP.get(currentAA).toLowerCase();
				
				if (correctAnswer.equals(answer.toLowerCase())) {
					correctAnswers += 1;
					correctAnswersJLabel.setText("Correct Answers: " + correctAnswers);
					feedbackLabel.setText("Correct!");
				} else {
					incorrectAnswers += 1;
					incorrectAnswersJLabel.setText("Incorrect Answers: " + incorrectAnswers);
					feedbackLabel.setText("Nope! Correct Answer: " + correctAnswer);
				}
				
				questionJLabel.setText(generateAA());
				answerTextField.setText("");
				answerTextField.requestFocusInWindow();
			}
		});
				
		return centerPanel;
	}

	private JPanel getBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(startButton);
		panel.add(stopButton);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				correctAnswersJLabel.setText("Correct Answers: 0");
				incorrectAnswersJLabel.setText("Incorrect Answers: 0");
				timerThread = new Thread(new Timer(duration, timerTextLabel, stopButton));
				timerThread.start();
				answerTextField.requestFocusInWindow();
				submitAnswerButton.setEnabled(true);
				stopButton.setEnabled(true);
				startButton.setEnabled(false);
			}
		});

		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitAnswerButton.setEnabled(false);
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
				timerThread.interrupt();
			}
		});
		return panel;
	}
	
	private String generateAA() {
		currentAA = aminoAcidCodes.getRandomAA();
		return "What is the single-letter AA code for " + currentAA + "?";
	}

	public GUI(int quizTime) {
		super("Amino Acid Quiz");
		this.duration = quizTime;
		timerTextLabel.setText("Seconds Remaining: " + duration);;
		setSize(550, 175);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCenterPannel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		submitAnswerButton.setEnabled(false);
		stopButton.setEnabled(false);
		// Allow submitting answer using ENTER key
		getRootPane().setDefaultButton(submitAnswerButton);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new GUI(30);
	}
}

