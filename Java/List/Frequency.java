/** Original Question : https://stackoverflow.com/questions/505928
 *
 *  How to count the number of occurrences of an element in a List
 *
 *  I have an ArrayList, a Collection class of Java, as follows:
 *
 * ArrayList<String> animals = new ArrayList<String>();
 * animals.add("bat");
 * animals.add("owl");
 * animals.add("bat");
 * animals.add("bat");
 * As you can see, the animals ArrayList consists of 3 bat elements and one owl element.
 * I was wondering if there is any API in the Collection framework that returns the number
 * of bat occurrences or if there is another way to determine the number of occurrences.
 *
 * I found that Google's Collection Multiset does have an API that returns the total number
 * of occurrences of an element. But that is compatible only with JDK 1.5.
 * Our product is currently in JDK 1.6, so I cannot use it.
 *
 */

package List_git;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Frequency {

    public static int frequency(Collection<?> c, Object o){
        return Collections.frequency(c, o);
    }

    public static long filter(Collection<?> c, Object o){
        return c.stream().filter(o::equals).count();
    }

    public static long manually(Collection<?> c, Object o){
        int count = 0;
        for(Object e : c)
            if(e.equals(o))
                count++;
        return count;
    }

    public static Map<?, Long> mapGroupBy(Collection<?> c){
        return c.stream()
                .collect(Collectors.groupingBy(Function.identity() , Collectors.counting()));
    }

    public static Map<Object, Long> mapMerge(Collection<?> c){
        Map<Object, Long> map = new HashMap<>();
        c.forEach(e -> map.merge(e, 1L, Long::sum));
        return map;
    }

    public static Map<Object, Long> manualMap(Collection<?> c){
        Map<Object, Long> map = new HashMap<>();
        c.forEach(e -> map.compute(e, (k, v) -> (v == null) ? 1 : v + 1));
        return map;
    }


    public static void main(String[] args){
        List<String> animals = new ArrayList<>();
        animals.add("bat");
        animals.add("owl");
        animals.add("bat");
        animals.add("bat");

        System.out.println(frequency(animals, "bat"));
        System.out.println(filter(animals,"bat"));
        System.out.println(manually(animals,"bat"));
        mapGroupBy(animals).forEach((k, v) -> System.out.println(k + " -> "+v));
        mapMerge(animals).forEach((k, v) -> System.out.println(k + " -> "+v));
        manualMap(animals).forEach((k, v) -> System.out.println(k + " -> "+v));
    }
}
