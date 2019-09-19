import java.util.*;


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
    private void swap(ArrayList<Integer> result, int i, int j) {
        int m = result.get(i);
        int n = result.get(j);
        result.set(i,n);
        result.set(j,m);
        return;

    }
    private void permuteHelper(int num, int t, ArrayList<Integer>result, int [] A) {
        int len = num;
        if (t >= len) {
            boolean isAdd = true;
            for (int i = 0; i < len - 1; i++) {
                if (A[i] == 1 && result.get(i) < result.get(i + 1)) isAdd = false;
                if (A[i] == 0 && result.get(i) > result.get(i + 1)) isAdd = false;
            }
            if (isAdd)
                results++;
            return;
        } else {
            for (int i = t; i < len; i++) {
                if (i != 0 && A[i] == 1 && result.get(i) < result.get(t))continue;
                if (i != 0 && A[i] == 0 && result.get(i) > result.get(t))continue;
                swap(result,i,t);
                permuteHelper(num,t+1,result,A);
                swap(result,i,t);
            }
        }
        return;

    }
    private  int results = 0;
    public  int permute(int num, int [] A) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < num ; i++) {
            result.add(i + 1);
        }
        permuteHelper(num,0,result, A);
        return results;
    }
    private int[] nextPermute(int [] arr) {
        if (arr == null || arr.length == 0)return null;
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i + 1])i--;
        if (i > 0) {
            int j = i + 1;
            while (j < arr.length && arr[j] > arr[i]) {
                j++;
            }
            j--;
            swap(arr, i, j);
        }
        reverse(arr, i + 1, arr.length - 1);
        return arr;

    }
    private  String[] words = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> s = new ArrayList<>();
        if (digits.isEmpty()) {
            return s;
        }
        String str = "";
        backTrack(0, digits, s, str);
        return s;
    }
    private void backTrack(int left, String digits, ArrayList<String> s, String str) {
        if (digits.length() == left) {
            s.add(str);
            return ;
        }
        String ss = words[digits.charAt(left) - '0'];
        for (int j = 0; j < ss.length(); j++) {
            str += ss.charAt(j);
            backTrack(left + 1, digits, s, str);
            str = str.substring(0, str.length() - 1);
        }
    }
    private int maxLength(int [] arr) {
        int len = arr.length;
        Arrays.sort(arr);
        if (arr[0] == arr[len - 1])return len;
        int res = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++ ) {
                int cur = j;
                int curAns = 2;
                int diff = arr[j] - arr[i];
                for (int k = j + 1; k < len; k++) {
                    if (arr[k] - diff > arr[cur])break;
                    if (arr[k] - arr[cur] == diff) {
                        cur = k;
                        curAns = curAns + 1;
                    }

                }
                if (res < curAns)res = curAns;
            }
        }
        return res;


    }


    public int getTwoSubArrayMinDiff(int[] arr) {
        // TODO Auto-generated method stub
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        int temp[][] = new int[arr.length + 1][sum / 2 + 1];
        for (int i = 0; i < arr.length; i++)
            for (int capacity = 1; capacity <= sum / 2; capacity++) {
                temp[i + 1][capacity] = temp[i][capacity];
                if (arr[i] <= capacity && temp[i][capacity - arr[i]] + arr[i] > temp[i][capacity]) {
                    temp[i + 1][capacity] = temp[i][capacity - arr[i]] + arr[i];
                }
            }
        return Math.min(temp[arr.length][sum / 2], sum - temp[arr.length][sum / 2]);
    }

    public String validIPAddressAll(String IP) {

        if (!IP.contains(".") && !IP.contains(":")) {
            return "Neither";
        }
        if (IP.contains(".")) {
            if (IP.endsWith(".")) {
                return "Neither";
            }
            String[] arr = IP.split("\\.");
            if (arr.length != 4) {
                return "Neither";
            }

            for (int i = 0; i < 4; i++) {
                if (arr[i].length() == 0 || arr[i].length() > 3) {
                    return "Neither";
                }
                for (int j = 0; j < arr[i].length(); j++) {
                    if (arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') {
                        continue;
                    }
                    return "Neither";
                }
                if (Integer.valueOf(arr[i]) > 255 || (arr[i].length() >= 2 && String.valueOf(arr[i]).startsWith("0"))) {
                    return "Neither";
                }
            }
            return "IPv4";
        }

        if (IP.contains(":")) {
            if (IP.endsWith(":") && !IP.endsWith("::")) {
                return "Neither";
            }
            if (IP.indexOf("::") != -1 && IP.indexOf("::", IP.indexOf("::") + 2) != -1) {
                return "Neither";
            }
            if (IP.contains("::")) {
                String[] arr = IP.split(":");
                if (arr.length > 7 || arr.length < 1) {
                    return "Neither";
                }
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("")) {
                        continue;
                    }
                    if (arr[i].length() > 4) {
                        return "Neither";
                    }
                    for (int j = 0; j < arr[i].length(); j++) {
                        if ((arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') || (arr[i].charAt(j) >= 'A' && arr[i].charAt(j) <= 'F')
                                || (arr[i].charAt(j) >= 'a' && arr[i].charAt(j) <= 'f')) {
                            continue;
                        }
                        return "Neither";
                    }
                }
                return "IPv6";
            }

            if (!IP.contains("::")) {
                String[] arr = IP.split(":");
                if (arr.length != 8) {
                    return "Neither";
                }
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].length() > 4) {
                        return "Neither";
                    }
                    for (int j = 0; j < arr[i].length(); j++) {
                        if ((arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') || (arr[i].charAt(j) >= 'A' && arr[i].charAt(j) <= 'F')
                                || (arr[i].charAt(j) >= 'a' && arr[i].charAt(j) <= 'f')) {
                            continue;
                        }
                        return "Neither";
                    }
                }
                return "IPv6";
            }
        }
        return "Neither";
    }



    private int sum(int [] arr, int bng, int len) {
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += arr[bng + i];
        }
        return sum;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Main m = new Main();
        int N = in.nextInt();
        int M = in.nextInt();
        int [] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = in.nextInt();
        }
        int res = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            for (int j = M; j <= N; j++) {
                if (i + j <= N) {
                    int t = 0;
                    if ((t = m.sum(arr, i, j)) < res) res = t;
                } else break;

            }

        }
        System.out.println(res);



    }
    public void swap(int[] num,int i,int j){
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
    public void reverse(int[] num,int i,int j){
        while(i < j)
            swap(num, i++, j--);
    }
    public void printArray(int [] arr) {
        int len = arr.length;
        for(int i = 0; i < len - 1; i++) {
            System.out.print(arr[i]);
            System.out.print(",");
        }
        System.out.print(arr[len - 1]);
        System.out.println("");
    }
    public boolean isPrimer(int num) {
        for (int i = 3; i <= num; i++) {
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) return false;
            }
        }
        return true;
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