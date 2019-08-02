import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void helper2(String[] words) {
        int l = words.length;

        ArrayList<StringBuilder> builders = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < l; i++) {
            if (!map.containsKey(words[i])) {
                map.put(words[i], 1);
            } else {
                map.put(words[i], map.get(words[i]) + 1);
            }

            StringBuilder builder = new StringBuilder();
            String[] t = words[i].split("/");
            int len = t.length - 1;
            builder.append('1');
            if (len == 1) {
                builders.add(builder);
                continue;
            }


            for (int j = 1; j < len - 1; j++) {
                builder.append(map.get(words[i]));
            }
            builder.append('1');
            builders.add(builder);
        }
        for (int i = 0; i < l; i++) {
            System.out.print(builders.get(i).toString());
            System.out.print(" ");


        }

    }

    public static ListNode reverseList(ListNode head) {
        ListNode first = head;
        ListNode reverseHead = null;
        while (first != null) {
            ListNode second = first.next;
            first.next = reverseHead;
            reverseHead = first;
            first = second;
        }
        return reverseHead;
    }

    private static ListNode helper(ListNode head, int k) {


        int count = 0;
        ListNode cur = head;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode pre = preHead;
        while (count >= k) {
            ListNode end = head;
            for (int i = 1; i < k; i++)
                end = end.next;
            ListNode nextHead = end.next;
            end.next = null;
            pre.next = reverseList(head);
            head.next = nextHead;
            pre = head;
            head = pre.next;
            count -= k;
        }
        return preHead.next;
    }

    public static void print(ListNode head) {
        ListNode node = head;
        StringBuilder builder = new StringBuilder();
        builder.append('[');

        while (node.next != null) {
            builder.append(node.val);
            builder.append(',');
            node = node.next;
        }
        builder.append(node.val);
        builder.append(']');
        System.out.println(builder.toString());
    }

    public static int LastRemaining(ArrayList<Integer> arr, int n, int m) {
        if (n == 1) return 0;
        int remaining = n;
        boolean index[] = new boolean[n];
        int number = 0;
        int pos = 0;
        while (true) {

            if (!index[pos]) {
                number++;
                if (number == m) {
                    index[pos] = true;
                    remaining -= 1;
                    if (remaining == 0) break;
                    arr.add(pos + 1);
                    number = 0;
                }
            }
            pos = (pos + 1) % n;

        }
        return pos + 1;
    }


    public static void helper3(int[] arr, int len) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            int step = 0;
            int pos = arr[i];
            while (true) {
                if (pos == 1) {
                    result.add(step);
                    break;
                }
                if (pos % 2 == 0) {
                    pos = pos / 2;
                    step++;
                } else {
                    pos = 3 * pos + 1;
                    step++;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            System.out.println(result.get(i));
        }

    }
    public void backTrack() {

    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

//        String [] words = in.nextLine().split(" ");
//        int n = Integer.valueOf(words[0]);
//        String [] arr = new String[n];
//        for (int i = 1; i < n + 1; i++) {
//            arr[i-1] = words[i];
//        }

        // helper2(arr);
//        int n = in.nextInt();
//        in.nextLine();
//        String[] words = in.nextLine().split(" ");
//        int[] arr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = Integer.valueOf(words[i]);
//        }
//        helper3(arr, n);

        int n = in.nextInt();
        int k = in.nextInt();
        int m = in.nextInt();
        helper2(n,k,m);


    }

    private static void helper2(int n, int k, int m) {
        int result = 0;
        result += (n/m)*k;
        int x = n % m;
        if ( x != 0) {
            result += k;
        } else {
            result += 0;
        }

        System.out.println(result);

    }

}