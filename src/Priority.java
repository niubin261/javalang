import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Priority {
    private static volatile boolean noStart = true;
    private static volatile boolean noEnd = true;
    public static void main(String [] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread" + i);
            thread.setPriority(priority);
            thread.start();
        }
        noStart = false;
        TimeUnit.SECONDS.sleep(10);
        noEnd = false;
        for (Job job : jobs) {
            System.out.println("job priority:" + job.priority + ", count:" + job.jobcount);
        }
    }
    static class Job implements Runnable {
        private int priority;
        private long jobcount;
        public Job(int priority) {
            this.priority = priority;
        }
        @Override
        public void run() {
            while (noStart) {
                Thread.yield();
            }
            while (noEnd) {
                Thread.yield();
                jobcount++;
            }
        }
    }

}
