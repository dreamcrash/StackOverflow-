/** Original Question : https://stackoverflow.com/questions/65492174/
 * When a prime number is found, I have to stop it.
 * When I use synchronized before the while, only one thread process will occur.
 * However, multiple thread operations should occur, but all should stop when prime is found.
 *
 * The initial value of i in the control part has been changed.
 *
 * What I want to do is to find prime numbers using lock and synchronized.
 *
 * public abstract class NumberGenerator {
 *
 *     private boolean isStop;
 *
 *     public abstract int generateNumber();
 *
 *     public void stop() {
 *         this.isStop = true;
 *     }
 *
 *     public boolean isStopped() {
 *         return isStop;
 *     }
 *
 * }
 *
 * public class IntegerNumberGenerator extends NumberGenerator {
 *
 *     private Random random;
 *     int randomAtama;
 *
 *     public IntegerNumberGenerator() {
 *         this.random = new Random();
 *     }
 *
 *     @Override
 *     public int generateNumber() {
 *         return random.nextInt(100) + 1;
 *     }
 *
 * }
 *
 * public class PrimeNumberChecker implements Runnable {
 *
 *     private NumberGenerator generator;
 *     private Lock lock = new ReentrantLock();
 *     public Condition continueLock = lock.newCondition();
 *
 *     public PrimeNumberChecker(NumberGenerator generator) {
 *         this.generator = generator;
 *     }
 *
 *     @Override
 *     public void run() {
 *
 *         while (!generator.isStopped()) {
 *
 *             int number = generator.generateNumber();
 *             System.out.println(Thread.currentThread().getName() + " generated " + number);
 *             if (check(number)) {
 *                 System.out.println(number + " is prime !");
 *                 generator.stop();
 *             }
 *
 *         }
 *
 *     }
 *
 *     public static boolean check(int number) {
 *
 *         boolean result = true;
 *         for (int i = 2; i <= number / 2; i++) {
 *             if ((number % i) == 0) {
 *                 result = false;
 *             }
 *         }
 *         return result;
 *     }
 *
 * }
 *
 * public class Driver {
 *
 *     public static void main(String[] args) {
 *
 *         ExecutorService executorService = Executors.newCachedThreadPool();
 *         NumberGenerator numberGenerator = new IntegerNumberGenerator();
 *         for (int i = 0; i < 5; i++) {
 *             executorService.execute(new PrimeNumberChecker(numberGenerator));
 *         }
 *         executorService.shutdown();
 *
 *     }
 *
 * }
 *
 */


package Multithreading_git.FindPrimeNumbers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        NumberGenerator numberGenerator = new IntegerNumberGenerator();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new PrimeNumberChecker(numberGenerator));
        }
        executorService.shutdown();
    }
}
