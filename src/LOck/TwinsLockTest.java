package LOck;

import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
    public static void main(String [] args) {
        final Lock lock = new TwinsLock();
        class worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            worker w = new worker();
            w.start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println();
            } catch (InterruptedException e) {

            }
        }


    }
}
