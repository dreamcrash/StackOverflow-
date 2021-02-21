/** Original Question : https://stackoverflow.com/questions/65468825/
 *
 * I have complex task to differently sort two dimensional array manually. So far I get done those tasks:
 *
 * User needs to input row size from 10 - 20,
 * Generate 2D array where row size is user input and column size is randomly generated from 10-50,
 * Each array is filled with randomly generated numbers from 100 - 999,
 * Output each array row by its descending value,
 * Output average value of each array line,
 * Output on screen array with biggest average value,
 * So far I can't solve task Nr. 7. Output sorted two dimensional array by each lines average value.
 * Tried to implement new arrayAverage in loop to sort lines it didn't work.
 * Array just need to be sorted without creating new array.
 *
 */


package Matrix_git;

import java.util.Arrays;
import java.util.stream.IntStream;
import static java.util.Comparator.comparingDouble;

public class SortMatrixLinesByAverage {

    private static float[]average(int[][] array) {
        float[] arrayAverage = new float[array.length];
        float sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sum += array[i][j];
            }
            arrayAverage[i] = (float) (Math.round((sum / array[i].length) * 100.0) / 100.0);
            sum = 0;
        }
        return arrayAverage;
    }

    private static int[] createArrayIndices(int size) {
        int [] row_position = new int [size];
        for(int i = 0; i < row_position.length; i++){
            row_position[i] = i;
        }
        return row_position;
    }

    private static void bubbleSortArrayAndIndices(float[] arrayAverage, int[] row_position) {
        for(int i = 0; i < arrayAverage.length; i++){
            for(int j = 1; j < (arrayAverage.length-i); j++){
                if(arrayAverage[j-1] > arrayAverage[j]){
                    float temp = arrayAverage[j-1];
                    arrayAverage[j-1] = arrayAverage[j];
                    arrayAverage[j] = temp;
                    int temp_pos = row_position[j-1];
                    row_position[j-1] = row_position[j];
                    row_position[j] = temp_pos;
                }
            }
        }
    }

    private static int[][] sortRows(int[][] matrix, int[] row_position) {
        int[][] new_matrix = new int [matrix.length][];
        for (int i = 0; i < new_matrix.length; i++) {
            new_matrix[i] = matrix[row_position[i]];
        }
        return new_matrix;
    }

    public static void main(String[] args) {
        int[][] array = {{10, 20, 30},{40, 50, 60}, {1,2,3} };
        Arrays.sort(array, comparingDouble(row -> IntStream.of(row).average().orElse(0.0)));
        Arrays.stream(array).map(Arrays::toString).forEach(System.out::println);

        // Without streams
        int[][] matrix = {{10, 20, 30},{40, 50, 60}, {1,2,3} };

        float[] arrayAverage = average(matrix);
        int[] row_position = createArrayIndices(arrayAverage.length);
        bubbleSortArrayAndIndices(arrayAverage, row_position);
        matrix = sortRows(matrix, row_position);
        Arrays.stream(matrix).map(Arrays::toString).forEach(System.out::println);

    }
}
