/**
 *  Original Question : https://stackoverflow.com/questions/65651835/
 *
 *  Find the 2nd highest no from array, where array contains duplicate values
 *
 *  public class Second_Largest_Number_In_Array_with_Dupes {
 *
 *     public static void main(String[] args) {
 *         int[] a = {6,8,2,4,3,1,5,7};
 *
 *         int temp ;
 *         for (int i = 0; i < a.length; i++){
 *             for (int j = i + 1; j < a.length; j++){
 *                 if (a[i] < a[j]){
 *                     temp = a[i];
 *                     a[i] = a[j];
 *                     a[j] = temp;
 *                 }
 *             }
 *         }
 *
 *         System.out.println("Second Largest element is " + a[1]);
 *     }
 * }
 * This only works for array with no duplicate values.
 *
 * For example it does not work for this array: int[] a = {6,8,2,4,3,1,5,7,8};
 */

package Others_git;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class FindNthMax {

    public static int getNthMaxElement(int [] array, int nth){
        SortedSet<Integer> sortedSet = new TreeSet<Integer>();
        Arrays.stream(array).forEach(sortedSet::add);
        if(array.length < nth) return Integer.MIN_VALUE;
        for(int i = 1; i < nth; i++)
            sortedSet.remove(sortedSet.last());
        return sortedSet.last();
    }

    public static void main(String[] args) {
        int [] a= {6,8,2,4,3,1,5,7,8} ;
        for(int i = 1; i < a.length; i++)
            System.out.println(i+"th max : "+getNthMaxElement(a, i));
    }
}
