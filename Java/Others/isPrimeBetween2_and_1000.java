/*** Original Question : https://stackoverflow.com/questions/65072711/
 *
 * I am trying to write a code that checks if a number between 2 and 1000 is a prime Number
 *
 * import java.util.Scanner;
 *
 * public class Main {
 *
 *   public static void main(String[] args) {
 *
 *     for(int j = 2; j<=1000; j++) {
 *         boolean yes = true;
 *         for(int i = 2; i<j && yes== true; i++){
 *             if(j%i==0) {
 *                 yes=false;
 *             }
 *         System.out.println(j + ":" + yes);
 *       }
 *     }
 *   }
 * }
 * I am trying to understand where is the problem without any answer so far.
 *
 */


package Others_git;

import java.util.stream.IntStream;

public class isPrimeBetween2_and_1000 {

    public static void main(String[] args) {

        IntStream.range(2, 1001)
                .boxed()
                .filter(j -> IntStream.range(2, j).boxed().allMatch(i -> j % i != 0))
                .forEach(System.out::println);
    }
}
