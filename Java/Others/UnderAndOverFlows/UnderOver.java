/**
 *  Original Question : https://stackoverflow.com/questions/66048103
 *
 * How to handle addition and subtraction beyond Integers MAX_VALUE and MIN_VALUE?
 *
 * The following is the piece of code I am trying to implement:
 *
 * if (n1 > 0 && n2 > 0 && result >= Integer.MAX_VALUE) {
 *     result = Integer.MAX_VALUE;
 * }
 * else if (n1 > 0 && n2 > 0 && (result <= Integer.MIN_VALUE || result < 0)) {
 *     result = Integer.MAX_VALUE;
 * }
 * else if (n1 < 0 && n2 < 0 && (result <= Integer.MIN_VALUE || result == 0)) {
 *     result = Integer.MIN_VALUE;
 * }
 * but I am not getting satisfactory results. For example, -2147483640-10 gives me 2147483646.
 *
 * I am sure there has to be a more concrete way of doing saturation
 */


package Others_git;

public class UnderOver {

    public static long add(int n1, int n2){
       return Math.min(Math.max((long) n1 + n2, Integer.MIN_VALUE), Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        System.out.println(add(Integer.MAX_VALUE, 10));
        System.out.println(add(Integer.MIN_VALUE, -10));
        System.out.println(add(-10, -10));
        System.out.println(add(10, 10));
        System.out.println(add(10, 0));
        System.out.println(add(-20, 10));
    }
}
