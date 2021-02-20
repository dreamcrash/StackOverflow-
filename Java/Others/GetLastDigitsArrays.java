/** Original Question : https://stackoverflow.com/questions/64958021/
 *
 * Returning the last digit of every number in an array
 *
 * My problem is that I'm trying to return the last digit of every element
 * in an int array but the function below rather returns the last element of the array.
 * How would I go about doing this.
 *
 * Array: {10, 12, 41, 23, 71}
 *
 * Expected output: 0, 2, 1, 3, 1
 *
 * public class TestForEachLoop
 * {
 *     private int[] iLoop = new int[5];
 *
 *     public int getLastDigit(){
 *
 *         int[] iLoop = new int[]{10, 12, 41, 23, 71};
 *         int lastnumber = iLoop[iLoop.length - 1];
 *         return lastnumber;
 *     }
 * }
 *
 */


package Others_git;

import java.util.Arrays;

public class GetLastDigitsArrays {

    public static void main(String[] args) {

        int[] iLoop = new int[]{10, 12, 41, 23, 71};
        Arrays.stream(iLoop).forEach(n -> System.out.print(Math.abs(n) % 10 + " "));
    }
}
