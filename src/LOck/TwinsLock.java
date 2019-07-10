package LOck;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
    private final Sync  sync = new Sync(2);
    private  static class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            setState(count);

        }
        public int tryAcquireShared(int reduceCount) {
            //for(;;){
            //
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || ( !hasQueuedPredecessors() && compareAndSetState(current, newCount))) {
                    return newCount;
                }
                return -1;

        }
        public boolean tryReleaseShared(int reduceCount) {

                int current = getState();
                int newCount = current + reduceCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
                return false;
        }
    }
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    @Override
    public void lockInterruptibly() {

    }
}
