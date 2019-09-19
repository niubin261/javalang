package LOck;

public class Retry {
    private int maxRetries = 10;
    private volatile boolean is_shutting = false;
    private int retries = 1;
    private int delay = 0;
    public void retry() throws InterruptedException {
        int a = 1;
        for (;;) {
            if (is_shutting) return;
            if (maxRetries > 0 && retries >= maxRetries) {
                return;
            }
            if (a == 1) {
                if (delay == 0) {
                    delay = 1;
                }
                delay = delay * 5;
                Thread.sleep(delay);
                delay = 1 << retries;
            }

        }

    }

}
