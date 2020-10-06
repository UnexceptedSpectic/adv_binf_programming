# Lab 3
This project parses a fasta or similar file having the following format:
>\>Seq1  
AAACCTTTGGGXXXXNNNAAAA  
>\>Seq2  
AAACCTTT  
AAATTTCC  
AAAAAAAAAA  
>\>Seq3  
TAACCCCGGTTTTAAACCNANEQQQAAA  


... and counts the number of nucleotide bases contained within each sequence, outputting a tab-delimited xls file with a structure shown by the following column headings:
> sequenceId, numA, numC, numG, numT, sequence

The input filename (looked for in the resources folder) and the output filename (saved to ./bin/output/) must be specified as parameters to the static `readParseSave` function of the `Parser` class. 