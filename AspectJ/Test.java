package logThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public void testExecutorService() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() ->  System.out.println("working on the task"));
        try {
            service.awaitTermination(2, TimeUnit.SECONDS);
            service.shutdown();
        }catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
