package primeFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;

public class Primerizer implements Runnable {
	
	private final long number;
	private JButton stopButton;
	private List<Long> primes;
	private long currentNumber;

	private void findPrimes() {
		primes = Collections.synchronizedList(new ArrayList<Long>());
		for (currentNumber = 0; currentNumber<number; currentNumber++) {
			if (isPrime(currentNumber)) {
				primes.add(currentNumber);
			}
		}
	}
	
	private boolean isPrime(long i2) {
	    if (i2%2 == 0) return false;
	    for(int i = 3; i*i <= i2; i += 2) {
	        if(i2%i == 0)
	            return false;
	    }
	    return true;
	}
	
	public String getStatus() {
		return "STATUS: " + primes.size() + " prime numbers found.\n" 
				+ "NUMBER CHECKED: " + currentNumber + "\n"
				+ "TOTAL NUMBERS: " + number + "\n"
				+ "PROGRESS: " + Math.round((double) currentNumber/number * 100) + "% done. ";
	}
	
	public List<Long> getResult() {
		return primes;
	}

	public Primerizer(long number, JButton stopButton) {
		this.number = number;
		this.stopButton = stopButton;
	}

	@Override
	public void run() {
		findPrimes();
		stopButton.doClick();
	}
	
	
	
}
