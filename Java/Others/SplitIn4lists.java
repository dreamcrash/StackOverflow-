/** Original Question : https://stackoverflow.com/questions/65928759
 *
 * Splitting list into 4 even separate lists
 *
 */

package Others_git;

import java.util.ArrayList;
import java.util.List;

class SplitIn4lists {
    public static void main(String[] arg) {
        List<String> list = new ArrayList<>();
        list.add("jdjdb");
        list.add("jsid");
        list.add("hsisi");
        list.add("hri");
        list.add("idt");

        final int size_split = 4;
        List<List<String>> result = new ArrayList<>();
        for(int i = 0; i < size_split; i++)
            result.add(new ArrayList<>());

        int count = 0;
        for(String i : list){
            result.get(count % size_split).add(i);
            count++;
        }
        System.out.println(result);
    }
}
