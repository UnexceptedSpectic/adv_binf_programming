# Lab 4

This project uses the `readFastaFile` method to parse a fasta file and return a list of `Sequence` objects, each containing the following properties:
- `header`
- `sequence`
- `gcRatio`
- `occurrences`

After calling the `readFastaFile` method, the `occurrences` fields are still unset and default to 0.  

The `writeUnique` method extends the `readFastaFile` method to set these fields, sort the `Sequence` list by `occurrences` in ascending order, and write unique sequences and their occurrence counts to an output file in the following format:  
>\>4\
CACAATTATTTGAGCCTTTA...  
\>3\
aagtatatatatatatatat...  
\>1\
ATCGGGTGCCACGTCCCGGT...  

The test file inlcuded in the `resources` folder of this project contains one sequence with 4 occurrences, another with 3, and the rest with 1.