import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskTest {
    public static void main(String [] args) {
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task1,1000);
        timer.schedule(task2,100);
    }
}
