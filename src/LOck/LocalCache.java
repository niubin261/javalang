package LOck;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
public class LocalCache {
    private static final int DEFAULT_MAX_NUMBER = 100;
    private final Map<String, Value> cache;
    private final int maxNumber;
    private final AtomicInteger cur = new AtomicInteger(0);

    public LocalCache() {
        this(DEFAULT_MAX_NUMBER);
    }
    public LocalCache(int maxNumber) {
        this.maxNumber = maxNumber;
        this.cache = new ConcurrentHashMap<>(maxNumber);
    }
    public boolean put(String key, Object value, long expire) {
        if (value == null || expire < 0) {
            return  false;
        }
        if (!incr()) {
            return  false;
        }
        if (isOver()) {
            expireAll();
            if (isOver()) {
                decr();
                return false;
            }
        }
        putValue(key, value, expire);
        return true;
    }
    public Object get(String key) {
        Value value = cache.get(key);
        if (value == null) {
            return null;
        }
        if (isExpired(value)) {
            removeValue(key);
            return null;
        }
        return value.value;
    }
    private boolean isExpired(Value value) {
        return System.currentTimeMillis() - value.updateTime > value.expire;
    }
    private void expireAll() {
        for (Map.Entry<String , Value> entry : cache.entrySet()) {
            if (isExpired(entry.getValue())) {
                removeValue(entry.getKey());
            }
        }
    }
    private void putValue(String key, Object value, long expire) {
        Value v = new Value(System.currentTimeMillis(), expire, value);
        if (cache.put(key, v) != null) {
            decr();
        }
    }
    private void removeValue(String key) {
        if (cache.remove(key) != null) {
            decr();
        }
    }

    private boolean isOver() {
        return cur.get() > maxNumber;
    }
     private boolean incr() {
        int c = cur.get();
        return cur.compareAndSet(c, c + 1);
     }
    private void decr() {
        for (;;) {
            int c = cur.get();
            if (c == 0) {
                return;
            }
            if (cur.compareAndSet(c, c - 1)) {
                return;
            }
        }
    }
    private static class Value {
        private long updateTime;
        private long expire;
        private Object value;
        private Value(long updateTime, long expire, Object value) {
            this.updateTime = updateTime;
            this.expire = expire;
            this.value = value;
        }
    }
    public static void main(String [] args) throws  InterruptedException{
        long start = System.currentTimeMillis();
        LocalCache localCache = new LocalCache();
        int n = 5_00;
        int m = 1_000_00;
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            new  Thread( () -> {
                for (int j = 0; j < m; j++) {
                    localCache.put(j + "", new Object(), 10);
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(localCache.cache.size());
        System.out.println(localCache.cur);

    }
}
