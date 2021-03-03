/** Original Question : https://stackoverflow.com/questions/53029722
 * 
 * How can I log Thread Id of Parent & Child using AspectJ in Java
 * 
 * I'm new to AspectJ. Was able to create a simple JUnit and Aspect classes to log ThreadId, which logs Parent Thread Id. But I'm not able to figure out how to log Child ThreadId.

Given the following snippet of code, I'd like to log Thread Id of both parent and child using AspectJ.

JUnit:

@Test
public void testExecutorService() {
    ExecutorService service = Executors.newSingleThreadExecutor();
    Runnable task = new Runnable() {
        @Override
        public void run() {
            System.out.println("working on the task");
        }
    };
    service.submit(task);
}
Aspect: The following aspect logs Parent ThreadId.

before() :
    call(* ExecutorService+.submit(..))
 {
    System.out.println("Parent Thread Id: "+Thread.currentThread().getId());
    //System.out.println("Child Thread Id: "+??); //?? - how to capture child thread id?
 }
I understand that it is using "before" advice here and also it is intercepting 
submit method, which might be an issue as well. How can I log child 
Thread Id along with Parent Thread Id using correct Pointcut expression?
 * 
 */

package logThread;

public class Main {
    public static void main(String[] args) {
	new Test().testExecutorService();
    }
}
