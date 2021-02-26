/***
 * Original Question : https://stackoverflow.com/questions/65858672/
 *
 * How to write a function to find a value bigger than N in parallel
 *
 */


package Multithreading_git;

import java.util.concurrent.atomic.AtomicInteger;

public class FindValueBiggerThanN {
    private final static int NO_FOUND = -1;

    // Linear-search function to find the index of an element
    public static int findIndex(int[] arr, int t, int threadId, int total_threads, AtomicInteger shared_index){
        for (int i = threadId; i < arr.length && shared_index.get() == -1; i += total_threads)
            if ( arr[i] > t)
                return i;
        return NO_FOUND;
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 8;
        int[] my_array = { 5, 4, 6, 1, 3, 2, 7, 8, 9 };
        int total_threads = 4;

        AtomicInteger shared_index = new AtomicInteger(-1);

        Thread[] threads = new Thread[total_threads];
        for(int t = 0; t < threads.length; t++) {
            final int thread_id = t;
            threads[t] = new Thread(() ->parallel_work(N, my_array, total_threads, shared_index, thread_id));
            threads[t].start();
        }

        for (Thread thread : threads)
            thread.join();

        System.out.println("Index of value bigger than " + N + " : " + shared_index.get());
    }

    private static void parallel_work(int n, int[] my_array, int total_threads, AtomicInteger shared_index, int thread_id) {
        int index_found = findIndex(my_array, n, thread_id, total_threads, shared_index);
        shared_index.compareAndExchange(NO_FOUND, index_found);
    }
}
