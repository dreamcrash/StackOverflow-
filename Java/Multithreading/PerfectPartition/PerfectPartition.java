/***
 * Original Question : https://stackoverflow.com/questions/66057907
 *
 * How to limit number of threads created and wait main thread until any one thread finds answer?
 * This is the code to find the first pair of numbers (except 1)
 * whose sum of LCM and HCF is equal to the number.
 *
 * import java.util.*;
 * import java.util.concurrent.atomic.AtomicLong;
 *
 * class PerfectPartition {
 *     static long gcd(long a, long b) {
 *         if (a == 0)
 *             return b;
 *         return gcd(b % a, a);
 *     }
 *
 *     // method to return LCM of two numbers
 *     static long lcm(long a, long b) {
 *         return (a / gcd(a, b)) * b;
 *     }
 *
 *     long[] getPartition(long n) {
 *         var ref = new Object() {
 *             long x;
 *             long y;
 *             long[] ret = null;
 *         };
 *
 *         Thread mainThread = Thread.currentThread();
 *         ThreadGroup t = new ThreadGroup("InnerLoop");
 *
 *         for (ref.x = 2; ref.x < (n + 2) / 2; ref.x++) {
 *             if (t.activeCount() < 256) {
 *
 *                 new Thread(t, () -> {
 *                     for (ref.y = 2; ref.y < (n + 2) / 2; ref.y++) {
 *                         long z = lcm(ref.x, ref.y) + gcd(ref.x, ref.y);
 *                         if (z == n) {
 *                             ref.ret = new long[]{ref.x, ref.y};
 *
 *                             t.interrupt();
 *                             break;
 *                         }
 *                     }
 *                 }, "Thread_" + ref.x).start();
 *
 *                 if (ref.ret != null) {
 *                     return ref.ret;
 *                 }
 *             } else {
 *                 ref.x--;
 *             }
 *         }//return new long[]{1, n - 2};
 *
 *         return Objects.requireNonNullElseGet(ref.ret, () -> new long[]{1, n - 2});
 *     }
 *
 *     public static void main(String[] args) {
 *         Scanner sc = new Scanner(System.in);
 *         long n = sc.nextLong();
 *         long[] partition = new PerfectPartition().getPartition(n);
 *         System.out.println(partition[0] + " " + partition[1]);
 *     }
 * }
 * I want to stop the code execution as soon as the first pair is found.
 * But instead, the main thread just keeps running and prints 1 and n-1.
 * What could be an optimal solution to limit the no. of threads
 * (<256 as the range of n is 2 to max of long)?
 *
 * Expected Output (n=4): 2 2
 * Expected Output (n=8): 4 4
 *
 */


package Multithreading_git;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


class ThreadWork implements Runnable{

    final long[] rest;
    final AtomicBoolean found;
    final int threadID;
    final int total_threads;
    final long n;

    ThreadWork(long[] rest, AtomicBoolean found, int threadID, int total_threads, long n) {
        this.rest = rest;
        this.found = found;
        this.threadID = threadID;
        this.total_threads = total_threads;
        this.n = n;
    }

    static long gcd(long a, long b) {
        return (a == 0) ? b : gcd(b % a, a);
    }

    static long lcm(long a, long b, long gcd) {
        return (a / gcd) * b;
    }

    @Override
    public void run() {
        for (int x = 2; !found.get() && x < (n + 2) / 2; x ++) {
            for (int y = 2 + threadID; !found.get() && y < (n + 2) / 2; y += total_threads) {
                long result = gcd(x, y);
                long z = lcm(x, y, result) + result;
                if (z == n) {
                    synchronized (found) {
                        if(!found.get()) {
                            rest[0] = x;
                            rest[1] = y;
                            found.set(true);
                        }
                        return;
                    }
                }
            }
        }
    }
}

class PerfectPartition {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        final long n = sc.nextLong();
        final int total_threads = Runtime.getRuntime().availableProcessors();
        long[] rest = new long[2];
        AtomicBoolean found = new AtomicBoolean();

        double startTime = System.nanoTime();
        Thread[] threads = new Thread[total_threads];
        for(int i = 0; i < total_threads; i++){
            ThreadWork task = new ThreadWork(rest, found, i, total_threads, n);
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for(int i = 0; i < total_threads; i++){
            threads[i].join();
        }

        double estimatedTime = System.nanoTime() - startTime;
        System.out.println(rest[0] + " " + rest[1]);


        double elapsedTimeInSecond = estimatedTime / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
    }
}
