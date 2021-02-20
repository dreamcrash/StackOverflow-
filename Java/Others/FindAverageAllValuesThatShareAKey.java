/** Original Question : https://stackoverflow.com/questions/65287747/
 *
 * I'm having a lot of trouble with trying to average the values of a map in java.
 * My method takes in a text file and sees the average length of each word starting with
 * a certain letter (case insensitive and goes through all words in the text file.
 *
 * For example, let's say I have a text file that contains the following::
 *
 * "Apple arrow are very common Because bees behave Cant you come home"
 * My method currently returns:
 *
 * {A=5, a=8, B=7, b=10, c=10, C=5, v=4, h=4, y=3}
 * Because it is looking at the letters and finding the average length of the word, but it is still case sensitive.
 *
 * It should return:
 *
 * {A=5, a=8, B=7, b=10, c=10, C=5, v=4, h=4, y=3}
 *
 * {a=4.3, b=5.5, c=5.0, v=4.0, h=4.0, y=3}
 *
 */
package Others_git;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FindAverageAllValuesThatShareAKey {

    public static void main(String[] args) {
        String str = "Apple arrow are very common Because bees behave Cant you come home";
        Arrays.stream(str.split(" "))
                .collect(Collectors.groupingBy(s -> String.valueOf(Character.toLowerCase(s.charAt(0))), Collectors.averagingDouble(String::length)))
                .entrySet().forEach(System.out::println);
    }
}
