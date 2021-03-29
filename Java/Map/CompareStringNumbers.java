/**
 *  Original Question : https://stackoverflow.com/questions/66857504
 *
 *  I have been given numbers as words {"one", "two", "three", "four", "five", "six",
 *  "seven", "eight", "nine", "ten"};(numbers are only up-to 10) and I my task is
 *  to compare given two input strings to each other.
 *
 * It should basically work as you compare two numbers:
 *
 * compare(1, 1) -> 0;
 * compare(1, 3) -> 1 < 3 as -1;
 * compare(5, 2) -> 5 > 2 as 1;
 * What would be the best suitable way to compare two strings like this?
 *
 * Result would look something like this:
 *
 * compare("one", "one") -> 0;
 * compare("one", "three") -> -1;
 * compare("five", "two") -> 1;
 * public int compare(String a, String b) {
 *         return 0;
 *     }
 */

package Others_git;

import java.util.Map;

public class CompareStringNumbers {

    enum Values {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN;
    }
    public static int compare_enum(String a, String b) {
        Values vA = Values.valueOf(a.toUpperCase());
        Values vB = Values.valueOf(b.toUpperCase());
        return Integer.compare(vA.compareTo(vB), 0);
    }


    private final static Map<String, Integer> STRING_VALUE =
            Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5,
                    "six", 6, "seven", 7, "eight", 8, "nine", 9, "ten", 10);

    public static int compare_map(String a, String b) {
        return Integer.compare(STRING_VALUE.get(a),STRING_VALUE.get(b));
    }

    public static void main(String[] args) {
        System.out.println(compare_map("one", "one"));
        System.out.println(compare_map("one", "three"));
        System.out.println(compare_map("five", "two"));


        System.out.println(compare_enum("one", "one"));
        System.out.println(compare_enum("one", "three"));
        System.out.println(compare_enum("five", "two"));
    }
}
