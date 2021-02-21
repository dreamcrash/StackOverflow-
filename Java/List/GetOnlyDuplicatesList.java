/** Original Question : https://stackoverflow.com/questions/65470475/
 *
 * I need to get all duplicates in my ArrayList. I don't need to remove duplicates,
 * I need to add them to another ArrayList. Here is an example:
 *
 * ArrayList<String> var = new ArrayList<>();
 * var.add("a");
 * var.add("b");
 * var.add("b");
 * var.add("c");
 * So, as you can see, there are 2 duplicate elements (b, and b). I need to add them to another ArrayList.
 *
 * The resulting ArrayList in this case should be [b,b]. How can I do this?
 *
 */
package List_git;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetOnlyDuplicatesList {

    private static List<String> approach1(List<String> var) {
        return var.stream()
                .filter(s -> Collections.frequency(var, s) > 1)
                .collect(Collectors.toList());
    }

    private static List<String> approach2(List<String> var) {
        List<String> dup =  new ArrayList<>();
        Map<String, Long> frequencies = var.stream()
                                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<String, Long> entry : frequencies.entrySet()){
            for(int i = 0; i < entry.getValue() && entry.getValue() > 1; i++)
                dup.add(entry.getKey());
        }
        return dup;
    }

    private static List<String>  approach3(List<String> var) {
        Set<String> set = new HashSet <>();
        List<String> duplicates = new ArrayList<>();
        Set<String> is_duplicated = new HashSet <>();
        var.forEach(s -> {
            if(set.contains(s)) {
                is_duplicated.add(s);
                duplicates.add(s);
            }
            else
                set.add(s);
        });
        duplicates.addAll(is_duplicated);
        return duplicates;
    }


    public static void main(String[] args) {

        List<String> var = List.of("c", "b", "b", "c", "c", "c", "c", "a");
        System.out.println(approach1(var));
        System.out.println(approach2(var));
        System.out.println(approach3(var));
    }
}
