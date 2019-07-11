import java.io.IOException;
import java.util.Arrays;

public class MinHeap {
    private int [] values = new int[16];
    private int size;
    public int poll() {
        if (size == 0) throw new IllegalStateException("");
        int value = values[0];
        values[0] = values[--size];
        values[size] = 0;
        fixDown();
        return value;
    }
    private int fixDown() {
        int j ;
        int f = 0;
        while ((j = (f << 1 + 1)) < size) {
            if ((j = (f << 1 + 2)) < size) {
                if (values[j] < values[j + 1])j++;
            }
            if (values[j] > values[f]) break;
            int t = values[f]; values[f] = values[j]; values[j] = t;
            f = j;
        }
        return f;

    }
    private int fixUp() {
        int j = size - 1;
        int f;
        while ((f = (j - 1) >> 1) > 0) {
            if (values[f] < values[j])break;
            int t = values[f]; values[f] = values[j]; values[j] = t;
            j = f;
        }
        return f;
    }
    public void push(int value) {
        if (size > values.length) Arrays.copyOf(values, size << 1);
        values[size++] = value;
        fixUp();
    }
}
