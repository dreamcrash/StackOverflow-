/** Original Question : https://stackoverflow.com/questions/65073072/
 *
 * How to calculate the average value of each column in 2D array?
 * I am trying to calculate the average value of columns in 2D array,
 * but I cannot figure out the code.
 * he function should return the average value of each column.
 * And I cannot print the result in the function.
 * The print should be in main function.
 *
 * static double average_columns(double matrix[][]) {
 *     int i, j, sum = 0, average=0;
 *     for (i = 0; i < matrix.length; i++) {
 *         for (j = 0; j < matrix[i].length; j++) {
 *             sum=(int) (sum+matrix[i][j]);
 *         }
 *         average=sum/matrix[i].length;
 *         sum=0;
 *     }
 *     return average;
 * }
 */

package Matrix_git;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AverageOfEachColumn {
        public static void main(String[] args) {
            double[][] matrix = {{1, 5, 15}, {1, 2, 2}, {25, 109, 150}};
            averageColumns(matrix).forEach(System.out::println);
        }

    private static List<Double> averageColumns(double[][] matrix) {
        return Arrays
                .stream(IntStream.range(0, matrix[0].length)
                        .mapToObj(c1 -> Arrays.stream(matrix)
                                .mapToDouble(doubles -> doubles[c1])
                                .toArray())
                        .toArray(double[][]::new))
                .map(i -> Arrays.stream(i).average().getAsDouble())
                .collect(Collectors.toList());
    }

}
