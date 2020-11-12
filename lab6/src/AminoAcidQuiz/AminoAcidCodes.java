package AminoAcidQuiz;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AminoAcidCodes {
	
	public final Map<String, String> MAP = new HashMap<>();
	private static final String[] CODES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };
	private static final String[] NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	private final Random RANDOM = new Random();;
	
	public AminoAcidCodes() {
		for (int i = 0; i < CODES.length; i++) {
			MAP.put(NAMES[i], CODES[i]);
		}
	}
	
	public String getRandomAA() {
		return MAP
				.keySet()
				.toArray()[RANDOM.nextInt(MAP.size())]
						.toString();
	}
	
}
