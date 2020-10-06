package fasta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {
	
	private static final char[] bases = new char[] { 'A', 'C', 'G', 'T' };
		
	/**
	 * This method reads a fasta file and writes its content another file to include the following, tab-delimited columns: sequenceId, numA, numC, numG, numT, sequence.
	 * @param inputFileName The name and extension of the input fasta file to be parsed. Must be in the resources folder.
	 * @param outputFileName The desired name and extension for the output file, which is saved in /bin/output
	 */
	public static void readParseSave(String inputFileName, String outputFileName) throws IOException, URISyntaxException {
		// Read in fasta file from resources folder
		URL uri = Parser.class.getResource("/" + inputFileName);
		if (uri == null) {
		    throw new IllegalArgumentException("file not found!");
		} else {
			BufferedReader reader = new BufferedReader(new FileReader(new File(uri.toURI())));
			
			StringBuffer seqBuffer = new StringBuffer();
			
			// Prepare output directory
			Path ROOT_PATH = Paths.get(Parser.class.getResource("/").toURI());
			Path OUTPUT_PATH = Paths.get(ROOT_PATH.toAbsolutePath() + "/output/");
			if (!Files.exists(OUTPUT_PATH)) {
				Files.createDirectories(OUTPUT_PATH);
			}
			// Initialize a writer pointing to the specified file
			BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH + "\\" + outputFileName));
			// Write column names to the output file
			writer.write("sequenceID" + "\t");
			for (char base : bases) {
				writer.write("num" + Character.toUpperCase(base) + "\t");
			}
			writer.write("sequence" + "\n");

			String line = reader.readLine();
			while (line != null) {
				if (line.startsWith(">")) {
					// Save the header/sequence id to output file
					writer.write(line.substring(1) + "\t");
				} else {
					// Save the sequence/partial sequence to a temporary string buffer
					seqBuffer.append(line);
				}
				line = reader.readLine();
				// Save sequences followed by empty lines or sequences headers
				if ((line == null || line.startsWith(">")) && seqBuffer.length() > 0) {
					// Write the last sequence and its base counts to the output file
					for (char base : bases) {
						writer.write(numberOfCharacters(base, seqBuffer) + "\t");
					}
					writer.write(seqBuffer + "\n");
					// Remove the sequence from its string buffer
					seqBuffer.delete(0, seqBuffer.length());
				}
			}
			// Print summary and clean up
			System.out.println("Saved parsed fasta data to " + OUTPUT_PATH + "\\" + outputFileName);
			reader.close();
			writer.close();
		}
	}
	
	
	/**
	 * This method determines the number of times a character occurs in a given string
	 * @param c The character being counted.
	 * @param s The string being assessed.
	 * @return The number of occurrences of c in s.
	 */
	private static long numberOfCharacters(char c, StringBuffer s) {
		return s.toString()
				.toUpperCase().chars()
				.filter(ch -> ch == Character.toUpperCase(c))
				.count();
	}
	
	public static void main(String[] args) {
		try {
			readParseSave("test.fa", "out.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
