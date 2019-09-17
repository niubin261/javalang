package LOck;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProCom {
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private final int size = 100;
    private Object [] array = new Object[size];
    private volatile int cnt = 0;
    public void produce(Object o) throws InterruptedException {
        lock.lock();
        try {
            while (cnt == size) {
                notFull.await();
            }
            array[cnt++] = o;
            notEmpty.signal();
        } catch (InterruptedException e) {
            throw e;
        } finally {
            lock.unlock();
        }

    }
    public Object consume() throws InterruptedException {
        lock.lock();
        try {
            while (cnt == 0) {
                notEmpty.await();
            }
            Object o = array[cnt];
            array[cnt] = null;
            cnt--;
            notFull.signal();
            return o;
        } catch (InterruptedException e) {
            throw e;
        } finally {
            lock.unlock();
        }

    }
    public static void main(String [] args) throws InterruptedException {
        ProCom pc = new ProCom();
        Thread producer = new Thread(() -> {
            try {
                pc.produce(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        Thread Consumer = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        Consumer.start();
    }

}
