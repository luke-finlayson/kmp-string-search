# KMP String Search
A simple KMP string search program that generates a skip array from a string and searches through a given file for that string.

Creating the skip array: `java KMPtable [search string] > [output file]`

Searching a file: `java KMPsearch [file containing skip array] [file to search]`