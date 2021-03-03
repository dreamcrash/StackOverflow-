import java.util.concurrent.Future;

public aspect Example {
     
     Object around(Runnable task) : call(public Future<?> java.util.concurrent.ExecutorService+.submit(Runnable)) 
     					&& args(task)
     {
         final long parentID = Thread.currentThread().getId();
         Runnable newTask =  () -> {
                 System.out.println("Parent Thread Id: "+ parentID);
                 System.out.println("Child Thread Id: "+Thread.currentThread().getId()); 
                 task.run();
         };
         return proceed(newTask);
     }
}