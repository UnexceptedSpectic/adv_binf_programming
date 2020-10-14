package fasta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

public class Sequence implements Comparable<Sequence> {

	private String header;
	private String sequence;
	private float gcRatio;
	private int occurrences;
	
	public void setHeader(String header) {
		this.header = header;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setGcRatio(float gcRatio) {
		this.gcRatio = gcRatio;
	}
	
	public void setOccurrances(int occurrances) {
		this.occurrences = occurrances;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getSequence() {
		return sequence;
	}
	
	public float getGCRatio() {
		return gcRatio;
	}
	
	public int getOccurrances() {
		return occurrences;
	}
	
	@Override
	public int compareTo(Sequence o) {
		return Integer.compare(o.getOccurrances(), this.occurrences);
	}
	
	public Sequence() {}
	
	public Sequence(Sequence fastaSequence) {
		this.header = fastaSequence.getHeader();
		this.sequence = fastaSequence.getSequence();
		this.gcRatio = fastaSequence.getGCRatio();
		this.occurrences = fastaSequence.getOccurrances();
	}
	
	/**
	 * This method reads a fasta file and returns a list of FastaSequence objects, each containing header, sequence, and GCRatio variables.
	 * @param inputFileName The name and extension of the input fasta file to be parsed. Must be in the resources folder.
	 */
	public static List<Sequence> readFastaFile(String inputFileName) throws IOException, URISyntaxException {
		List<Sequence> fastaSequenceList = new ArrayList<Sequence>();
		// Read in fasta file from resources folder
		URL uri = Objectifier.class.getResource("/" + inputFileName);
		if (uri == null) {
		    throw new IllegalArgumentException("file not found!");
		} else {
			BufferedReader reader = new BufferedReader(new FileReader(new File(uri.toURI())));
			StringBuffer seqBuffer = new StringBuffer();

			Sequence fastaSequence = new Sequence();
			String line = reader.readLine();
			while (line != null) {
				if (line.startsWith(">")) {
					// Save the header/sequence id to the FastaSequence object
					fastaSequence.setHeader(line.substring(1) + "\t");
				} else {
					// Save the sequence/partial sequence to a temporary string buffer
					seqBuffer.append(line);
				}
				line = reader.readLine();
				// Save sequences followed by empty lines or sequences headers
				if ((line == null || line.startsWith(">")) && seqBuffer.length() > 0) {
					// Save the sequence and its GC content to the FastaSequence object
					fastaSequence.setSequence(seqBuffer.toString());
					fastaSequence.setGcRatio(numberOfGC(seqBuffer) / fastaSequence.getSequence().length());
					// Append the FastaSequence object to the output list
					fastaSequenceList.add(new Sequence(fastaSequence));
					// Remove the sequence from its string buffer
					seqBuffer.delete(0, seqBuffer.length());
				}
			}
			// Print summary and clean up
			System.out.println("Finished parsing fasta data in " + inputFileName);
			reader.close();
			return fastaSequenceList;
		}
	}
	
	/**
	 * This method uses the readFastaFile method to parse a fasta file and save its sequences as a list of Sequence objects.
	 * It then find unique sequences, sorts them by their occurrences, and writes them as well as their occurrence numbers (as headers) to an output file.
	 * @param inFile The fasta file to be parsed
	 * @param outFile The file in which to save the sorted sequences
	 */
	public static void writeUnique(File inFile, File outFile ) throws IOException, URISyntaxException {
		
		List<Sequence> fastaSequenceList = Sequence.readFastaFile(inFile.getName());
		HashMap<String, Sequence> numberMap = new HashMap<String, Sequence>();
		for (Sequence sequence : fastaSequenceList) {
			Sequence seq = new Sequence(sequence);
			if (numberMap.containsKey(sequence.getSequence())) {
				seq.setOccurrances(numberMap.get(sequence.getSequence()).getOccurrances() + 1);
				numberMap.put(sequence.getSequence(), seq);
			} else {
				seq.setOccurrances(1);
				numberMap.put(sequence.getSequence(), seq);
			}
		}
		// Create and sort a new list of fasta Sequence objects
		List<Sequence> fastaSequenceOccurrencesList = new ArrayList<Sequence>(numberMap.values());
		Collections.sort(fastaSequenceOccurrencesList);
		// Initialize a writer pointing to the specified file
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		for (Sequence sequence : fastaSequenceOccurrencesList) {
			writer.write(">" + sequence.getOccurrances() + "\n");
			writer.write(sequence.getSequence() + "\n");
		}
		System.out.println(fastaSequenceOccurrencesList.size() + " unique sequences were saved to the file " + outFile.getName());
		writer.close();
	}
	
	
	/**
	 * This method determines the GC content in a given string
	 * @param s The string being assessed.
	 * @return The number of occurrences of 'G' or 'C' in s.
	 */
	private static float numberOfGC(StringBuffer s) {
		return s.toString()
				.toUpperCase().chars()
				.filter(ch -> (Character.toUpperCase(ch) == 'G' || Character.toUpperCase(ch) == 'C'))
				.count();
	}
}
