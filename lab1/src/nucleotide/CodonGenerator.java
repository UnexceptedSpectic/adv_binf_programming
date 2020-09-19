package nucleotide;

import java.util.Random;

public class CodonGenerator {
		
	public static final int CODON_NUMBER = 1000;
	
	public static Random random = new Random();

	/**
	 * 
	 * @param aProb Adenine probability 
	 * @param cProb Cytosine probability
	 * @param gProb Guanine probability
	 * @param tProb Thymine probability
	 * @return The string of a random nucleotide base as determined by its probability 
	 */
	public static String generateNucleotide(Float aProb, Float cProb, Float gProb, Float tProb) {		
		
		Float randFloat = random.nextFloat();
		
		if ( randFloat >= 0 && randFloat < aProb ) {
			return "a";
		} else if ( randFloat >= aProb && randFloat < cProb + aProb ) {
			return "c";
		} else if ( randFloat >= cProb + aProb && randFloat < cProb + aProb + gProb ) {
			return "t";
		} else {
			return "g";
		}
				
	}
	
	/**
	 * Generate CODON_NUMBER random codons with base frequencies determined by the probabilities specified
	 * @param aProb Adenine probability 
	 * @param cProb Cytosine probability
	 * @param gProb Guanine probability
	 * @param tProb Thymine probability
	 */
	public static void generateCodons(Float aProb, Float cProb, Float gProb, Float tProb) {
		System.out.println("Given the A, C, G, T probabilities " 
				+ String.format("%s, %s, %s, %s, respectively", aProb, cProb, gProb, tProb));
		System.out.println("------------------------------------------------------------------------\n");
		
		String codon;
		int aaaCount = 0;
		
		for (int i = 0; i < CODON_NUMBER; i++) {
			
			codon = "";
			
			for ( int ii = 0; ii < 3; ii++) {
				codon += generateNucleotide(aProb, cProb, gProb, tProb);
			}
			
			if ( codon.equals("aaa") ) {
				aaaCount += 1; 
			}
			
//			System.out.println(codon);
		}
		
		System.out.println("Observed number of 'aaa' codon occurrences:");
		System.out.println(aaaCount);
		System.out.println("Expected number of 'aaa' codon occurrences:");
		System.out.println(Math.pow(aProb, 3) * CODON_NUMBER);
		System.out.println();
	}
	
	public static void main(String[] args) {
		generateCodons(0.25f, 0.25f, 0.25f, 0.25f);
		
		generateCodons(0.12f, 0.38f, 0.39f, 0.11f);
	}

	
}
