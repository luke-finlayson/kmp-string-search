import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Uses a given skip array to search through lines of
 * a file for some string.
 * Outputs the line and the column of the first occurrence of
 * the string on each line (if the string exists on that line)
 * 
 * Usage: java KMPsearch [skip array] [file to search]
 * 
 * 
 * Created by Luke Finlayson, 1557835
 */
public class KMPsearch {
  public static void main(String[] args) {
    // Ensure valid arguments have been passed into the program
    if (args.length != 2) {
      System.err.println("Usage: java KMPsearch [file containing skip array] [file to search]");
      return;
    }

    // The components of our skip array
    char[] word;
    ArrayList<Character> symbols;
    int[][] skipArray;

    BufferedReader reader;

    // Create the skip array from the given file
    try {
      FileReader file = new FileReader(args[0]);
      reader = new BufferedReader(file);

      String line = reader.readLine();

      // Read in the word from the table columns
      char[] columnHeaders = line.toCharArray();
      
      word = new char[(columnHeaders.length - 2) / 2];
      symbols = new ArrayList<>();
      
      // Extract the characters of the word from the column
      int j = 0;
      for (int i = 2; i < columnHeaders.length; i += 2) {
        word[j] = columnHeaders[i];

        // Read in the unique symbols at the same time
        if (!symbols.contains(word[j])) {
          symbols.add(word[j]);
        }

        j++;
      }

      skipArray = new int[word.length][symbols.size() + 1];

      // Now read in the actual skip array
      for (j = 0; j < symbols.size() + 1; j++) {
        line = reader.readLine();
        String[] row = line.split(" ");

        for (int i = 0; i < word.length; i++) {
          skipArray[i][j] = Integer.parseInt(row[i + 1]);
        }
      }

      reader.close();
    } 
    catch (Exception e) {
      System.err.println(e);
      return;
    }

    // Find the target word in the given file
    try {
      FileReader file = new FileReader(args[1]);
      reader = new BufferedReader(file);

      String line = reader.readLine();

      int lineNum = 1;

      while (line != null) {
        char[] currentLine = line.toCharArray();

        int pointer = 0;
        int suffix = 0;

        while (pointer < currentLine.length) {
          char symbol = currentLine[pointer];
          int symbolPos = symbols.indexOf(symbol);

          // Use the wildcard if symbol doesn't match
          if (symbolPos == -1) {
            symbolPos = symbols.size();
          }
          
          int skip = skipArray[suffix][symbolPos];

          // If we have found a whole word - move on to the next line
          if (skip == 0 && suffix == word.length - 1) {
            System.out.println(lineNum + " " + (pointer - suffix + 1));
            break;
          }

          // Offset the pointer in the line being read if we have to skip
          if (skip != 0) {
            // Last letter could be the start of the next word
            if (symbols.get(symbolPos) == word[0]) {
              skip--;
            }

            pointer += skip;
            suffix = 0;
          }
          // Otherwise move the pointer forwards and look for 
          // the next character in the search string
          else {
            pointer++;
            suffix++;
          }
        }

        line = reader.readLine();
        lineNum++;
      }
    }
    catch (Exception e) {
      System.err.println(e);
    }
  }
}
