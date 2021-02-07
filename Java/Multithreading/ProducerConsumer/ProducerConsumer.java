/**
 *  Original Question : https://stackoverflow.com/questions/66016112
 *
 *  I have built producer-consumer using java threads. Producer and consumer are two different
 *  class which refers to single LinkedList and object lock.
 *  What is the problem with the below implementation?
 *
 *  Item Produced by Thread-0 Item 1
 * Item Consumed by Thread-1 Item 1
 *
 *     Exception in thread "Thread-0" Exception in thread "Thread-1" java.lang.IllegalMonitorStateException
 *         at java.lang.Object.notify(Native Method)
 *         at ProducerConsumer$Producer.produce(ProducerConsumer.java:35)
 *         at ProducerConsumer$Producer.run(ProducerConsumer.java:19)
 *         at java.lang.Thread.run(Thread.java:745)
 *     java.lang.IllegalMonitorStateException
 *         at java.lang.Object.notify(Native Method)
 *         at ProducerConsumer$Consumer.consume(ProducerConsumer.java:63)
 *         at ProducerConsumer$Consumer.run(ProducerConsumer.java:50)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 *             import java.util.LinkedList;
 *
 *     public class ProducerConsumer {
 *         LinkedList<Integer> items =  new LinkedList<>();
 *         Object lock = new Object();
 *         int capacity = 10;
 *         public static void main(String[] args) {
 *             ProducerConsumer m = new ProducerConsumer();
 *             Thread p = new Thread(m.new Producer());
 *             Thread c =  new Thread(m.new Consumer());
 *             p.start();
 *             c.start();
 *         }
 *
 *         class Producer implements Runnable{
 *
 *             @Override
 *             public void run() {
 *                 produce();
 *             }
 *
 *             public void produce(){
 *                 int value =0;
 *                 while(true){
 *                     synchronized (lock){
 *                         while(items.size() == capacity){
 *                             try{
 *                                 wait();
 *                             }catch (InterruptedException e){
 *                                 e.printStackTrace();
 *                             }
 *                         }
 *                         items.add(++value);
 *                         System.out.println("Item Produced by "+Thread.currentThread().getName()+" Item "+value);
 *                         notify();
 *                         try{
 *                             Thread.sleep(1000);
 *                         }catch (InterruptedException e){
 *                             e.printStackTrace();
 *                         }
 *
 *                     }
 *                 }
 *             }
 *         }
 *
 *         class Consumer implements Runnable{
 *             @Override
 *             public void run() {
 *                 consume();
 *             }
 *             public void consume(){
 *                 while (true){
 *                     synchronized (lock){
 *                         while (items.size() == 0){
 *                             try{
 *                                 wait();
 *                             }catch (InterruptedException e){
 *                                 e.printStackTrace();
 *                             }
 *                         }
 *                         System.out.println("Item Consumed by "+Thread.currentThread().getName()+" Item "+items.remove());
 *                         notify();
 *                     }
 *                 }
 *             }
 *         }
 *     }
 */


package Multithreading_git;

import java.util.LinkedList;

public class ProducerConsumer {
    final LinkedList<Integer> items =  new LinkedList<>();
    final Object lock = new Object();
    final int capacity = 10;
    public static void main(String[] args) {
        ProducerConsumer m = new ProducerConsumer();
        Thread p = new Thread(m.new Producer());
        Thread c = new Thread(m.new Consumer());
        p.start();
        c.start();
    }

    class Producer implements Runnable{

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) { /** do something **/ }
        }

        public void produce() throws InterruptedException {
            int value =0;
            while(true){ // the infinite loop is in on purpose o simulate continuous consuming and producing
                synchronized (lock){
                    while(items.size() == capacity)
                        lock.wait();
                    items.add(++value);
                    System.out.println("Item Produced by "+Thread.currentThread().getName()+" Item "+value);
                    lock.notify();
                    Thread.sleep(1000);
                }
            }
        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) { /** do something **/ }
        }
        public void consume() throws InterruptedException {
            while (true){ // the infinite loop is in on purpose o simulate continuous consuming and producing
                synchronized (lock){
                    while (items.size() == 0)
                            lock.wait();
                    System.out.println( "Item Consumed by "
                                        +Thread.currentThread().getName()+
                                        " Item "+items.remove());
                    lock.notify();
                }
            }
        }
    }
}