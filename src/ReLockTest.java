public class ReLockTest {
    private boolean locked = false;
    public synchronized void lock() throws InterruptedException{
        while (locked) {
            wait();
        }
        locked = true;
    }
    public synchronized void unlock() throws InterruptedException{
        locked = false;
        notifyAll();
    }
    public void doAdd() {



    }
    public static void sort(int [] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }

            }
        }
    }
    private static void swap(int [] arr, int i, int j) {
        int t = arr[j];
        arr[j] = arr[i];
        arr[i] = t;
    }
    public static void main(String[] args) throws InterruptedException{
        ReLockTest lockTest = new ReLockTest();

        int [] arr = new int[]{3,6,4,2,11,10,5};
        sort(arr);
        System.out.println();

    }

}
