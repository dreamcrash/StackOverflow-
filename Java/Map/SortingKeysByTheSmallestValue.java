/** Original Question : https://stackoverflow.com/questions/66534970
 *
 * I'm trying to sort a HashMap as below
 *
 * HashMap<String, List<obj> a = new hashmap<>();
 *
 * a.put('a',{8,10,9});
 * a.put('b',{6,9,1});
 * sorting output be like
 *
 * { ('b',{1,6,9}) , ('a',{8,9,10}) }
 * Sorting condition:
 *
 * Sort the values in each key
 * Sort the key based on the smallest value.
 *
 */

package Map_git;

import java.util.*;

public class SortingKeysByTheSmallestValue {

    public static Map<String, List<Integer>> sortByMinimalValue(Map<String, List<Integer>> m){
        List<Map.Entry<String, List<Integer>>> list = new ArrayList<>(m.entrySet());
        m.forEach((k, v) -> Collections.sort(v));
        list.sort(Comparator.comparingInt(o -> Collections.min(o.getValue())));
        Map<String, List<Integer>> sortedMap = new LinkedHashMap<>();
        list.forEach(aa -> sortedMap.put(aa.getKey(), aa.getValue()));
        return sortedMap;
    }

    public static void main(String[] args) {
        List<Integer> x = new ArrayList<>(List.of(8, 10, 9));
        List<Integer> y = new ArrayList<>(List.of(6, 9, 1));
        List<Integer> z = new ArrayList<>(List.of(2, 5, 7));
        Map<String, List<Integer>> a = Map.of("a", x, "b", y, "c", z);
        sortByMinimalValue(a).forEach((k, v) -> System.out.println(k + " - "+ v ));
    }
}
