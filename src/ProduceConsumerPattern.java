import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
class Producer implements Runnable {
    private BlockingQueue<String> buf;
    private BlockingQueue<String> msgs;
    private AtomicLong producers;
    public Producer(BlockingQueue<String> buf, BlockingQueue<String> msgs, AtomicLong producers) {
        this.producers = producers;
        this.buf = buf;
        this.msgs = msgs;

    }

    @Override
    public void run() {
        try {
            String s;
            while ((s = msgs.poll()) != null) {
                buf.offer(s);
                System.out.println(Thread.currentThread().getName() + "producers" + s );
            }
        } finally {
            producers.decrementAndGet();
        }
    }
}
class Consumer implements Runnable {
    private BlockingQueue<String> drop;
    private BlockingQueue<String> msgs;
    private AtomicLong producers;
    public Consumer(BlockingQueue<String> drop, AtomicLong producers) {
        this.drop = drop;
        this.producers = producers;
    }
    @Override
    public void run() {
        String s;
        while ((s = drop.poll())!= null) {
            System.out.println(Thread.currentThread().getName() + "consumer" + s);

        }
        if (producers.get() == 0)  {

        }
    }
}
public class ProduceConsumerPattern {


        private static final int P_NUM = 1; // 记录生产者的数量
        private static final AtomicLong P_NOW_COUNT = new AtomicLong(P_NUM);

        public static void main(String[] args) {
            BlockingQueue<String> messages = initMes();
            BlockingQueue<String> drop = new LinkedBlockingQueue(100);

            long begin = System.currentTimeMillis();
            Map<String, Thread> threadMap = new HashMap<String, Thread>();
            for (int i = 1; i <= 5; i++) {
                if (i <= P_NUM) {
                    String name = "线程A" + i;
                    threadMap.put(name, new Thread(new Producer(drop, messages, P_NOW_COUNT), name));
                } else {
                    String name = "线程B" + i;
                    threadMap.put(name, new Thread(new Consumer(drop, P_NOW_COUNT), name));
                }
            }
            for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
                entry.getValue().start();
            }
            for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
                try {
                    entry.getValue().join();
                } catch (InterruptedException e) {
                }
            }
            long end = System.currentTimeMillis();

            System.out.println("耗费时间： " + (end - begin));
        }
        public static BlockingQueue<String> initMes() {
            List<String> messages = Arrays.asList("cat ", "dog ", "pig", "fish", "sheep", "cattle",
                    "duck", "chicken", "goose", "bird", "tiger", "lion", "elephant", "wolf", "mouse",
                    "leopard");
            BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
            queue.addAll(messages);
            return queue;
        }



}
