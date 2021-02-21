/** Original Question : https://stackoverflow.com/questions/65810513/
 *
 * Java Program using Big O(n log n) or Big O(log n) complexity
 *
 * I am trying to create a Java program to find the common elements/ages between 2 arrays.
 * However, I need the algorithm has to run on Big O(n log n) or Big O(log n) time complexity.
 * This is what I came up with, any ideas on how to improve it?
 * Am I counting of the steps of the operation correctly as well?
 *
 *
 */

package Set_git;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompareTwoSetsInLessThanQuadraticTime {
    public static void main(String[] args) {

        int [] ageMale = {21, 19, 24, 22, 20, 23, 18};  //Program is executed 1 time
        int [] ageFemale = {17, 17, 22, 19};            //Program is executed 1 time
        System.out.println("Age of male students: " + Arrays.toString(ageMale));
        System.out.println("Age of female students: " + Arrays.toString(ageFemale));
        Set<Integer> female_ages = new HashSet<>(ageFemale.length);
        for (int age : ageFemale) // Time complexity of O(S)
            female_ages.add(age); // Time complexity of O(1)

        for (int age : ageMale) { // Time complexity of O(L)
            if(!female_ages.add(age)){ // Time complexity of O(1)
                System.out.println("The common ages are: " + age);
            }
        }
    }
}
