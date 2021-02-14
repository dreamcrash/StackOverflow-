/** Original Question : https://stackoverflow.com/questions/13446282/
 *
 * Writing a recursive sorting algorithm of an array of integers
 *
 * I am trying to write a recursive sorting algorithm for an array of integers.
 * The following codes prints to the console: 3, 5, 2, 1, 1, 2, 6, 7, 8, 10, 20
 *
 * The output should be sorted but somehow "it doesn't work".
 *
 */

package Sorting_git;

import java.util.Random;
import java.util.stream.IntStream;

public class MergeSortTest {
    public static void mergeSort(int[] data) {
        if(data.length <= 1) return;               // Base case: just 1 elt

        int[] a = new int[data.length / 2];        // Split array into two
        int[] b = new int[data.length - a.length]; //   halves, a and b
        for(int i = 0; i < data.length; i++) {
            if(i < a.length) a[i] = data[i];
            else             b[i - a.length] = data[i];
        }

        mergeSort(a);                              // Recursively sort first
        mergeSort(b);                              //   and second half.

        int ai = 0;                                // Merge halves: ai, bi
        int bi = 0;                                //   track position in
        while(ai + bi < data.length) {             //   in each half.
            if(bi >= b.length || (ai < a.length && a[ai] < b[bi])) {
                data[ai + bi] = a[ai]; // (copy element of first array over)
                ai++;
            } else {
                data[ai + bi] = b[bi]; // (copy element of second array over)
                bi++;
            }
        }
    }
    public static boolean isArraySorted(int[] array){
        for(int i = 0; i < array.length - 1; i++)
            if(array[i] > array [i+1])
                return false;
        return true;
    }

    public static void main(String[] args) {
        int[]  randomIntsArray = IntStream.generate(() -> new Random().nextInt(100)).limit(100).toArray();
        mergeSort(randomIntsArray);
        System.out.println(isArraySorted(randomIntsArray));
    }
}
