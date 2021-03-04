package Multithreading_git.FindPrimeNumbers;

import java.util.Random;

public class IntegerNumberGenerator extends NumberGenerator {

    private Random random;
    int randomAtama;

    public IntegerNumberGenerator() {
        this.random = new Random();
    }

    @Override
    public int generateNumber() {
        return random.nextInt(100) + 1;
    }
}
