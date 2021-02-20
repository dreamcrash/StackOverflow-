package Multithreading_git.ShowTwoThreadSynchronization;

import java.util.*;

public class Example1 {

    private int x;

    public void addX(int y){ x = x + y;}


    public static void main(String[] args) {
        Example1 example = new Example1();
        List<Thread> threads = new ArrayList<>();
        int total_threads = 2;
        for(int i = 1; i < total_threads + 1; i++){
            final int threadID = i;
            Thread thread  = new Thread(() -> {
                for(int j = 0; j < 1000; j++ )
                    example.addX((threadID % 2 == 0) ? -threadID : threadID);
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(example.x);
    }
}