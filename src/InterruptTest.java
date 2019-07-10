import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InterruptTest extends Thread{

    @Override
    public void run() {
        System.out.println("work");
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("ding");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String [] args) {
        InterruptTest test = new InterruptTest();
        test.start();
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test.interrupt();

    }
}
