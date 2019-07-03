
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;

public class ZTE {

    public CyclicBarrier barrier = new CyclicBarrier(3);
    public static int[][] gridTopo = new int[956][];// 存放原始拓扑信息
    public static int[][] request = new int[4001][];// 存放所有业务需求信息

    public static void readTxt() throws IOException {
        String s;
        int i;
        // 1.read gridtopo
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        s = in.readLine();
        i = 0;
        for (i = 0; i < 956; i++) {
            String[] temp = s.split("\\ ");
            gridTopo[i] = new int[temp.length];
            for (int kk = 0; kk < temp.length; kk++) {
                gridTopo[i][kk] = Integer.parseInt(temp[kk]);
            }
            s = in.readLine();
        }
        // 2.read request
        i = 0;
        for(i = 0; i< 4001;i++) {
            String[] temp = s.split("\\ ");
            request[i] = new int[temp.length];
            for (int kk = 0; kk < temp.length; kk++) {
                request[i][kk] = Integer.parseInt(temp[kk]);
            }
            s = in.readLine();
        }
    }
    public static void tofinoLog(int N, int l, int m) {
        int z = 0;
        for (int i = 0; i < N; i++) {
            StringBuilder builder = new StringBuilder();
            int min = Integer.min(m - 1, N - 1 -i);
            int max = Integer.max(0, N - i -m);
            for (int j = 1; j <= Math.pow(2,min) ; j++) {
                int match = (int) (Math.pow(2,min) - j);
                int ternary = max;
                match = (1 << (max + min)) + (match << max) + (1 << (ternary / 2));
                int value = (int) ((Math.log(match) / Math.log(2) * Math.pow(2,l - Math.log(N)/Math.log(2))));

                System.out.println("match == " + Integer.toBinaryString(match));
                System.out.println("value == " + value);
                System.out.println("tenrary == " + ternary);
                z++;
            }

        }
        System.out.println(z);
    }
    public static void tofinoExp(int N, int l, int i) {

        for (int j = 0; j < Math.pow(2,l); j++) {
            int match = j;

            double value = Math.pow(2,(j*1.0)/Math.pow(2,l - Math.log(N)/Math.log(2)));
            System.out.println("match == " + match);
            System.out.println("value == " + (int)value);
        }
    }


    private static class SortWorker implements Runnable {
        private int [] data = null;
        public SortWorker(int [] data) {
            this.data = data;
        }
        @Override
        public void run () {
            Arrays.sort(data);


        }
    }
    public String convert(String s, int nRows) {
        if (nRows <= 1)return s;
        StringBuilder res = new StringBuilder();
        int size = 2*nRows - 2;
        for (int i = 0; i < nRows; i++) {
            for (int j = i; j < size; j+=size) {
                res.append(s.charAt(j));
                int t = j + size - 2 * i;
                if (i != 0 && i != nRows - 1 && t < s.length())res.append(s.charAt(t));
            }
        }
        return res.toString();
    }
    private static class MergeWorker implements Callable {
        private int [] data1;
        private int [] data2;
        public MergeWorker(int [] data1, int [] data2) {
            this.data1 = data1;
            this.data2 = data2;
        }

        @Override
        public String  call() throws Exception {
            int l1 = data1.length;
            int l2 = data2.length;
            int [] res = new int[l1 + l2];
            int i = 0;
            int j = 0;
            int k = 0;
            while (i < l1 && j < l2) {
                if (data1[i] < data2 [j]){
                    res[k++] = data1[i++];
                } else {
                    res[k++] = data2[j++];
                }

            }
            while (i < l1) {
                res[k++] = data1[i++];
            }
            while (j < l2) {
                res[k++] = data2[j++];
            }
            return Arrays.toString(res);
        }
    }
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int [] data1 = new int[] {1,3,5,7,9};
        int [] data2 = new int[] {1,2,3,5,7,9};
        new Thread(new SortWorker(data1)).start();
        new Thread(new SortWorker(data2)).start();
        FutureTask<String> f = new FutureTask<>(new MergeWorker(data1,data2));
        new Thread(f).start();
        System.out.println((String) f.get());
        data1 = null;
        data2 = null;
        //1.输入

        tofinoLog(16,6,3);
        tofinoExp(16,6,0);
        //2.write you code

    }
}