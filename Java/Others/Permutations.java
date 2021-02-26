/** Original Question : https://stackoverflow.com/questions/65734844
 *
 * Find 3 numbers that are sum up to a given number
 *
 * I need to create a recursive method that takes a number as input (ranging from 3 to 30, exclusive), and finds all the combinations of adding three natural numbers (ranging from 1 to 10, inclusive) so that they will be equal to this number.
 *
 * For instance, if the input is 5, I will need to find all the 6 combinations: "1+1+3", "1+2+2", "1+3+1", "2+1+2", "2+2+1" and "3+1+1".
 *
 * The method should print the options, and give back the number of options. This is my code so far:
 *
 * public static int solutions(int num)
 * {
 *     if(num < 3 || num >30)
 *         return 0;
 *     return solutions(1,1,1, num);
 * }
 *
 * private static int solutions(int x1, int x2, int x3, int num)
 * {
 *     if(x1 > 10 || x2 > 10 || x3 > 10)
 *         return 0;
 *     if (x1+x2+x3 != num)
 *     {
 *         return  solutions(x1 + 1, x2, x3, num)+
 *                 solutions(x1, x2 + 1, x3, num)+
 *                 solutions(x1, x2, x3 + 1, num);
 *     }
 *     else
 *     {
 *         System.out.println(x1 + "+" + x2 +"+" + x3);
 *         return 1;
 *     }
 * }
 * this code gives me too many answers (all are right but redundant)
 * e.g. for 5 it give me 9 answers instead of 6.
 *
 */

package Others_git;

import java.util.Set;

public class Permutations {
    /**
     * Sub Optimal approach
     *
     * @param x1
     * @param x2
     * @param x3
     * @param num
     * @param combinations
     */
    public static void solutions(int x1, int x2, int x3, int num, Set<String> combinations){
        if(x1 <= 10 && x2 <= 10 && (num - x2 - x1) <= 10) {
            combinations.add(x1 + "+" + x2 + "+" + (num - x2 - x1));
        }
        if ( x1 <= num - 2 && x2 <= num - 2 && x3 <= num - 2 && x1 + x2 + x3 < num) {
            if(x1 < 10) solutions(x1 + 1, x2, x3, num, combinations);
            if(x2 < 10 && x1 <= x2) solutions(x1, x2 + 1, x3, num, combinations);
        }
    }


    /**
     * Faster approach
     *
     * @param x1
     * @param x2
     * @param num
     * @return
     */
    private static int solutions(int x1, int x2, int num){
        int count = 0;
        if (x2 < Math.min(10, num - x1)) count = solutions(x1, x2 + 1, num);
        else if (x1 < Math.min(10, num)) count = solutions(x1 + 1, 1, num);
        if(x1 <= 10 && x2 <= 10) {
            int x3_tmp = num - x2 - x1;
            if(0 < x3_tmp && x3_tmp <= 10) {
                System.out.println(x1 + "+" + x2 + "+" + x3_tmp);
                return 1 + count;
            }
        }
        return count;
    }

    public static int solutions(int num){
        return (num < 3 || num > 30) ? 0 : solutions(1,1, num);
    }
    public static void main(String[] args){
        int count = solutions(5);
        System.out.println("Count = " +count);
    }
}
