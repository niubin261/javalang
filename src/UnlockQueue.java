public class UnlockQueue {
    private volatile int front = 0;
    private volatile int rear = 0;
    private int size = 0;
    private Object [] buffer;
    private int mask;
    public UnlockQueue(int size) {
        if (!isPowerof2(size)) {
            this.size = roundUpOf2(size);
        }
        buffer = new Object[this.size];
        this.mask = this.size - 1;
    }
    public Object get() {
        if (rear == front) {
            return null;
        }
        Object obj = buffer[front];
        buffer[front] = null;
        front = (front + 1) & mask;
        return obj;
    }
    public boolean put(Object object) {
        if (((rear + 1) & mask) == front) {
            return false;

        }
        buffer[rear] = object;
        rear = (rear + 1) & mask;
        return true;
    }
    private boolean isPowerof2(int n) {
        return (n != 0 && (n & (n - 1)) == 0);

    }
    private int roundUpOf2(int val) {
        int offset = 1;
        int t = 0;
        while ( (t = 1 << offset++) < val);
        return t;
    }
    public static void main(String [] args) {

        UnlockQueue queue = new UnlockQueue(15);
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
        System.out.println(obj3.hashCode());
        queue.put(obj1);
        System.out.println(queue.get().hashCode());
        System.out.println(queue.size);
        queue.put(obj2);
        queue.put(obj3);
        queue.put(obj3);
        queue.put(obj3);
        System.out.println(queue.get().hashCode());
        queue.get();
    }

}
