package fasta;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Objectifier {
	
	public static void main(String[] args) {
		try {
			final String INPUT_FILENAME = "test.fa";
			final String OUTPUT_FILENAME = "uniqueSequencesAndCounts.txt";
			
			// Parse an input fasta file and generate a list of Sequence objects
			List<Sequence> fastaSequenceList = Sequence.readFastaFile(INPUT_FILENAME);
			// Eclipse doesn't print long strings to the console, so trying to print the last two in this sample list will fail.
			// The loop below excludes the last two sequences, which are complete mitochondrion and chloroplast genomes
			for (int i=0; i<fastaSequenceList.size() - 2; i++) {
				System.out.println(fastaSequenceList.get(i).getHeader());
				System.out.println(fastaSequenceList.get(i).getSequence());
				System.out.println(fastaSequenceList.get(i).getGCRatio());
				System.out.println(fastaSequenceList.get(i).getOccurrances());
			}
			System.out.println("Saved a list of Sequence objects to memory. Length: " + fastaSequenceList.size());
			
			// Write unique sequences and their occurrence numbers to an output file
			// Prepare output directory
			Path ROOT_PATH = Paths.get(Objectifier.class.getResource("/").toURI());
			Path OUTPUT_PATH = Paths.get(ROOT_PATH.toAbsolutePath() + "/output/");
			if (!Files.exists(OUTPUT_PATH)) {
				Files.createDirectories(OUTPUT_PATH);
			}
			System.out.println();
			Sequence.writeUnique(new File(Objectifier.class.getResource("/" + INPUT_FILENAME).toURI()), 
					Paths.get(OUTPUT_PATH.toAbsolutePath() + "/" +  OUTPUT_FILENAME).toFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
