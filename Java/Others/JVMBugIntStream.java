/** Original Question : https://stackoverflow.com/questions/65394902/
 *
 * IntStream leads to array elements being wrongly set to 0 (JVM Bug, Java 11)
 *
 * In the class P below, the method test seems to return identically false:
 *
 * import java.util.function.IntPredicate;
 * import java.util.stream.IntStream;
 *
 * public class P implements IntPredicate {
 *     private final static int SIZE = 33;
 *
 *     @Override
 *     public boolean test(int seed) {
 *         int[] state = new int[SIZE];
 *         state[0] = seed;
 *         for (int i = 1; i < SIZE; i++) {
 *             state[i] = state[i - 1];
 *         }
 *         return seed != state[SIZE - 1];
 *     }
 *
 *     public static void main(String[] args) {
 *         long count = IntStream.range(0, 0x0010_0000).filter(new P()).count();
 *         System.out.println(count);
 *     }
 * }
 * Combining class P with IntStream, however, the method test can (wrongly) return true.
 * The code in the main method above results in some positive integer, like 716208.
 * The result changes after every execution.
 *
 * This unexpected behavior occurs because the int array state[] can be set to zero during the execution.
 * If a testing code, such as
 *
 * if (seed == 0xf_fff0){
 *     System.out.println(Arrays.toString(state));
 * }
 * is inserted at the tail of the method test, then the program will output a line
 * like [1048560, 1048560, 1048560, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0].
 *
 * Question: Why can the int array state[] be set to zero?
 *
 * I already know how to avoid this behavior: just replacing int[] with ArrayList.
 *
 * I examined in:
 *
 * windows 10 + and debian 10+ with OpenJDK Runtime Environment (build 15.0.1+9-18)
 * OpenJDK 64-Bit Server VM (build 15.0.1+9-18, mixed mode, sharing)
 * debian 9 + OpenJDK Runtime Environment AdoptOpenJDK (build 13.0.1+9)
 * OpenJDK 64-Bit Server VM AdoptOpenJDK (build 13.0.1+9, mixed mode, sharing)

 **/

package Others_git;

import java.util.stream.IntStream;

public class JVMBugIntStream {
    private final static int SIZE = 33;

    public static boolean test2(int seed) {
        int[] state = new int[SIZE];
        state[0] = seed;
        for (int i = 1; i < SIZE; i++) {
            state[i] = state[i - 1];
        }
        return seed != state[SIZE - 1];
    }

    public static void main(String[] args) {
        long count = IntStream.range(0, 0x0010_0000).filter(JVMBugIntStream::test2).count();
        System.out.println(count);

    }
}
