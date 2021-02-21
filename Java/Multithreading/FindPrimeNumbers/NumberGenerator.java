package Multithreading_git.FindPrimeNumbers;

public abstract class NumberGenerator {

    private boolean isStop = false;

    public abstract int generateNumber();

    public void stop() {
        this.isStop = true;
    }

    public boolean isStopped() {
        return isStop;
    }
}