
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.*;
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
    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    public boolean isLoopList(ListNode pHead) {
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
    public ListNode EntryNodeOfLoop(ListNode pHead)
    {
        if (pHead == null) return pHead;
        ListNode slow = pHead;
        ListNode fast = pHead;
        boolean isLoop = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                isLoop = true;
                break;
            }
        }
        if (!isLoop)return null;
        ListNode p = slow;
        ListNode q = pHead;
        while (p != null && q != null) {
            if (p == q) break;
            p = p.next;
            q = q.next;
        }
        return p;

    }
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> res = new ArrayList<>();
        if (listNode == null)return res;
        Stack<ListNode> s = new Stack<>();
        ListNode p = listNode;
        while (p != null) {
            s.push(p);
            p = p.next;
        }
        while (!s.isEmpty()) {
            res.add(s.pop().val);
        }
        return res;
    }
    public boolean Find(int target, int [][] array) {
        int row = array.length;
        int col = array[0].length;
        int posX = row - 1;
        int posY = 0;
        while (posX >= 0 && posY < col) {
            int value = array[posX][posY];
            if (value == target) return true;
            if (value > target) {

            }

        }
        return false;

    }
    public String replaceSpace(StringBuffer str) {
        int l1 = str.length();
        int spaceCnt = 0;
        for (int i = 0; i < l1; i++) {
            if (str.charAt(i) == ' ') {
                spaceCnt++;

            }
        }
        char [] ss = new char[l1 + spaceCnt * 2];
        for (int i = l1 - 1; i >= 0; i--) {
            if (str.charAt(i) == ' ') {
                spaceCnt--;
                ss[2 * spaceCnt + i] = '%';
                ss[2 * spaceCnt + i + 1] =  '2';
                ss[2 * spaceCnt + i + 2] = '0';
            } else {
                ss[2 * spaceCnt + i] = str.charAt(i);
            }
        }
        return new String(ss);
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ZTE zte = new ZTE();
        //2.write you code
        System.out.println('A' + 1);
        System.out.println(zte.replaceSpace(new StringBuffer("We Are Happy")));

    }
}


