import com.sun.rmi.rmid.ExecOptionPermission;
import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

interface StringTest {
    String test(char[] a);
}

interface A {

    default void a() {
        System.out.println("This is a.a");
    }

    void aA();

}

interface B {
    default void b() {
        System.out.println("This is b.b");
    }
}

interface C {
    void c();
}

class E implements Serializable {
    void print() {
        System.out.println("e");
    }
}

class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        super("Thread-1");
        this.name = name;

    }

    @Override
    public void run() {
        try {
            System.out.println(name);
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread2 implements Runnable {
    private int tickets = 20;

    @Override
    public void run() {
        while (tickets > 0) {
            System.out.printf("ticket is %d\n", tickets--);
        }
    }
}

class Tickets {
    private int size;
    private int number;
    private boolean avaible;

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public boolean isAvaible() {
        return avaible;
    }

    public void setAvaible(boolean avaible) {
        this.avaible = avaible;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Tickets(int size) {
        this.size = size;
    }

}

class TicketProducer extends Thread {
    private Tickets tickets;

    public TicketProducer(Tickets tickets) {
        this.tickets = tickets;
    }

    @Override
    public void run() {
        synchronized (tickets) {
            while (tickets.getNumber() < tickets.getSize()) {
                int t = tickets.getNumber();
                tickets.setNumber(++t);
                tickets.setAvaible(true);
            }
        }
    }
}

class TicketComsumer extends Thread {
    private int i = 0;
    private Tickets tickets;

    public TicketComsumer(Tickets tickets) {
        this.tickets = tickets;
    }
    ThreadLocal<Integer> a = new ThreadLocal<>();
    @Override
    public void run() {
        synchronized (tickets) {

            if (tickets.getNumber() > 0 && tickets.isAvaible()) {
                int t = tickets.getNumber();
                tickets.setNumber(--t);
            }
            if (i == tickets.getNumber()) {
                tickets.setAvaible(false);
            }
        }
    }
}

class TestClient extends Thread implements Runnable {

    private SequenecNumber sequenecNumber;

    public TestClient(SequenecNumber sequenecNumber) {
        this.sequenecNumber = sequenecNumber;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {

            System.out.println(sequenecNumber.getNextNum());
        }
    }
}

class SequenecNumber {
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        SequenecNumber sequenecNumber = new SequenecNumber();
        TestClient testClient1 = new TestClient(sequenecNumber);
        TestClient testClient2 = new TestClient(sequenecNumber);
        TestClient testClient3 = new TestClient(sequenecNumber);
        testClient1.start();
        testClient2.start();
        testClient3.start();
    }
}

class CalcTest {

    public static Integer calc(Integer para) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        return para * para;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(50));
        System.out.println(future.get());
    }
}

class CalcTest2 {

    public static Integer calc(Integer para) {
        return para / 6;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable command) {

            }
        };
        CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .exceptionally(ex -> {
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        fu.get();
    }

}

class Test {
    public static void main(String[] args) throws ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 10000;
        });
        executorService.submit(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete(10);
            System.out.println("ok");
        });
        try {

            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class CalcCompose {

    public static Integer calc(Integer para) {
        return para / 2;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        fu.get();
    }
}

class DispatchLoop implements Callable<Integer> {
    private String name;
    private volatile boolean stop;
    private volatile Future<?> dispatchFuture;
    private final BlockingDeque<Integer> integerQueue;
    private final ExecutorService executor;

    DispatchLoop(String name) {
        this.name = name;
        executor = newSingleThreadExecutor();
        integerQueue = new LinkedBlockingDeque<>();
        dispatchFuture = executor.submit(this::call);
    }

    public boolean add(int a) {
        return integerQueue.add(a);
    }

    @Override
    public Integer call() {
        stop = false;
        while (!stop) {
            try {
                System.out.println(this.name);
                int a = integerQueue.take();
                System.out.println(a);
            } catch (InterruptedException e) {

            }
        }
        return 1;
    }
}

class Person {
    private String name;

    public Person(Builder builder) {
        name = builder.name;

    }

    private static class Builder {
        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;

        }

        public Person build() {
            return new Person(this);
        }
    }

    public static void main(String[] args) {
        Person person = new Person.Builder().name("hi").build();
        System.out.println(person.name);
    }

}

class DispatchTest {
    public static void main(String[] args) {

        DispatchLoop firstDispatch = new DispatchLoop("first");
        DispatchLoop secondDispatch = new DispatchLoop("second");
        System.out.println("here");
        Thread t = new Thread(() -> {
            int i = 0;
            while (true) {

                firstDispatch.add(i++);
            }
        });
        Thread t2 = new Thread(() -> {
            int i = 0;
            while (true) {

                secondDispatch.add(i--);
            }
        });
        t.start();
        t2.start();

//        firstDispatch.add(1);
//        firstDispatch.add(2);
//        firstDispatch.add(3);
//        firstDispatch.add(4);
//        firstDispatch.add(5);
//        secondDispatch.add(-1);
//        secondDispatch.add(-2);
//        secondDispatch.add(-3);
//        secondDispatch.add(-4);
//        secondDispatch.add(-5);
//        secondDispatch.add(-6);
    }


}

class LatchTest {
    public static void main(String[] args) {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        latch1.await();
                        System.out.println("work");
                        latch2.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        System.out.println("bg");
        latch1.countDown();
        try {
            latch2.await();
            System.out.println("he");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread.yield();
    }
}

class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        semaphore = new Semaphore(bound);
    }

    public boolean add(T o) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded)
                semaphore.release();
        }
    }

    public boolean remove(T o) {
        boolean was = set.remove(o);
        if (was) {
            semaphore.release();
        }
        return was;
    }
}

class BoundedHashSetTest {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        ArrayList<Integer> list = (ArrayList<Integer>) s.stream().collect(Collectors.toList());
        System.out.println(list);
        BoundedHashSet<Integer> set = new BoundedHashSet<>(3);
        set.add(1);
        set.add(2);
        set.add(3);
        set.remove(3);
        set.add(4);
    }
}


class CopyOnWriteArrayListTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        ExecutorService executorProducer = newFixedThreadPool(1);
        Future<?> future;
        int i = 0;
        future = executorProducer.submit(() -> {
            list.set(0, 2);
            System.out.println(list.get(0));
            return 1;
        });
        System.out.println("Here");
        System.out.println(future.get());

        if (future.isDone())
            executorProducer.shutdown();

    }


}

class FutureTest {

    public static void main(String[] args) {
        LinkedList<Integer> queue = new LinkedList<>();

        Callable<Integer> c = () -> {
            return 5 + 6;
        };
        FutureTask<Integer> f = new FutureTask<>(c);
        Thread thread = new Thread(f);
        thread.start();
        try {
            Integer result = f.get();
            System.out.println(result);
        } catch (Exception e) {

        }

    }
}

class CompletableFutureTest {
    private static int cal() {

        return 8;
    }
    private static void s() {
        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> {
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            System.out.println(10);
            return 9;
        }).whenComplete((result,e) -> {
            System.out.println(100);

        });
        System.out.println("exit");
//        CompletableFuture<String> reponse = new CompletableFuture<>();
//        reponse.complete("hello");
//        System.out.println("ok");
    }


}

class TestNet {
    public static void main(String[] args) throws Exception {
        URL cs = new URL("https://www.baidu.com/");
        URLConnection tc = cs.openConnection();
        tc.setRequestProperty("accept", "*/*");
        tc.setRequestProperty("connection", "Keep-Alive");
        tc.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
        String inline;
        while ((inline = in.readLine()) != null) {
            System.out.println(inline);
        }
        in.close();
    }
}

class StreamTest {
    public static void main(String[] args) {
        Integer a[] = new Integer[]{1, 2, 3, 4, 5, 5};
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(a));
        System.out.println(arrayList.stream().filter(t -> {
            return t != 2;
        }).collect(Collectors.toSet()));
        System.out.println(arrayList.stream().filter(t -> {
            return t != 2;
        }).collect(Collectors.toSet()));

        Optional<Integer> result = arrayList.stream().map(t -> {
            return t * t;
        }).reduce((sum,num)-> sum + num);

        result.ifPresent(System.out::println);
        String s1 = "abc";
        String s2 = "abc";
        Integer I1 = new Integer(1);
        Integer I2 = new Integer(1);

        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);

        System.out.println(I1 == I2);
        System.out.println(I1.equals(I2));
        char s [] = new char[] {'1','1'};
        StringBuilder builder = new StringBuilder();
        builder.append(s);
        System.out.println(builder.toString());


    }
}

class D {
    private static void repeat(IntConsumer consumer) {

        for (int i = 0; i < 10; i++) {
            consumer.accept(i);
        }
    }



    public static void main(String[] args) {
        String st = "a"+"b";
        String ss = "ab";
        System.out.println(st.equals(ss));
        System.out.println(st==(ss));
        String sm = new String("ab");
        System.out.println(st==sm);
        System.out.println(sm.intern()==st);
        String tt = String.valueOf(1);
        String tt1 = String.valueOf(1);
        System.out.println(tt1.equals(tt));
        System.out.println(tt1==(tt));
        Tickets tickets = new Tickets(10);
        new TicketComsumer(tickets).start();
        new TicketProducer(tickets).start();
        MyThread thread = new MyThread("ok");
        thread.start();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("hello ok");
            }
        };

        MyThread2 myThread2 = new MyThread2();
        new Thread(myThread2).start();
        new Thread(myThread2).start();
        new Thread(myThread2).start();

        Thread thread2 = new Thread(() -> {
            System.out.println("hello thread lambda");
        });
        thread2.start();
        try {
            FileWriter writer = new FileWriter("hello.txt");
            writer.write("hello");
            writer.write("world");
            writer.close();

        } catch (IOException iox) {
            System.out.println("error");
            return;
        }
        try {
            FileReader reader = new FileReader("hello.txt");
            int c = reader.read();
            while (c != -1) {
                System.out.print((char) c);
                c = reader.read();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("io error");
            return;
        }
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("hello.txt"));

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("hello.txt"));
            bufferedWriter.write("hello");
            bufferedWriter.newLine();
            bufferedWriter.write("world");

            bufferedWriter.close();
            String s = bufferedReader.readLine();
            while (s != null) {
                System.out.println(s);
                s = bufferedReader.readLine();

            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("error io");
            return;
        }
        try {

            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("hello.dat")));
            dataOutputStream.writeInt(1);
            dataOutputStream.writeInt(2);
            dataOutputStream.close();
        } catch (IOException e) {
            System.out.println("io error");
        }
        try {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("hello.dat")));
            try {
                int a = dataInputStream.readInt();
                while (true) {
                    System.out.println(a);
                    a = dataInputStream.readInt();
                }
            } catch (EOFException e) {
                System.out.println("EOF");
            }
        } catch (IOException e) {
            System.out.println("IO error");
        }
        try {
            E e = new E();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("hello.txt"));
            objectOutputStream.writeObject(e);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("io error");

        }
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream("hello.txt")));

            try {
                E e = (E) inputStream.readObject();
                e.print();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Arrays arrays = new Arrays();
//        Arrays.sort(people,Comparator.comparing(People::getName));
        HashMap<Integer, String> map = new HashMap<Integer, String>();


        GeneralType<Integer> generalType = new GeneralType<>(1);
        generalType.printType();
        GeneralType<String> generalType1 = new GeneralType<>("GeneralType");
        generalType1.printType();
        GeneralMethod generalMethod = new GeneralMethod();
        generalMethod.generalMethod("GeneralMethod");
        StringTest stringTest = String::new;
        String t = stringTest.test(new char[]{'h', '0'});
        System.out.println(t);
        repeat(System.out::println);
        repeat(new IntConsumer() {
            @Override
            public void accept(int i) {
                System.out.println(i);
            }
        });
        int x = 10;
        C c = new C() {
            @Override
            public void c() {
                System.out.println("This is C.c");
            }

        };
        c.c();
        C c1 = () -> {
            System.out.println("This is C.c");
            System.out.println(x);
        };
        c1.c();
        A a = new A() {
            @Override
            public void aA() {
                System.out.println("This is A.a");
            }

            @Override
            public void a() {
                System.out.println("This is A.a");
            }
        };
        A a1 = () -> {
            System.out.println("This is A.aA");
        };
        a.a();
        a1.aA();
    }


}
class PrimeGenerator implements Runnable {
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean isCancelled = false;
    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!isCancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }
    public void cancel() {
        isCancelled = true;
    }
    public synchronized List<BigInteger> get()  {
        return new ArrayList<BigInteger>(primes);
    }
    public static void main(String [] args) throws InterruptedException{
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        System.out.println(generator.get());
    }

}


class LogWriter{
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    public LogWriter(PrintWriter writer){
        this.queue = new LinkedBlockingQueue<>();
        this.logger = new LoggerThread(writer);
    }

    public void start(){
        logger.start();
    }

    public void log(String msg) throws InterruptedException{
        queue.put(msg);
    }

    private class LoggerThread extends Thread{
        private final PrintWriter writer;;
        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }
        public void run(){
            try{
                while(true){
                    writer.println(queue.take());
                }
            } catch(InterruptedException ignored){

            } finally {
                writer.close();
            }

        }
    }

}

