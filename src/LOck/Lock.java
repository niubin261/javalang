package LOck;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class Lock implements java.util.concurrent.locks.Lock {
    private static class Sync extends AbstractQueuedSynchronizer {
        public boolean isHeldExclusively() {
            return getState() == 1;
        }
        public boolean tryAcquire() {

            if (compareAndSetState(0,1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        public boolean tryRelease(int release) {
            if (getState() == 0 ) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }
    private Sync sync = new Sync();

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock() {
        return sync.tryRelease(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {

        return sync.tryAcquire();
    }

    @Override
    public void lockInterruptibly() {

    }
}
