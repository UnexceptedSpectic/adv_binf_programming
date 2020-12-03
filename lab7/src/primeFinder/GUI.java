package primeFinder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class GUI extends JFrame {
	
private static final long serialVersionUID = 1L;
 
// Top panel components
private JTextArea statusTextArea = new JTextArea();
JLabel timeElapsedLabel = new JLabel("Time Elapsed: 0 seconds");

// Bottom panel components
private JButton startButton = new JButton("Start");
private JButton stopButton = new JButton("Stop");

// Primerizer runnable
Primerizer primerizer;
private Thread primerizerThread;

// Swing timer
double startMS;
Timer timer;
double timeElapsed = 0;

	private JPanel getTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		statusTextArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(statusTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(timeElapsedLabel, BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel getBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				String number = JOptionPane.showInputDialog(null, "Enter a number to primerize");
				if (isNumeric(number)) {
					statusTextArea.setText("");
					stopButton.setEnabled(true);
					startButton.setEnabled(false);
					// start primerization
					primerizer = new Primerizer(Long.parseLong(number), stopButton);
					primerizerThread = new Thread(primerizer);
					primerizerThread.start();
					// Start a timer that triggers an action once every second
					// Sometimes the action is triggered more frequently than once/second. Not sure why...
					// Restarting the app fixes the above issue
					startMS = System.currentTimeMillis();
					timer = new Timer(1000, new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if (primerizerThread.isAlive()) {
								// Update the time elapsed label
								timeElapsed += 1;
								timeElapsedLabel.setText("Time Elapsed: " + Double.toString(timeElapsed) + " seconds");
								statusTextArea.setText(primerizer.getStatus());
							}
							
						}
					});
					timer.restart();
				} else {
					statusTextArea.setText("ERROR: The input you provided is not a number");
				}
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
				// ConcurrentModificationException occasionally thrown if copy of array isn't made. Not sure why it would be changing...
				List<Long> result = new ArrayList<Long>(primerizer.getResult());
				statusTextArea.setText(primerizer.getStatus() + "\n" + 
						"Result:\n" +
						result.subList(0, 50) + 
						" ............. " + 
						result.subList(result.size() - 50, result.size() - 1));
				timeElapsedLabel.setText("Running Time: " +String.format("%.2f", (System.currentTimeMillis() - startMS) / 1000) + " seconds");
				timeElapsed = 0;
				
				primerizerThread.stop();
				timer.stop();
			}
		});
		
		panel.add(startButton);
		panel.add(stopButton);
		return panel;
	}
	
	private boolean isNumeric(final String str) {

        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }

	public GUI() {
		super("Prime Finder");
		setSize(1000, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		stopButton.setEnabled(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
