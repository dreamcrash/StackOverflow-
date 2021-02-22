/** Original Question : https://stackoverflow.com/questions/65798404/
 *
 * Function to get the sum of all numbers multiples of 3, 5, 7 until 1000
 *
 * The computeMultiplesSum (n) method should return the sum of all positive multiples of 3 or 5 or 7
 * that are strictly less than n.
 *
 * For example, for n = 11, we get 3, 5, 6, 7, 9, 10 as multiples and the sum of those multiples is 40.
 *
 * Implement computeMultiplesSum (n).
 *
 * constraints: 0 = <n <1000
 *
 */


package Others_git;

public class SumMultiples3_5_7 {

    static int computeMultiplesSumComplexityN(int n){
        int x3 = 0,x5 = 0, x7 = 0;
        int x15 = 0, x21 = 0, x35 = 0;
        int x105 = 0;

        for(int i = 3; i < n; i+=3)      x3 += i;
        for(int i = 5; i < n; i+=5)      x5 += i;
        for(int i = 7; i < n; i+=7)      x7 += i;
        for(int i = 15; i < n; i+=15)    x15 += i;
        for(int i = 21; i < n; i+=21)    x21 += i;
        for(int i = 35; i < n; i+=35)    x35 += i;
        for(int i = 105; i < n; i+=105)  x105 += i;

        return x3 + x5 + x7 - x15 - x21 - x35 + x105;
    }

    static int computeMultiplesSumComplexityConstant(int n){
        int x3 =    (n - 1) / 3;                      // 3 * ( 1 +2 + 3 … (n-1)/3)
        int x5 =    (n - 1) / 5;                      // 5 * ( 1 +2 + 3 … (n-1)/5)
        int x7 =    (n - 1) / 7;
        int x15 =   (n - 1) / 15;
        int x21 =   (n - 1) / 21;
        int x35 =   (n - 1) / 35;
        int x105 =  (n - 1) / 105;
        int sn3 =   (x3 * (x3 + 1)) / 2;
        int sn5 =   (x5 * (x5 + 1)) / 2;
        int sn7 =   (x7 * (x7 + 1)) / 2;
        int sn15 =  (x15 * (x15 + 1))/ 2;
        int sn21 =  (x21 * (x21 + 1))/ 2;
        int sn35 =  (x35 * (x35 + 1))/ 2;
        int sn105 = (x105 * (x105 + 1))/2;
        return (3*sn3) + (5 *sn5) + (7 * sn7) - (15*sn15) - (21 *sn21) - (35 * sn35)
                + (105 * sn105);
    }

    public static void main(String[] args) {
        System.out.println(computeMultiplesSumComplexityN(1000));
        System.out.println(computeMultiplesSumComplexityConstant(1000));
    }
}
