/**
 * Original Question : https://stackoverflow.com/questions/66834232/
 *
 * This is probably already asked, but it is a little difficult for me to understand.
 * I created a for loop to add random integers into my array, but when the integer is already in the array,
 * restart the loop. But I keep on getting the same integers into the array + when there already is a duplicate,
 * the array size increases. Does anyone know what I'm doing wrong?
 */


package Others_git;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneratingRandomValuesWithoutDuplicates {

    public static void main(String[] args) {
        System.out.println(strategy1());
        System.out.println(strategy2());
    }

    /**
     * This approach is good when desirableSize is much less than the pool size,
     * but if the desirableSize is a substantial proportion of pool size you're better
     * off shuffling the pool and selecting the first desirableSize elements {@link #strategy2()} .
     *
     * @return
     */
    private static Set<Integer> strategy1() {
        Random r = new Random();
        int desirableSize = 5;
        Set<Integer> uniques = new HashSet<>(desirableSize);
        while(uniques.size() < desirableSize){
            uniques.add(r.nextInt(10));
        }
        return uniques;
    }

    /**
     * With rejection-based schemes such as repeated attempts to add to a set,
     * the expected number of iterations is O(poolsize * log(desirableSize)) with O(desirableSize)
     * storage for the set. Shuffling is O(poolsize) but requires O(poolsize) storage for the shuffle.
     * As desirableSize -> poolsize, shuffling wins on expected iterations and becomes competitive on storage.
     * With partial shuffling implementations, the number of iterations for shuffling is O(desirableSize)
     * although the storage remains the same.
     * @return
     */
    public static List<Integer> strategy2(){
        int start  = 0;
        int end = 10;
        int size = 5;
        List<Integer> collect = IntStream.rangeClosed(start, end)
                .boxed()
                .limit(size)
                .collect(Collectors.toList());
        Collections.shuffle(collect);
        return collect;
    }
}
