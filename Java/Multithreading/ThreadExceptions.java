/**
 * Original Question : https://stackoverflow.com/questions/65616757
 * I was trying to learn how exception propagate in multithread.
 *
 * Question
 *
 * In the following example. Why is the RuntimeException exception not caught in
 *
 * `System.out.println("Exception handled " + e);
 * Given Code
 *
 * package p05_Interrupt;
 *
 * public class D05_Interrupt1 extends Thread {
 *   public void run()  {
 *     try {
 *       Thread.sleep(1000);
 *       System.out.println("task");
 *     } catch (InterruptedException e) {
 *       throw new RuntimeException("Thread interrupted..." + e);
 *     }
 *   }
 *
 *   public static void main(String args[]) {
 *     D05_Interrupt1 t1 = new D05_Interrupt1();
 *     t1.start();
 *     try {
 *       t1.interrupt();
 *     } catch (Exception e) {
 *       System.out.println("Exception handled " + e); // Why not print this line
 *     }
 *   }
 * }
 * 1st example in JavaTpoint "Example of interrupting a thread that stops working"
 *
 * Output
 *
 * Exception in thread "Thread-0" java.lang.RuntimeException: Thread interrupted...java.lang.InterruptedException: sleep interrupted
 *     at p05_Interrupt.D05_Interrupt1.run(D05_Interrupt1.java:9)
 * Is that because this is multithreading, so exception is printed in the thread t1,
 * and the output of main() thread is hidden?
 *
 * I also tried to add throws at public void run() throws RuntimeException { didn't change anything.
 *
 *
 */

package Multithreading_git;

public class ThreadExceptions extends Thread implements Thread.UncaughtExceptionHandler{
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("task");
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted..." + e);
        }
    }

    public static void main(String[] args) {
        ThreadExceptions t1 = new ThreadExceptions();
        Thread.setDefaultUncaughtExceptionHandler(t1);
        t1.start();
        t1.interrupt();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Exception handled " + e);
    }
}
