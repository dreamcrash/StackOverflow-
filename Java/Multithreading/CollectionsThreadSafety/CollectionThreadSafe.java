/***
Original Question : https://stackoverflow.com/questions/66059994

How Synchronized and Concurrent Collections are thread-safe but their content not

From this source one can read:

It's worth mentioning that synchronized and concurrent collections only make the 
collection itself thread-safe and not the contents.
I thought if Collection is thread-safe then its content will implicitly be thread-safe.

I mean if two threads cannot access my Collection object then the object which my 
Collection object is holding will implicitly become thread-safe.

I missing the point, could someone please explain me with an example?





***/





package Multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CollectionThreadSafe {

    private static void addToList(List<Integer> list) {
        for(int i = 0; i < 10; i++)
            list.add(i);
    }

    /***
     * This method has a race-condition during the add of elements into the
     * list. One might have to run several times to experience the race-condition
     *
     * The output should be 45 * total_threads. However, due to the race condition
     * the result is non-deterministic; yielding sometimes a NPE.
     *
     * @throws InterruptedException
     */
    private static int non_thread_safe_collection() throws InterruptedException {
        final int total_threads = 2;
        List<Integer> list = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(total_threads);
        for(int i = 0; i < total_threads; i++){
            pool.submit(() -> addToList(list));
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        return list.stream().reduce(0, Integer::sum);
    }

    /**
     * Version without the race condition
     *
     * @throws InterruptedException
     */
    private static int thread_safe_collection() throws InterruptedException {
        final int total_threads = 2;
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        ExecutorService pool = Executors.newFixedThreadPool(total_threads);
        for(int i = 0; i < total_threads; i++){
            pool.submit(() -> addToList(list));
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        return list.stream().reduce(0, Integer::sum);
    }

    /**
     * Version that proves that :
     *
     * It's worth mentioning that synchronized and concurrent collections
     * only make the collection itself thread-safe and not the contents.
     *
     * @throws InterruptedException
     */
    private static int thread_safe_collection_but_race_condition_on_its_content() throws InterruptedException {
        final int total_threads = 2;
        List<List<Integer>> list_tread_safe = Collections.synchronizedList(new ArrayList<>());
        list_tread_safe.add(new ArrayList<>());
        List<Integer> list = list_tread_safe.get(0);
        ExecutorService pool = Executors.newFixedThreadPool(total_threads);
        for(int i = 0; i < total_threads; i++){
            pool.submit(() -> addToList(list));
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        return list.stream().reduce(0, Integer::sum);
    }

    public static void main(String[] arg) throws InterruptedException {
        int result;
        int count = 0;
        try {
            do {
                count++;
                result = non_thread_safe_collection();
            } while (result == 90);
            System.out.println("First = " + result + " | Executions = "+count);
        } catch (NullPointerException e){ // Never do this in production
            System.out.println("Race condition lead to NPE");
        }

        result = thread_safe_collection();
        System.out.println("Second = "+ result);

        try {
            count = 0;
            do {
                count++;
                result = thread_safe_collection_but_race_condition_on_its_content();
            } while (result == 90);
            System.out.println("Third = " + result + " | Executions = "+count);
        } catch (NullPointerException e){ // Never do this in production
            System.out.println("Race condition lead to NPE");
        }
    }
}
