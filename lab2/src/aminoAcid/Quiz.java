package aminoAcid;

import java.util.*;  

public class Quiz {
	
	private final Random RANDOM = new Random();;
	private final Scanner SCANNER = new Scanner(System.in); 
	private final Map<String, String> AA_CODE_DICT = new HashMap<>();
	private String questionAA;
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	private long startTime;
	private int duration;
	private static final String[] AA_CODES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };
	private static final String[] AA_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};	
	
	public Quiz(int duration) {
		this.duration = duration;
		// Initialize dictionary of single-letter amino acid codes
		for (int i = 0; i < Quiz.AA_CODES.length; i++) {
			AA_CODE_DICT.put(Quiz.AA_NAMES[i], Quiz.AA_CODES[i]);
		}
		this.startTime = System.currentTimeMillis();
	}

	public void start() {
		printIntro();
		while (timeElapsed() <= duration * 1000 ) {
			printAnswerSummary();
		}
		printFinalSummary();
	}
	
	private long timeElapsed() {
		return System.currentTimeMillis() - startTime;
	}
	
	private void printIntro() {
		print("Welcome to the amino acid quiz!");
		print(String.format("Enter as many amino acid single-letter codes as you can in %s seconds.\n\n", this.duration));
	}
	
	private String askQuestion() {
		questionAA = getRandomAA();
		print(String.format("Code for %s:", questionAA));
		return SCANNER.next();
	}
	
	private Boolean answerCorrect(String answer) {
		// Only count responses made in the allotted quiz time
		if (timeElapsed() <= duration * 1000) {
			if (answer.toLowerCase().equals(AA_CODE_DICT.get(questionAA).toLowerCase())) {
				correctAnswers += 1;
				return true;
			} else {
				incorrectAnswers += 1;
				return false;
			}
		} else {
			return null;
		}
	}
	
	private void printAnswerSummary() {
		String answer = askQuestion();
		// Allow user to break out of the quiz
		if (answer.toLowerCase().equals("quit")) {
			duration = (int) (timeElapsed()/1000 - 100);
		} else {
			Boolean correctness = answerCorrect(answer);
			if (correctness != null) {
				if (correctness) {
					print(String.format("%s ; score = %s ; seconds = %s", "correct", correctAnswers, timeElapsed()/1000));
				} else {
					print(String.format("%s (should be %s); score = %s ; seconds = %s", "incorrect", AA_CODE_DICT.get(questionAA), correctAnswers, timeElapsed()/1000));
				}
			} else {
				print("The allotted time ran out and your answer was not counted");
			}
		}
	}
	
	private void printFinalSummary() {
		print(String.format("\n\nTotal correct: %s \nTotal incorrect: %s", correctAnswers, incorrectAnswers));
	}
	
	private String getRandomAA() {
		return AA_CODE_DICT
				.keySet()
				.toArray()[RANDOM.nextInt(AA_CODE_DICT.size())]
						.toString();
	}
	
	private static void print(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		
		// Define quiz duration
		int duration;
		if (args.length > 0) {
			duration = Integer.parseInt(args[0]);
		} else {
			duration = 30;
		}
		
		Quiz quiz = new Quiz(duration);
		quiz.start();
				
	}

}
