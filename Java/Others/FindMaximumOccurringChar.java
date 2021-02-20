/** Original Question : https://stackoverflow.com/questions/65180480/
 *  I'm learning Data Structures and Algorithms and I've a small problem of understanding
 *  the process of find which char occurs the most in a string.
 *
 * I understand the general goal - having an array that represent the count of a specific char,
 * I obviously understand how to find max in array but I've big problem with this chuck of
 * code(code from https://www.geeksforgeeks.org/return-maximum-occurring-character-in-the-input-string/):
 *
 *         int count[] = new int[256];
 *
 *           for (int i=0; i<str.length(); i++)
 *             count[str.charAt(i)]++; <-- what I don't understand
 * I'm initializing count array to hold ints, but inside the for loop
 * I'm searhing for specific char in a string, so for example:
 *
 * count["t"]++
 * So it basically telling me "give me the value of index "t"?
 * how can I even search with chararter where I should search with index?
 *
 * Also in kotlin I'm getting expection (count[str.get(i)]) that it's expecting int, not char.
 *
 * I probably missed fundamental concept that prevent me from understand this,
 * but after short google search I didn't find much.
 *
 */


package Others_git;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FindMaximumOccurringChar {

    private static int[] countChar(String str) {
        int[] count = new int[str.chars().max().orElse(0) + 1];
        for (int i = 0; i< str.length(); i++)
            count[str.charAt(i)]++;
        return count;
    }

    public static void main(String[] args) {
        String str = "miaumiauuuuu";
        int[] count = countChar(str);
        String str_without_duplicated_char = Arrays.stream(str.split(""))
                .distinct()
                .collect(Collectors.joining());

        for (int i=0; i<str_without_duplicated_char.length(); i++){
            System.out.println("The char '"+str_without_duplicated_char.charAt(i)+"' shows up "
                    + count[str_without_duplicated_char.charAt(i)] +" times");
        }
    }
}
