package Multithreading_git.FindPrimeNumbers;

public class PrimeNumberChecker implements Runnable {

    private final NumberGenerator generator;

    public PrimeNumberChecker(NumberGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run() {
        while (!generator.isStopped()) {
            int number = generator.generateNumber();
            System.out.println(Thread.currentThread().getName() + " generated " + number);
            boolean result = check(number);
            synchronized (generator) {
                if (result && !generator.isStopped()) {
                    System.out.println(number + " is prime !");
                    generator.stop();
                }
            }
        }
    }

    public boolean check(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (generator.isStopped() || number % i == 0) {
                return false;
            }
        }
        return true;
    }

}

