/** Original Question : https://stackoverflow.com/questions/65586621/
 *
 * calculate supremum of a given number
 *
 * So my question is to get a number as an input (number should not have duplicate digits)
 * and the program should find the smallest number between the larger numbers of
 * input by swapping the digits :||
 *
 * example:
 *
 * 129 -- (192), 912, 921, 291
 *
 * 32874 -- (34278), 47823, 43278
 *
 * 123456 -- (123465), 132465, 321645
 *
 * i tried this for a 10 digit number but it didn't work
 *
 * public static long findSup(long num) {
 *
 *     int[] digits = new int[10];
 *
 *     for (int i = 9; i >= 0; i--) {
 *
 *         long temp = (num % 10);
 *         digits[i] = (int) temp;
 *         num /= 10;
 *
 *     }
 *
 *     boolean stop = false;
 *     for (int i = 9; i >= 0 && !stop; i--) {
 *
 *         for (int j = 8; j >= 0 && !stop; j--) {
 *
 *             if (digits[i] > digits[j]) {
 *                 int temp = digits[j];
 *                 digits[j] = digits[i];
 *                 digits[i] = temp;
 *
 *                 int min = Math.min(i, j);
 *
 *                 for (int k = 9; k > min; k--) {
 *                     for (int l = 8; l > min; l--) {
 *                         if (digits[k] < digits[l]) {
 *                             temp = digits[k];
 *                             digits[k] = digits[l];
 *                             digits[l] = temp;
 *                         }
 *                     }
 *                 }
 *
 *                 stop = true;
 *             }
 *
 *         }
 *
 *     }
 *
 *     System.out.println(Arrays.toString(digits));
 *     int j = 0;
 *     for (int i = digits.length - 1; i >= 0 ; i--) {
 *         result =  (long) (result + (Math.pow(10, j++) * digits[i]));
 *     }
 *
 *     return result;
 * }
 *
 */

package Others_git;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Supremun {

    public static String toString(int [] digits) {
        StringBuilder result = new StringBuilder();
        for (int a : digits) {
            result.append(a);
        }
        return result.toString();
    }

    private static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }


    public static void combinations(int n, int [] elements, List<int[]> combinations) {
        if(n == 1)
            combinations.add(elements.clone());
        else {
            for(int i = 0; i < n-1; i++) {
                combinations(n - 1, elements, combinations);
                if(n % 2 == 0) swap(elements, i, n-1);
                else swap(elements, 0, n-1);
            }
            combinations(n - 1, elements, combinations);
        }
    }

    public static long findSup(long num) {
        int[] digits =  Long.toString(num).chars().map(c -> c-'0').toArray();
        List<int[]> comb = new ArrayList<>();
        combinations(digits.length, digits, comb);
        long[] possible_values = comb.stream().map(Supremun::toString).mapToLong(Long::parseLong).toArray();
        System.out.println(possible_values.length);
        Arrays.sort(possible_values);
        for(int i = 0; i < possible_values.length - 1; i++){
            if(possible_values[i] == num){
                return possible_values[i+1];
            }
        }
        return -1; // undefined
    }

    public static void main(String[] args) {

        System.out.println(findSup(219));
        System.out.println(findSup(32874));

        double start = System.nanoTime();
        System.out.println(findSup(1234567089));
        double end = System.nanoTime();
        System.out.println((double) (end - start) / 1_000_000_000.0);
    }
}