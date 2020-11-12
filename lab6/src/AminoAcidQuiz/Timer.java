package AminoAcidQuiz;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Timer implements Runnable {

	private int duration;
	private JLabel timeLabel;
	private JButton stopButton;
	
	@Override
	public void run() {
		for (int i = duration; i >= 0; i--) {
			try {
				final Integer secondsRemaining = new Integer(i);
				SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						timeLabel.setText("Seconds Remaining: " + secondsRemaining);
						
					}
				});
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				break;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					stopButton.doClick();						
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Timer(int duration, JLabel timeLabel, JButton stopButton) {
		this.duration = duration;
		this.timeLabel = timeLabel;
		this.stopButton = stopButton;
	}

}
