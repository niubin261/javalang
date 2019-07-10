package LOck;

public class Output implements Runnable {
    private int num;
    private Object lock;
    public Output(Object lock, int num) {
        this.num = num;
        this.lock = lock;

    }
    public void setNum() {
        this.num = this.num * 2;
    }
    @Override
    public void run() {
        try {
            for(;;) {
                synchronized(lock) {
                    lock.notifyAll();
                    lock.wait();
                    setNum();
                    System.out.println(num);

                }

            }
        }catch (InterruptedException e) {
            e.printStackTrace();

        }
    }
    public static void main(String [] args) {
        final Object lock = new Object();
        Thread thread1 = new Thread(new Output(lock,1));
        Thread thread2 = new Thread(new Output(lock,2));
        thread1.start();
        thread2.start();

    }
}