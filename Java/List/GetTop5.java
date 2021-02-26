/*** Original Question : https://stackoverflow.com/questions/66008858/
 * I have a list of Integer
 *
 * List<Integer> lst = new ArrayList<>();
 * lst.add(10);
 * lst.add(15);
 * lst.add(16);
 * lst.add(8);
 * lst.add(100);
 * lst.add(1);
 * lst.add(40);
 * How to write a code so that I can get the top 5 max element from the list, namely 100, 40, 16, 15, 10?
 *
 * I have tried by using Java stream API:
 *
 * Integer var = lst.stream().max(Integer::compare).get();
 * but get only one value element.
 */

package Others_git;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class GetTop5 {

    public class User{
         public int getProp2(){
             return 0;
         }

        public void setNormalized(int i) {
        }
    }

    public static void normalization(Collection<User> data, ToIntFunction<User> getProp) {
        IntSummaryStatistics summaryStatistics = data.stream()
                .mapToInt(getProp)
                .summaryStatistics();

        int max = summaryStatistics.getMax();
        int min = summaryStatistics.getMin();

        data.forEach(d -> d.setNormalized(5 * ((getProp.applyAsInt(d) - min) / (max - min))));
    }


    public static void main(String []args){
        List<Integer> lst = List.of(10, 15, 16, 8, 100, 1, 40);
        printTop(lst, 5);
    }

    private static void printTop(Collection<Integer> col, int top) {
        List<Integer> collect = col.stream().sorted(Comparator.reverseOrder()).limit(top).collect(Collectors.toList());
        System.out.println(collect);
    }
}
