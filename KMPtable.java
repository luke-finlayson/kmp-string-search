import java.util.ArrayList;

/**
 * Produces a skip array for some search string.
 * 
 * Usage: java KMPtable [search string]
 * 
 * 
 * Created by Luke Finlayson, 1557835
 */
public class KMPtable {
  public static void main(String[] args) {
    // We only want one arg - the word to generate the table for
    if (args.length != 1) {
      System.err.println("Usage: java KMPtable [word]");
      return;
    }

    char[] word = args[0].toCharArray();
    ArrayList<Character> symbols = new ArrayList<>();

    // Generate the list of unique symbols
    for (char c : word) {
      if (!symbols.contains(c)) {
        symbols.add(c);
      }
    }

    // Store skip array in 2d matrix
    int[][] skipArray = new int[word.length][symbols.size() + 1];

    // Calculate each row of the skip array
    for (int j = 0; j < symbols.size(); j++) {
      char symbol = symbols.get(j);

      // Calculate the amount to skip by for the current symbol combination
      for (int i = 0; i < word.length; i++) {
        skipArray[i][j] = calculateSkip(word, symbol, i);
      }
    }

    // Add the wildcard
    for (int i = 0; i < word.length; i++) {
      skipArray[i][symbols.size()] = i + 1;
    }
    symbols.add('*');

    // Output skip array in table format
    String output = "  ";

    for (char c : word) {
      output += c + " ";
    }

    output += "\n";

    for (int j = 0; j < symbols.size(); j++) {
      output += symbols.get(j) + " ";

      for (int i = 0; i < word.length; i++) {
        output += skipArray[i][j] + " ";
      }

      output += "\n";
    }

    System.out.println(output);
  }

  /**
   * Calculates the minimum amount of characters needed to offset a pattern
   * to match a word prefix
   * @param word The target word
   * @param symbol The last symbol found in the pattern
   * @param position The position of the symbol in the word
   */
  private static int calculateSkip(char[] word, char symbol, int position) {
    // Copy the previous character of the word and the current symbol into
    // an array to form a comparison pattern
    char[] pattern = new char[position + 1];
    System.arraycopy(word, 0, pattern, 0, position);
    pattern[position] = symbol;

    int k = 0;
    boolean match;

    do {
      // Assume there is a match
      match = true;

      // We only the portion of the word that has already been validated
      int i = k;
      int length = pattern.length - k;

      // Check if offset pattern matches word
      for (int j = 0; j < length; j++) {
        if (pattern[i] != word[j]) {
          k++;
          match = false;
          break;
        }

        i++;
      }

    } while (!match);

    return k;
  }
}
