package LOck;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock {
    private static Map<String, String> m = new HashMap<>();
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock  rLock = reentrantReadWriteLock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    public String get(String k) {
        rLock.lock();
        try {
            return m.get(k);
        } finally {
            rLock.unlock();
        }
    }
    public String put(String k, String v) {
        writeLock.lock();
        try {
            return m.put(k,v);
        } finally {
            writeLock.unlock();
        }
    }



}
