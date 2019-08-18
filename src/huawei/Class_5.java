package huawei;

import java.util.ConcurrentModificationException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
public class Class_5 {
    public static Lock lock = new ReentrantLock();
    public static Condition condition1 = lock.newCondition();
    public static Condition condition2 = lock.newCondition();
    public static Condition condition3 = lock.newCondition();
    public static Condition condition4 = lock.newCondition();
    private static class Func1 implements Runnable {

        @Override
        public void run() {
            String s = "hello";

            lock.lock();

            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("A");
                    condition2.signal();
                    if (i != 9) {

                        condition1.await();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }



        }
    }
    private static class Func2 implements Runnable {
        @Override
        public void run() {
            lock.lock();


            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("B");
                    condition3.signal();
                    if (i != 9) {

                        condition2.await();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    private static class Func3 implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("C");
                    condition4.signal();
                    if (i != 9) {

                        condition3.await();
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    private static class Func4 implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("D");
                    condition1.signal();
                    if (i != 9) {

                        condition4.await();
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String [] args) throws InterruptedException {
        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
        m.putIfAbsent("hello", "hello");
        Map<String, String> mm = new TreeMap<>();
        mm.put("hello", null);
        Map<String, String> mmm = new Hashtable<>();
        mmm.put("hello", null);

        Scanner in = new Scanner(System.in);

        Thread Func1 = new Thread(new Func1());
        Thread Func2 = new Thread(new Func2());
        Thread Func3 = new Thread(new Func3());
        Thread Func4 = new Thread(new Func4());
        Func1.start();
        Func2.start();
        Func3.start();
        Func4.start();
        Func1.join();
        Func2.join();
        Func3.join();
        Func4.join();
    }
}
