/** Original Question : https://stackoverflow.com/questions/66836500/
 *
 * public class PrimeNumbers {
 *     public static void main(String[] args) {
 *         System.out.println(getPrimeNumbers(3, 10));
 *     }
 *
 *     public static List<Integer> getPrimeNumbers(int lowerBound, int upperBound) {
 *         List<Integer> numbers = new ArrayList<>();
 *         for (int i = lowerBound; i <= upperBound; i++) {
 *             for (int j = 2; j < upperBound; j++) {
 *                 if (i % j == 0) {
 *                     break;
 *                 } else {
 *                     numbers.add(i);
 *                 }
 *             }
 *         }
 *         return numbers;
 *     }
 * }
 * This is the code so far but the prime numbers keep repeating, I need help to stop the repetition
 *
 */


package Others_git;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindPrimeNumbersBetweenRange {

    public static void main(String[] args) {
        System.out.println(getPrimeNumbers(3, 10));
        System.out.println(getPrimeNumbersStream(3, 10));
    }

    public static List<Integer> getPrimeNumbers(int lowerBound, int upperBound) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = lowerBound; i <= upperBound; i++)
            if(isPrime(i))
                numbers.add(i);
        return numbers;
    }

    private static boolean isPrime(int i) {
        final int upperLimit = (int) Math.sqrt(i);
        for (int j = 2; j <= upperLimit; j++)
            if (i % j == 0)
                return false;
        return true;
    }

    public static List<Integer> getPrimeNumbersStream(int lowerBound, int upperBound) {
        return IntStream.range(lowerBound, upperBound)
                .filter(FindPrimeNumbersBetweenRange::isPrime)
                .boxed()
                .collect(Collectors.toList());
    }
}
