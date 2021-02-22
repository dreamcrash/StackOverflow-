/** Original Question : https://stackoverflow.com/questions/19691830/
 *
 * find the sum of the multiples of 3 and 5 below 1000
 * Ok guys, so I'm doing the Project Euler challenges and I can't believe I'm stuck on the first challenge.
 * I really can't see why I'm getting the wrong answer despite my code looking functional:
 *
 */


package Others_git;

public class SumMultiples2And5 {

    public static int multiply3_5_complexityN(int max){
        int i, x3 = 0, x5 = 0, x15 = 0;

        for(i = 3; i < max; i+=3)    x3 += i;    // Store all multiples of 3
        for(i = 5; i < max; i+=5)    x5 += i;    //  Store all multiples of 5
        for(i = 15; i < max; i+=15)  x15 += i;   //   Store all multiples 15;

        return x3 + x5 - x15;
    }

    public static int multiply3_5_constant(int max){
        int x3 =   (max - 1) / 3;
        int x5 =   (max - 1) / 5;
        int x15 =  (max - 1) / 15;
        int sn3 =  (x3 * (x3 + 1)) / 2;
        int sn5 =  (x5 * (x5 + 1)) / 2;
        int sn15 = (x15 * (x15 + 1)) / 2;
        return (3*sn3) + (5 *sn5) - (15*sn15);
    }

    public static void main(String[] args) {
        System.out.println(multiply3_5_complexityN(1000));
        System.out.println(multiply3_5_constant(1000));
    }
}
