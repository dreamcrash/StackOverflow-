/** Original Question : https://stackoverflow.com/questions/65840463/
 *
 * Calculating Pi using Monte Carlo method in parallel
 *
 * I have been trying to calculate the value of pi using Monte Carlo simulation in parallel.
 *
 * So far, I have created threads, and generated the random points, but I'm not able to return
 * those generated points back from the threads to the main thread.
 *
 * How to tackle this problem ?
 *
 * Below is the code I have written
 *
 */

package Multithreading_git;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

class Task implements Callable<Integer> {
    final int points;

    public Task(int points){
        this.points = points;
    }

    @Override
    public Integer call() {
        int insidePoints = 0;
        int prec = 1000000;
        for( int i=0 ; i < points ; ++i){
            double x = (double)ThreadLocalRandom.current().nextInt(prec + 1)/(double)prec;
            double y = (double)ThreadLocalRandom.current().nextInt(prec + 1)/(double)prec;
            if( (x*x + y*y) <= 1){
                ++insidePoints;
            }
        }
        System.out.println("Thread " + Thread.currentThread().getId() + " running and Inside points are " + insidePoints);
        return insidePoints;
    }
}

class Main {
    public static void main(String[] args){
        int totalPoints = 1000000;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the value of Threads : ");
        int threadsToBeUsed = scan.nextInt();

        int eachThreadPoints = totalPoints/threadsToBeUsed;
        ExecutorService pool = Executors.newFixedThreadPool(threadsToBeUsed);
        List<Future<Integer>> results = new ArrayList<>(threadsToBeUsed);
        for(int i = 0; i < threadsToBeUsed; i++){
            Future<Integer> insidePointsThr = pool.submit(new Task(eachThreadPoints));
            results.add(insidePointsThr);
        }
        int insidePoints = results.stream().mapToInt(Main::getFutureResult).sum();

        System.out.println("Number of inside points :" + insidePoints);
        System.out.println("Pi/4 = " + (double)insidePoints/(double)totalPoints);
        System.out.println("Pi   = " + 4*(double)insidePoints/(double)totalPoints);

        pool.shutdown();
    }

    private static int getFutureResult(Future<Integer> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            // handle the error
        }
        return 0;
    }
}
