/**
 * Original Question : https://stackoverflow.com/questions/65029032/
 *
 * How to find the indexes of the biggest number in a given List with duplicates using Java
 *
 * I have a list with values [1,2,2,8,7,8].
 * I would like to find the indexes of the biggest number. Here the biggest number is 8.
 * 8 is repeated twice. So the answer should be [3,5] i.e. index of both the 8's.
 *
 * I spent a lot of time. I am able to find the biggest number.
 * I am not able to find a clean and easy solution to find the indexes.
 *
 * import java.util.ArrayList;
 * import java.util.List;
 *
 * public class Test {
 *
 *     public static void main(String[] args) {
 *         List<Integer> parts = new ArrayList<>();
 *         parts.add(1);
 *         parts.add(2);
 *         parts.add(2);
 *         parts.add(8);
 *         parts.add(7);
 *         parts.add(8);
 *
 *         Test ob = new Test();
 *         System.out.println("Indexs with max value:" + ob.getIndex(parts));
 *     }
 *
 *     public List<Integer> getIndex(List<Integer> parts) {
 *         int big = parts.get(0);
 *         List<Integer> indexes = new ArrayList<>();
 *         for (int i = 1; i < parts.size(); i++) {
 *             if (big <= parts.get(i)) {
 *                 big = parts.get(i);
 *                 indexes.add(i);
 *             }
 *         }
 *         System.out.println("Biggest Number:" + big);
 *         return indexes;
 *     }
 * }
 * The above code prints the biggest number and prints wrong indexes i.e.
 * I am adding 'i' value to the index whenever it goes inside the if loop.
 * I need to filter so that it adds only if the value is big.
 * I am looking for a solution without two for loops. Any help is appreciated.
 *
 */


package LinkedList_git;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class IndexesOfBiggestValues {

    public static void main(String[] args) {
        List<Integer> parts = new ArrayList<>();
        parts.add(1);
        parts.add(2);
        parts.add(2);
        parts.add(8);
        parts.add(7);
        parts.add(8);

        IndexesOfBiggestValues ob = new IndexesOfBiggestValues();
        System.out.println("Indexes with max value:" + ob.getIndex(parts));
    }

    public List<Integer> getIndex(List<Integer> parts) {
        if (!parts.isEmpty()) {
            int max = parts.stream().max(Integer::compare).get();
            return IntStream.range(0, parts.size())
                    .filter(i -> parts.get(i) == max)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
