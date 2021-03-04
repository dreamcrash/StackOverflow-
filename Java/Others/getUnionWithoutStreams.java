/** Original Question : https://stackoverflow.com/questions/65054208
 * Checking if two int arrays have duplicate elements, and extract one of the duplicate elements from them
 *
 * I'm trying to write a method, union(), that will return an int array,
 * and it takes two int array parameters and check if they are sets,
 * or in other words have duplicates between them. I wrote another method, isSet(),
 * it takes one array argument and check if the array is a set.
 * The problem is I want to check if the two arrays in the union method have duplicates between them,
 * if they do, I want to extract one of the duplicates and put it in the unionArray[] int array.
 * This is what I tried so far.
 *
 */

package Others_git;

import java.util.Arrays;

public class getUnionWithoutStreams {

    public static int[] union(int[] array1, int[] array2){
        int[] tmp_union = new int[array1.length + array2.length];
        int added_so_far = add_unique(array1, tmp_union, 0);
        added_so_far = add_unique(array2, tmp_union, added_so_far);
        return copyArray(tmp_union, added_so_far);
    }

    private static int[] copyArray(int[] ori, int size) {
        int[] dest = new int[size];
        for(int i = 0; i < size; i++)
            dest[i] = ori[i];
        return dest;
    }

    private static int add_unique(int[] array, int[] union, int added_so_far) {
        for (int element : array)
            if (!is_present(union, added_so_far, element))
                union[added_so_far++] = element;
        return added_so_far;
    }

    private static boolean is_present(int[] union, int added_so_far, int element) {
        for (int z = 0; z < added_so_far; z++)
            if (element == union[z])
                return true;
        return false;
    }

   public static void main(String[] args) {
        // Tests copied from https://stackoverflow.com/a/65055452/1366871
       System.out.println(Arrays.toString(union(new int[]{1, 2, 3, 4},          new int[]{3, 4, 5, 6})));
       System.out.println(Arrays.toString(union(new int[]{1, 2, 3},             new int[]{4, 5, 6})));
       System.out.println(Arrays.toString(union(new int[]{1, 2, 3, 4},          new int[]{1, 2, 3, 4})));
       System.out.println(Arrays.toString(union(new int[]{1, 2, 3, 4},          new int[]{3, 4})));
       System.out.println(Arrays.toString(union(new int[]{1, 2, 3, 4},          new int[]{4, 5})));
       System.out.println(Arrays.toString(union(new int[]{1, 2, 3, 4, 5, 6},    new int[]{7, 8})));
    }
}
