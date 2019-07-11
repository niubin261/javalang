import java.util.concurrent.*;

public class FutureTaskTest {
    public static void main(String [] args) throws ExecutionException, InterruptedException {
        FutureTask<String> f = new FutureTask<>(()->{
            return "future";
        });
        new Thread(f).start();
        try {

            System.out.println(f.get());
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> future = executorService.submit(()-> {
           return Thread.currentThread().getName();
        });
        doSomethingElse();
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private static void doSomethingElse() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
