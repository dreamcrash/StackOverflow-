package Multithreading_git.ShowTwoThreadSynchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example2 {

    private int x;

    public void addX(int y){x = x + y;}

    public static void main(String[] args) {
        Example2 example = new Example2();
        int total_threads = 2;
        ExecutorService pool = Executors.newFixedThreadPool(total_threads);
        pool.execute(() -> {
            synchronized (example) {
                parallel_task(example, -2);
            }
        });
        pool.execute(() -> {
            synchronized (example) {
                parallel_task(example, 1);
            }
        });

        pool.shutdown();
        try { pool.awaitTermination(1, TimeUnit.MINUTES);}
        catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(example.x);
    }

    private static void parallel_task(Example2 example, int i) {
        thread_sleep();
        for (int j = 0; j < 1000; j++)
            example.addX(i);
    }

    private static void thread_sleep() {
        try { Thread.sleep(1000); }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
