/** Original Question : https://stackoverflow.com/questions/66844970
 *
 * How can I not overwrite values via putAll() but instead add to the current value?
 *
 * Suppose I have two hash maps:
 *
 * HashMap <String, Integer> h1;
 * h1.put("hi", 30);
 * h1.put("hi2",20);
 * h1.put("hi3",10);
 * h1.put("hi4",20);
 *
 *
 * HashMap <String, Integer> h2;
 * h2.put("hi", 20);
 * h2.put("hi2", 20);
 * h2.put("hi3", 20);
 * h2.put("hi4", 20);
 * My question is, if I do the following
 *
 * h2.putAll(h1);
 * How could I update the values of h2 to be the sum, instead of just overwriting it? That is I want
 *
 * [{"hi"=50}]
 * [{"hi2"=40}]
 * [{"hi3"=30}]
 * [{"hi4"=40}]
 *
 * Instead of this
 *
 * [{"hi"=30}]
 * [{"hi2"=20}]
 * [{"hi3"=10}]
 * [{"hi4"=20}]
 * Note: no functional constructs (including lambdas) and external libraries are allowed
 *
 */

package Map_git;
import java.util.HashMap;
import java.util.Map;

public class MergeMaps {

    private static Map<String, Integer> merge(Map<String, Integer> h1, Map<String, Integer> h2) {
        Map<String, Integer> h3 = new HashMap<>(h2);
        h1.forEach((key, value) -> h3.merge( key, value, Integer::sum));
        return h3;
    }

    private static Map<String, Integer> oldMethod(Map<String, Integer> h1, Map<String, Integer> h2) {
        Map<String, Integer> h3 = new HashMap<>(h2);
        for(String key : h1.keySet()){
            Integer v1 = h1.get(key);
            Integer v2 = h2.get(key);
            h3.put(key, (v2 == null) ? v1 : v1 + v2);
        }
        return h3;
    }

    public static void main(String[] args) {
        Map<String, Integer> h1 = Map.of("hi", 30, "hi2",20, "hi3",10, "hi4",20);
        Map<String, Integer> h2 = Map.of("hi", 20, "hi2",20, "hi3",20, "hi4",20);
        System.out.println(merge(h1, h2));
        System.out.println(oldMethod(h1, h2));
    }
}
