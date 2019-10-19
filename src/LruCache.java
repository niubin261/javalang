import java.util.HashMap;
import java.util.Map;

public class LruCache<K, V> {
    private final int MAX_CACHE_SIZE;
    private Entry<K, V> first;
    private Entry<K, V> last;
    private Map<K, Entry<K, V>> map;
    public LruCache(int capacity) {
        MAX_CACHE_SIZE = capacity;
        map = new HashMap<>();
    }
    public V get(K k) {
        Entry<K, V> entry = map.get(k);
        if (entry == null)return  null;
        moveToFisrt(entry);
        return entry.v;
    }
    public void put(K k ,V v) {
        Entry<K, V> entry = map.get(k);
        if (entry == null) {
            if (map.size() >= MAX_CACHE_SIZE) {
                map.remove(last.k);
                removeLast();
            }
        }
        entry = new Entry<K, V>(k, v);
        moveToFisrt(entry);
        map.put(k, entry);
    }
    private void moveToFisrt(Entry<K, V> entry) {
        if (first == null || last == null) {
            first = last = entry;
            return;
        }
        if (entry == first)return;
        if (entry == last) {
            last = entry.pre;
        }
        if (entry.pre != null) {
            entry.pre.next = entry.next;
        }
        if (entry.next != null) {
            entry.next.pre = entry.pre;
        }
        entry.next = first;
        first.pre = entry;
        first = entry;
        entry.pre = null;
    }
    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null)first = null;
            else last.next = null;
        }
    }
    private static class Entry<K, V> {
        Entry<K, V> pre;
        Entry<K, V> next;
        K k;
        V v;
        Entry(K k, V v) {
            pre = null;
            next = null;
            this.k = k;
            this.v = v;

        }

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Entry<K, V> entry = first;
        while (entry != null) {
            sb.append(String.format("%s : %s ", entry.k, entry.v));
            entry = entry.next;
        }
        return sb.toString();
    }
    public static void main(String [] args) {
        LruCache<Integer, String> lru = new LruCache(5);
        lru.put(1, "11");
        lru.put(2, "22");
        lru.put(3, "33");
        lru.put(4, "44");
        lru.put(5, "55");
        System.out.println(lru.toString() + " ");
        lru.put(6, "66");
        lru.get(2);
        lru.put(7, "77");
        lru.get(4);
        System.out.println(lru.toString());
        System.out.println();
    }
}
