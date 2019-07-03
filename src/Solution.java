import com.sun.deploy.util.SyncAccess;

import java.io.Serializable;
import java.util.*;


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    public RandomListNode(int label) {
        this.label = label;
    }
}

class Point {
    int x;
    int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }
}

public class Solution {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static int LastRemaining_Solution(int n, int m) {
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
                    number = 0;
                }
            }
            pos = (pos + 1) % n;

        }
        return pos;
    }

    public static int findLongest01Seq(char[] str) {
        if (str == null) return -1;


        int len = str.length;
        if (len == 0) return 0;
        int midResult[] = new int[len];
        midResult[0] = 1;
        for (int i = 1; i < len; i++) {
            if (str[i] != str[i - 1]) {
                midResult[i] = midResult[i - 1] + 1;
            } else {
                midResult[i] = 1;
            }
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            if (result < midResult[i]) {
                result = midResult[i];
            }
        }
        return result;

    }

    public static void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        ArrayList<Integer> leftHalf = new ArrayList<>();
        ArrayList<Integer> rightHalf = new ArrayList<>();
        int len = array.length;
        if (array == null) return;
        if (len == 0) return;
        int xorResult = array[0];
        for (int i = 1; i < len; i++) {
            xorResult = xorResult ^ array[i];
        }
        int pos = 0;
        while (xorResult != 0) {
            if ((xorResult & 1) == 1) {
                break;
            }
            xorResult = xorResult >> 1;
            pos++;

        }
        int t = pos << 1;
        for (int i = 0; i < len; i++) {
            if ((array[i] & t) == 0) {
                leftHalf.add(array[i]);
            } else {
                rightHalf.add(array[i]);
            }
        }
        int leftHalfLen = leftHalf.size();
        int rightHalfLen = rightHalf.size();
        num1[0] = leftHalf.get(0);
        num2[0] = rightHalf.get(0);
        for (int i = 1; i < leftHalfLen; i++) {
            num1[0] = num1[0] ^ leftHalf.get(i);
        }
        for (int i = 1; i < rightHalfLen; i++) {
            num2[0] = num2[0] ^ rightHalf.get(i);
        }


    }

    public static String ReverseSentence(String str) {
        String dummy = " ";
        str = str + dummy;
        ArrayList<String> copy = new ArrayList<String>();
        int len = str.length();
        int j = 0;
        int i = 0;
        while (i < len && j < len) {


            if (str.charAt(i) == ' ') {
                i++;
                j++;
            } else if (str.charAt(j) != ' ') {
                j++;
            }
            if (str.charAt(j) == ' ') {
                copy.add(str.substring(i, j));
                j++;
                i = j;
            }
        }

        Collections.reverse(copy);
        StringBuilder result = new StringBuilder();
        len = copy.size();
        for (i = 0; i < len; i++) {
            result.append(copy.get(i)).append(" ");
        }

        return result.toString();
    }

    private static boolean helper(char[] matrix, int rows, int cols, int i, int j, char[] str, int k, int[] flag) {
        int index = i * cols + j;
        if (i < 0 || i >= rows || j < 0 || j >= cols || matrix[index] != str[k] || flag[index] == 1)
            return false;
        if (k == str.length - 1) return true;
        flag[index] = 1;
        if (helper(matrix, rows, cols, i - 1, j, str, k + 1, flag)
                || helper(matrix, rows, cols, i + 1, j, str, k + 1, flag)
                || helper(matrix, rows, cols, i, j - 1, str, k + 1, flag)
                || helper(matrix, rows, cols, i, j + 1, str, k + 1, flag)) {
            return true;
        }
        flag[index] = 0;
        return false;
    }

    private int step = 1;

    private boolean isOk(int i, int j, int threshold) {

        int result = 0;

        while (i != 0) {
            result += i % 10;
            i = i / 10;
        }
        while (j != 0) {
            result += j % 10;
            j = j / 10;

        }
        return result <= threshold;
    }

    private int backTrack(int i, int j, int rows, int cols,
                          boolean[][] visited, int threshold) {
        if (i < 0 || i >= rows || j < 0 || j >= cols
                || !isOk(i, j, threshold) || !visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        int left = backTrack(i - 1, j, rows, cols, visited, threshold);
        int right = backTrack(i + 1, j, rows, cols, visited, threshold);
        int up = backTrack(i, j + 1, rows, cols, visited, threshold);
        int down = backTrack(i, j - 1, rows, cols, visited, threshold);
        return left + right + up + down + 1;


    }

    public int movingCount(int threshold, int rows, int cols) {
        boolean visited[][] = new boolean[rows][cols];
        int step = 0;
        if (threshold < 0) return 0;

        return backTrack(0, 0, rows, cols, visited, threshold);
    }

    private static boolean hasPathHelper(int i, int j, int rows, int cols, boolean[] visited, int step, char[] matrix, char[] str) {
        int pos = rows * i + j;
        if (i < 0 || i > rows - 1 || j < 0 || j > cols - 1 || visited[pos] ||
                step > str.length - 1 || str[step] != matrix[pos]) {
            return false;
        }

        if (step == str.length - 1) {
            return true;
        }
        visited[pos] = true;
        if (hasPathHelper(i, j + 1, rows, cols, visited, step + 1, matrix, str)
                || hasPathHelper(i, j - 1, rows, cols, visited, step + 1, matrix, str)
                || hasPathHelper(i + 1, j, rows, cols, visited, step + 1, matrix, str)
                || hasPathHelper(i - 1, j, rows, cols, visited, step + 1, matrix, str)) {
            return true;
        } else {

            visited[pos] = false;
            return false;
        }

    }

    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        int len = matrix.length;
        boolean visited[] = new boolean[len];
        int step = 0;
        boolean result = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (hasPathHelper(i, j, rows, cols, visited, step, matrix, str)) {
                    return true;
                }

            }
        }
        return false;
    }

    public static ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> result = new ArrayList<Integer>();

        int len = array.length;
        int result1 = 0;
        int result2 = 0;
        int mul = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            int pos = find(array, i, sum - array[i]);
            if ((pos) != -1) {

                if (array[i] * array[pos] < mul) {
                    result1 = array[i];
                    result2 = array[pos];
                }
            }

        }
        result.add(result1);
        result.add(result2);
        return result;

    }

    private static int find(int[] array, int i, int j) {
        int start = i + 1;
        int end = array.length;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (array[middle] > j) {
                end = middle - 1;
            } else if (array[middle] < j) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static int StrToInt(String str) {
        int result = 0;
        int len = str.length();
        boolean index = false;
        int a = 1;
        if (result > Integer.MAX_VALUE) {
            result = 0;
        }
        if (result < Integer.MIN_VALUE) {
            result = 0;
        }
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if ((c == '+' || c == '-')) {
                if (!index) {
                    a = (c == '+' ? 1 : -1);
                    index = true;
                } else {
                    result = 0;
                    break;
                }
            } else if ('0' <= c && c <= '9') {
                result += (c - '0') * Math.pow(10, len - 1 - i);
            } else {
                result = 0;
                break;
            }

        }
        return result;
    }

    synchronized public static int FindGreatestSumOfSubArray(int[] array) {
        int result = Integer.MIN_VALUE;
        int len = array.length;
        int[] b = new int[len];
        b[0] = array[0];
        if (len == 1) {
            result = b[0];
        } else {
            for (int i = 1; i < len; i++) {
                b[i] = Math.max(b[i - 1] + array[i], array[i]);
                if (result < b[i]) {
                    result = b[i];
                }
            }
        }
        return result;


    }

    public static void swap(int[] input, int i, int j) {

        int t = input[i];
        input[i] = input[j];
        input[j] = t;
    }

    public static boolean parentValid(int i) {
        int parent = (i - 1) >> 1;
        return parent >= 0;
    }

    public static void precolateUp(int[] input, int i) {
        int parent;
        while ((parent = (i - 1) >> 1) >= 0) {
            if (input[parent] > input[i]) {
                swap(input, i, parent);
                i = parent;
            } else {
                break;
            }
        }

    }

    public static int properChildren(int[] input, int i, int j) {
        int lchild = (i << 1) + 1;
        int rchild = (i << 1) + 2;
        if (lchild > j && rchild > j) return -1;
        int lval = Integer.MAX_VALUE;
        int rval = Integer.MAX_VALUE;
        if (lchild <= j) {
            lval = input[lchild];
        }
        if (rchild <= j) {
            rval = input[rchild];
        }
        return lval > rval ? rchild : lchild;


    }

    public static void precolateDown(int[] input, int end) {
        int parent = 0;
        int t;
        while ((t = properChildren(input, parent, end)) != -1) {
            swap(input, parent, t);
            parent = t;
        }
    }

    public static void buildHeap(int[] input) {
        int len = input.length;
        int i = 0;

        for (int j = 0; j < len; j++) {
            precolateUp(input, j);
        }
    }

    public static int findMax(int[] num, int beg, int end) {
        int max = Integer.MIN_VALUE;
        int result = -1;
        for (int i = beg; i <= end; i++) {
            if (num[i] > max) {
                max = num[i];
                result = i;
            }
        }
        return result;
    }

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) return null;
        HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
        ListNode node = pHead;
        while (node != null) {
            if (m.containsKey(node.val)) {
                m.put(node.val, m.get(node.val) + 1);
            } else {
                m.put(node.val, 1);
            }
            node = node.next;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = pHead;
        ListNode p = dummy;
        ListNode q = pHead;
        ListNode r = pHead.next;
        while (r != null) {
            if (m.get(q.val) != 1) {
                p.next = r;
                q = r;
                r = r.next;
            } else {
                p = q;
                q = r;
                r = r.next;
            }
        }
        return dummy.next;


    }

    public static ArrayList<Integer> maxInWindows(int[] num, int size) {
        PriorityQueue<Integer> a = new PriorityQueue<>(15, (o1, o2) -> {
            return o2 - o1;
        });
        ArrayList<Integer> result = new ArrayList<Integer>();
        int windowbeg = 0;
        int windowend = 0;
        int len = num.length;
        int pos = -1;
        for (; windowbeg < len - size + 1; windowbeg++) {
            windowend = windowbeg + size - 1;
            if (pos == windowbeg - 1) {
                pos = findMax(num, windowbeg, windowend);

            } else {
                if (num[pos] > num[windowend]) {
                } else {
                    pos = windowend;

                }

            }
            result.add(num[pos]);

        }
        return result;

    }

    public static int addjustHeap(int[] input, int i) {
        int result = input[0];
        swap(input, 0, input.length - 1 - i);
        precolateDown(input, input.length - 1 - i - 1);
        return result;

    }

    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int len = input.length;
        buildHeap(input);
        for (int i = 0; i < k; i++) {
            result.add(addjustHeap(input, i));
        }
        return result;
    }

    private static boolean isUglyNumber(int t) {
        boolean isUgly = true;
        for (int i = 2; i <= t; ) {
            if (t % i == 0) {
                if (i != 2 && i != 3 && i != 5) {
                    isUgly = false;
                    break;
                }
                t /= i;
            } else {

                i++;
            }
        }
        return isUgly;
    }

    public static int GetUglyNumber_Solution(int index) {
        if (index == 1) return 1;
        int i = 2;
        int cnt = 1;
        while (true) {
            if (isUglyNumber(i)) {
                cnt++;
                if (cnt == index) {
                    break;
                }
            }
            i++;
        }
        return i;
    }

    public static int FirstNotRepeatingChar(String str) {
        int len = str.length();
        LinkedHashMap<Integer, Integer> m = new LinkedHashMap<Integer, Integer>();

        for (int i = 0; i < len; i++) {
            if (!m.containsKey(Integer.valueOf(str.charAt(i)))) {
                m.put(Integer.valueOf(str.charAt(i)), 1);
            } else {

                int val = m.get(Integer.valueOf(str.charAt(i)));
                m.put(Integer.valueOf(str.charAt(i)), val + 1);
            }
        }
        System.out.println(m);
        Iterator iter = m.entrySet().iterator();
        int t = -1;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) (iter.next());
            int a = (int) entry.getValue();
            if (a == 1) {
                t = (int) entry.getKey();
                break;
            }
        }
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == t) {
                return i;
            }
        }

        return -1;


    }

    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        double t = 0;
        for (int i = sum; i >= 2; i--) {
            t = (2. * sum + i - i * i) / (2. * i);
            if (t - (int) t == 0.0f && t > 0.0) {
                ArrayList<Integer> subresult = new ArrayList<Integer>();
                for (int j = 0; j < i; j++) {
                    subresult.add((int) t + j);
                }
                result.add(subresult);
            }

        }
        return result;
    }

    public static int maxAliveDay(int x, int f, int d, int p) {
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < d; i++) {

            int t = Integer.min(i / p + f, (100 - i) / x);
            if (result < t) {

                result = t;
            }

        }
        return result;
    }

    public static int findMax(ArrayList<Integer> array) {
        int maxa = Integer.MIN_VALUE;
        int posa = -1;
        int maxb = Integer.MIN_VALUE;
        int posb = -1;
        int maxc = Integer.MIN_VALUE;

        int mind = Integer.MAX_VALUE;
        int posd = -1;
        int mine = Integer.MAX_VALUE;

        int len = array.size();
        if (len < 3) return Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            if (array.get(i) > maxa) {
                maxa = array.get(i);
                posa = i;
            }
        }
        for (int i = 0; i < len; i++) {
            if (array.get(i) > maxb && i != posa) {
                maxb = array.get(i);
                posb = i;
            }

        }
        for (int i = 0; i < len; i++) {
            if (array.get(i) > maxc && i != posa && i != posb) {
                maxc = array.get(i);

            }
        }
        for (int i = 0; i < len; i++) {
            if (array.get(i) < mind) {
                mind = array.get(i);
                posd = i;
            }
        }
        for (int i = 0; i < len; i++) {
            if (array.get(i) < mine && i != posd) {
                mine = array.get(i);

            }
        }
        return Integer.max(maxa * maxb * maxc, mind * mine * maxa);

    }

    public static boolean isNumeric(char[] str) {
        if (str == null) return false;
        int len = str.length;
        if (len == 0) return false;
        int pos = 0;
        if (str[pos] == '+' || str[pos] == '-') {
            pos++;
        }
        if (len <= pos) return false;
        boolean result = true;
        pos = scanDigit(str, pos);
        if (pos < len) {
            if (str[pos] == '.') {
                pos++;
                pos = scanDigit(str, pos);
                if (pos == len) return result;
                else if (str[pos] == 'e' || str[pos] == 'E') {

                } else {
                    result = false;
                }

            } else if (str[pos] == 'e' || str[pos] == 'E') {

            } else {
                result = false;
            }
        }
        return result;


    }

    private static int scanDigit(char[] str, int pos) {
        int len = str.length;
        while (str[pos] <= '9' && str[pos] >= '0' && pos != len) {
            pos++;
        }
        return pos;

    }

    private static boolean isExp(char[] str, int pos) {
        if (str[pos] != 'e' && str[pos] != 'E') return false;
        pos++;
        if (str[pos] == '+' || str[pos] == '-') pos++;
        if (pos == str.length) return false;
        scanDigit(str, pos);
        return pos == str.length ? true : false;
    }

    public static String BigIntSub(String a, String b) {
        StringBuilder result = new StringBuilder();
        int lena = a.length();
        int lenb = b.length();

        int borrow = 0;
        for (int i = lenb - 1; i >= 0; i--) {
            if (a.charAt(i) < b.charAt(i)) {

                result.append(10 + a.charAt(i) - b.charAt(i) - borrow);
                borrow = 1;


            } else {
                result.append(10 + a.charAt(i) - b.charAt(i) - borrow);


            }
        }
        if (borrow == 1) {
            result.append(a.charAt(lena - 1 - lenb) - borrow);
        }
        return result.reverse().toString();
    }

    public static String BigIntAdd(String a, String b) {

        StringBuilder result = new StringBuilder();
        int lena = a.length();
        int lenb = b.length();

        int lenMin = lena > lenb ? lenb : lena;
        int carry = 0;
        for (int i = lenMin - 1; i >= 0; i--) {
            int t = carry + a.charAt(i) - '0' + b.charAt(i) - '0';
            carry = t / 10;
            result.append((t % 10));


        }
        if (lena > lenMin) {

            for (int i = lena - 1 - lenMin; i >= 0 && carry > 0; i--) {
                int t = carry + (a.charAt(i) - '0');
                carry = t / 10;
                result.append((t % 10));
            }
        } else {
            for (int i = lenb - 1 - lenMin; i >= 0 && carry > 0; i--) {
                int t = carry + b.charAt(i) - '0';
                carry = t / 10;
                result.append((t % 10) + '0');
            }
        }
        if (carry != 0) {
            result.append(carry);
        }
        return result.reverse().toString();
    }

    public static int method(char[] str) {
        int len = str.length;
        HashSet set = new HashSet();
        for (int i = 0; i < len; i++) {
            set.add(str[i]);
        }
        int size = set.size();
        if (size == 1) {
            return 1;
        }

        if (size == 2) {
            return 2;
        }
        return 0;

    }

    public static void reverseN(int[] array) {
        int len = array.length;
        ArrayList<Integer> arrayList = new ArrayList<>();


        for (int i = 0; i < len; i++) {
            arrayList.add(array[i]);
            Collections.reverse(arrayList);

        }
        for (int i = 0; i < len; i++) {
            System.out.print(arrayList.get(i));
            System.out.print(' ');

        }

    }

    public static int moreChildren(int n, int[] h, int m, int[] w) {
        int hlen = h.length;
        int wlen = w.length;
        Arrays.sort(h);
        Arrays.sort(w);
        int result = 0;
        if (wlen > hlen) {
            for (int i = wlen - hlen, j = 0; i < wlen && j < hlen; i++) {
                if (w[i] > h[j]) {
                    result++;
                }
            }
        } else {
            for (int i = 0; i < wlen; i++) {
                if (w[i] > h[i]) {
                    result++;
                }
            }

        }
        return result;

    }

    public static void BigIntMultiple(String a, String b) {
        if(a == "0" || b == "0")return;
        int la = a.length();
        int lb = b.length();
        int[] result = new int[la + lb];
        int n = 0;
        for (int i = la - 1; i >= 0; i--) {
            for (int j = lb - 1; j >= 0; j--) {
                int x = a.charAt(i) - '0';
                int y = b.charAt(j) - '0';
                result[la + lb - 1 - (la - i - 1) - (lb - j - 1)] =
                        result[la + lb - 1 - (la - i - 1) - (lb - j - 1)] + x*y;
                n++;
            }
        }
        int carry = 0;
        for (int i = (la + lb - 1); i >=0; i--) {
            int t = result[i] + carry;
            result[i] = t % 10;
            carry = t / 10;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (result[i] == 0)i++;
        for (;i < la + lb ; i++) {
            builder.append(result[i]);
        }
        builder.toString();

    }

    public int run(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int currentDepth = 1;
        int childrenlayerNodes = 0;
        int parentlayerNodes = 1;
        while (queue.isEmpty()) {
            TreeNode t = queue.peek();
            if (t.left != null) {
                queue.add(t.left);
                childrenlayerNodes++;
            }
            if (t.right != null) {
                queue.add(t.right);
                childrenlayerNodes++;
            }
            if (t.left == null && t.right == null) {
                return currentDepth;
            }
            queue.removeFirst();
            parentlayerNodes--;
            if (parentlayerNodes == 0) {
                currentDepth++;
                parentlayerNodes = childrenlayerNodes;
                childrenlayerNodes = 0;
            }

        }
        return currentDepth;
    }

    public static void compareString(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();


        if (len1 > len2) {
            System.out.println("Greater");
            return;
        }

        if (len1 < len2) {
            System.out.println("Less");
            return;
        }

        int i = 0;
        for (; i < len1; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 > c2) {
                System.out.println("Greater");
                return;
            }
            if (c1 < c2) {
                System.out.println("Less");
                return;
            }

        }
        System.out.println("Equal");
        return;

    }

    public static void kcopiedStringCompare(char[] x1, int k1, char[] x2, int k2) {
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < k1; i++) {
            builder1.append(x1);
        }
        for (int i = 0; i < k2; i++) {
            builder2.append(x2);
        }
        int a = Integer.valueOf("11");
        compareString(builder1.toString(), builder2.toString());


    }




    private static int pos = 0;


    public static char findN(int num) {
        int a = 4;
        int sum = 0;
        int i = 1;
        int t = 0;
        while (true) {
            sum = sum + a;
            t = num - sum;
            a = 2 * a;
            if (t <= a) {
                break;
            }
            i++;

        }
        int m = (int) (4 * Math.pow(2.0, (double) i));
        int average = m / 4;
        if (t > 3 * average) {
            return 'd';
        } else if (t > 2 * average && t < 3 * average) {
            return 'c';
        } else if (t < 2 * average && t > average) {
            return 'b';
        } else {
            return 'a';
        }

    }

    private static class Bear implements Comparable {
        public int hungerVal;
        public int EneryVal;

        Bear(int a, int b) {
            this.EneryVal = b;
            this.hungerVal = a;
        }

        @Override
        public int compareTo(Object o) {
            Bear bear = (Bear) o;
            return this.EneryVal - bear.EneryVal;
        }
    }

    private static class Food implements Comparable {
        public int foodVal;
        public boolean eated = false;

        Food(int a) {
            foodVal = a;
        }

        @Override
        public int compareTo(Object o) {
            Food a = (Food) o;
            return this.foodVal - a.foodVal;
        }
    }

    public static void bearEatSoultion(Bear[] bears, Food[] food) {
        Arrays.sort(bears, Collections.reverseOrder());
        Arrays.sort(food, Collections.reverseOrder());
        int l1 = bears.length;
        int l2 = food.length;

        for (int i = 0; i < l1; i++) {
            for (int j = 0; j < l2; j++) {
                if (!food[j].eated && bears[i].hungerVal >= food[j].foodVal) {
                    bears[i].hungerVal = bears[i].hungerVal - food[j].foodVal;
                    food[j].eated = true;
                }
            }
        }
        System.out.println("he");
    }

    public static void quickSort(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        quickSortHelper(arr, left, right);

    }

    private static void quickSortHelper(int[] arr, int left, int right) {
        if (left > right) return;
        int pos = partition(arr, 0, left, right);
        quickSortHelper(arr, left, pos - 1);
        quickSortHelper(arr, pos + 1, right);

    }

    public static void merge(int[] arr, int left, int middle, int right, int[] temp, Comparator comparator) {
        int i = left;
        int j = middle + 1;
        int t = 0;
        while (i <= middle && j <= right) {
            if (comparator.compare(arr[i], arr[j]) <= 0) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        while (i <= middle) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }


    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(arr, left, middle, temp);
            mergeSort(arr, middle + 1, right, temp);
            merge(arr, left, middle, right, temp, (o1, o2) -> {
                if ((int) o1 % 2 == 1 && (int) o2 % 2 == 0) {
                    return -1;
                } else if ((int) o1 % 2 == 0 && (int) o2 % 2 == 1) {
                    return 1;
                } else
                    return 0;
            });
        }
    }

    public static void heapSort(int[] arr) {

    }

    public static void shellSort(int[] arr) {

    }

    public static boolean VerifySquenceOfBST(int[] sequence) {
        boolean result = false;
        return result;

    }

    public static int partition(int[] arr, int k, int left, int right) {
        int i = left;
        int j = right;
        int t = arr[left];
        while (i != j) {

            while (arr[j] >= t && j > i) {
                j--;

            }
            while (arr[i] <= t && i < j) {
                i++;
            }
            if (i < j) {

                swap(arr, i, j);
            }
        }

        swap(arr, left, i);
        return i;
    }

    public static int kthElement(int[] arr, int k) {
        if (k < 0 || k >= arr.length) return -1;
        int left = 0;
        int right = arr.length - 1;
        int pos = partition(arr, k, left, right);
        while (pos >= 0 && pos <= arr.length - 1) {
            if (k == pos) {
                return pos;
            } else if (k > pos) {
                pos = partition(arr, k, pos + 1, right);
            } else {
                pos = partition(arr, k, left, pos - 1);
            }

        }
        return -1;
    }

    public static RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) return null;

        RandomListNode p = pHead;
        while (p != null) {
            RandomListNode clone = new RandomListNode(p.label);
            clone.next = p.next;
            p.next = clone;
            p = clone.next;
        }
        RandomListNode oldNode = pHead;
        RandomListNode newNode = oldNode.next;
        while (newNode != null) {
            RandomListNode oldrandomNode = oldNode.random;
            RandomListNode newRandomNode;
            if (oldrandomNode != null) {
                newRandomNode = oldrandomNode.next;
            } else {
                newRandomNode = null;
            }
            newNode.random = newRandomNode;
            oldNode = newNode.next;
            if (oldNode == null)break;
            newNode = oldNode.next;
        }
        RandomListNode dummy = new RandomListNode(0);
        dummy.random = null;
        oldNode = pHead;
        newNode = oldNode.next;
        RandomListNode node = dummy;
        while (newNode != null) {

            node.next = newNode;
            node = newNode;
            oldNode = newNode.next;
            if (oldNode == null)break;
            newNode = oldNode.next;

        }
        return dummy.next;

    }



    public static void numOfInslandHelper(int x, int y, boolean[][] visited, int[][] map) {
        int[][] next = {
                {0, 1},
                {1, 0},
                {0, -1},
                {-1, 0}
        };
        int tx;
        int ty;

        for (int i = 0; i < 4; i++) {
            tx = x + next[i][0];
            ty = y + next[i][1];
            if (tx < 0 || tx > map.length - 1 || ty < 0 || ty > map[0].length - 1) continue;
            if (!visited[tx][ty] && map[tx][ty] > 0) {
                visited[tx][ty] = true;
                numOfInslandHelper(tx, ty, visited, map);
            }

        }
        return;

    }

    public static int numOfInsland(int[][] map) {
        int row = map.length;
        int col = map[0].length;
        boolean visited[][] = new boolean[row][col];
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!visited[i][j] && map[i][j] > 0) {
                    visited[i][j] = true;
                    numOfInslandHelper(i, j, visited, map);
                    count++;
                }
            }
        }

        return count;
    }

    public static void dijkstra(int[][] map, boolean[] visited, int[] dis) {
        int len = dis.length;
        int u = 0;
        for (int i = 1; i < len; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 1; j < len; j++) {
                if (visited[j] && dis[j] < min) {
                    min = dis[j];
                    u = j;
                }
            }
            visited[u] = false;
            for (int v = 1; v < len; v++) {
                if (map[u][v] != -1) {

                    if (dis[u] + map[u][v] < dis[v]) {
                        dis[v] = dis[u] + map[u][v];

                    }
                }
            }
        }
    }

    public static void prime(int[][] map, boolean[] visited, int[] dis) {
        int len = dis.length;
        int[] preNode = new int[len];
        visited[0] = false;
        int u = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < len; i++) {
            dis[i] = map[0][1];
        }
        preNode[0] = 0;
        for (int j = 0; j < len; j++) {
            if (visited[j] && map[u][j] != -1) {
                if (dis[j] < map[u][j]) {
                    dis[j] = map[u][j];
                    preNode[j] = u;
                }
            }
        }
        for (int i = 1; i < len; i++) {

            for (int j = 0; j < len; j++) {
                if (visited[j] && dis[j] < min) {
                    u = j;
                    min = dis[j];
                }
            }
            visited[u] = false;
            for (int j = 0; j < len; j++) {
                if (visited[j] && map[u][j] != -1) {
                    if (dis[j] < map[u][j]) {
                        dis[j] = map[u][j];
                        preNode[j] = u;
                    }
                }
            }
        }


    }

    public static TreeNode searchBST(TreeNode root, TreeNode hot, int target) {
        if (root == null) return null;
        if (root.val == target) return root;
        hot = root;
        return searchBST(root.val > target ? root.left : root.right, hot, target);
    }

    public static TreeNode deleteNodeOfBST(TreeNode root, int target) {
        TreeNode hot = root;
        TreeNode t = searchBST(root, hot, target);


        if (t != null) {
            if (t.left == null && t.right == null) {
                t = null;
            }
            if (t.left != null && t.right == null) {

            }
        }
        return null;
    }

    public static TreeNode addNodeOfBST(TreeNode root, int val) {
        TreeNode hot = root;
        TreeNode t = searchBST(root, hot, val);
        if (t == null) {
            TreeNode node = new TreeNode(val);
            t = node;
        }
        return root;
    }

    public static boolean huawei2(String str) {
        int len = str.length();
        if (str.charAt(0) != str.charAt(1) - 32) {
            return false;
        }
        for (int i = 1; i < len / 2; i++) {
            if (str.charAt(2 * i) != str.charAt(2 * i + 1) - 32) {
                return false;
            }
            if (str.charAt(2 * i) != str.charAt(2 * i - 2) + 1) {
                return false;
            }
        }
        Byte a = new Byte("11");
        return true;
    }

    public void swap(int parm1, int parm2) {
        int t = parm1;
        parm1 = parm2;
        parm2 = t;
    }

    public void reOrderArray(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; ) {
            for (int j = len - 1; j >= 0; ) {
                if (array[i] % 2 == 0 && array[j] % 2 != 0) {
                    int t = array[i];
                    array[i] = array[j];
                    array[j] = t;
                    i++;
                    j--;
                } else if (array[i] % 2 != 0) {
                    i++;
                } else if (array[j] % 2 == 0) {
                    j--;
                }

            }
        }
    }

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        boolean result = false;
        if (root1 != null && root2 != null) {
            if (root1.val == root2.val) {
                result = Tree1HasTree2(root1, root2);
            }
            if (!result) {
                result = HasSubtree(root1.left, root2);
            }
            if (!result) {
                result = HasSubtree(root1.right, root2);
            }
        }
        return result;
    }

    public boolean Tree1HasTree2(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 != null) return false;
        if (root2 == null) return true;
        if (root1.val != root2.val) return false;
        return Tree1HasTree2(root1.left, root2.left) && Tree1HasTree2(root1.right, root2.right);
    }

    public boolean match(char[] str, char[] pattern) {
        int ls = str.length;
        int lp = pattern.length;
        int j = 0;
        for (int i = 0; i < lp; i++) {
            int m = i;
            if (j == ls) return true;
            for (j = 0; j < ls && j < lp; j++) {
                if (pattern[m] == '.') {
                    j++;
                    m++;


                }
                if (pattern[m] == '*') {
                    while (true) {

                        if (str[j] == pattern[m - 1]) {
                            j++;
                        } else {
                            break;
                        }
                    }
                    m++;
                }
                if (pattern[m] <= 'z' && pattern[m] >= 'a') {
                    if (str[j] == str[m]) {
                        j++;
                        m++;
                    } else {
                        break;
                    }
                }
            }


        }
        return false;

    }

    public static ListNode mergeList(ListNode head1, ListNode head2) {
        ListNode p = head1;
        ListNode q = head2;
        ListNode dummy = new ListNode(-1);
        ListNode node = dummy;
        while (p != null && q != null) {
            if (p.val <= q.val) {
                node.next = p;
                p = p.next;
            } else {
                node.next = q;
                q = q.next;
            }
            node = node.next;
        }
        if (p != null) {
            node.next = p;

        }
        if (q != null) {
            node.next = q;
        }
        return dummy.next;

    }

    //o(nlgn)
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode left = head;
        ListNode right = slow.next;
        slow.next = null;
        left = sortList(left);
        right = sortList(right);
        return mergeList(left, right);

    }



    public static ListNode insertionSortList(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode q = head.next;
        while (q != null) {
            ListNode p = head;
            ListNode node = q;
            while (p != q) {
                if (q.val < p.val) {
                    pre.next = q;
                    p.next = q.next;
                    q.next = p;
                    break;
                } else {
                    pre = p;
                    p = p.next;
                }

            }
            q = node.next;
        }
        return dummy.next;
    }


    public static void InsertionSort(int[] arr) {
        int i = 0;
        int len = arr.length;
        for (int j = 1; j < len; j++) {
            int t = arr[j];
            for (int k = j - 1; k >= 0; k--) {
                if (t < arr[k]) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }

            }
            arr[j + 1] = t;
        }

    }

    public static boolean wordBreakHelper(String s, Set<String> dict, int start) {
        if (start == s.length()) return true;
        int len = s.length();
        for (int i = start + 1; i <= len; i++) {
            if (dict.contains(s.substring(start, i)) && wordBreakHelper(s, dict, i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean wordBreak(String s, Set<String> dict) {
        int len = s.length();
        if (len == 0) return false;
        return wordBreakHelper(s, dict, 0);
    }

    private static ListNode locateMiddleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }

    private static ListNode reverseList(ListNode head) {
        if (head == null && head.next == null) return head;
        ListNode p = head;
        ListNode q = head.next;
        ListNode r;
        while (q != null) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }
        head.next = null;
        return p;
    }

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;
        ListNode p = head;
        ListNode q = locateMiddleNode(head);

        reorderList(q.next);
        q.next = null;
        q = q.next;
        while (p != null && q != null) {
            ListNode q1 = q.next;
            ListNode p1 = p.next;
            p.next = q;
            q.next = p1;
            p = p1;
            q = q1;
        }


    }
    private List<String> wordBreakerIIhelper(String s, Set<String> dict,
                                     HashMap<String,LinkedList<String>> m) {
        if(m.containsKey(s)) return m.get(s);
        LinkedList<String> res = new LinkedList<String>();

        if(s.length() == 0){
            res.add("");

            return res;
        }
        for (String word : dict) {
            if (s.startsWith(word)) {
                List<String> sublist = wordBreakerIIhelper(s.substring(word.length()),dict,m);
                for (String sub : sublist) {
                    res.add(word + (sub.isEmpty() ? "" : " ") + sub);
                }
            }


        }
        m.put(s,res);
        return res;

    }
    public ArrayList<String> wordBreakII(String s, Set<String> dict) {
        HashMap<String, LinkedList<String>> m = new HashMap<>();
        return (ArrayList<String>) wordBreakerIIhelper(s,dict,m);
    }

    private static int min = Integer.MAX_VALUE;


    public static void dfs(String s, int start, ArrayList<ArrayList<String>> results, ArrayList<String> result, int step) {
        int len = s.length();
        if (start == len) {
            results.add(new ArrayList<>(result));
            if (step < min) {
                min = step;
            }
            return;
        }
        for (int i = start; i < len; i++) {
            if (isPalindrome(s.substring(start, i + 1))) {
                result.add(s.substring(start, i + 1));
                dfs(s, i + 1, results, result, step + 1);
                result.remove(result.size() - 1);

            }
        }
        return;


    }

    public static boolean isPalindrome(String s) {

        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(j) != s.charAt(i)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    private static int tep = 0;

    public static ArrayList<ArrayList<String>> partition(String s) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        dfs(s, 0, results, result, 0);
        System.out.println(results);
        return results;
    }
    private static boolean isIp(String s) {
        if (s.length() == 0 || s == null) return false;
        int t = Integer.valueOf(s);
        if (t < 256) {
            return true;
        }
        return false;
    }


    private static void restoreIpAddressHelper(String s, int start, int t, ArrayList<String> result,
                                        ArrayList<String>results) {

        if (result.size() == 4 && start == s.length()) {
            results.add(result.get(0) + "." +  result.get(1) + "." + result.get(2) + "." + result.get(3));
            return;
        }
        if ((s.length() - result.size() * 3) > 3* (4 - result.size()))return;
        if ((s.length() - result.size()) < (4 - result.size()))return;
        for (int i = start; i < 3 + start && i < s.length(); i++) {
            if (i > start && s.charAt(start)== '0')break;

            if (isIp(s.substring(start,i + 1))) {
                result.add(s.substring(start,i + 1));
                restoreIpAddressHelper(s,i + 1,t,result,results);
                result.remove(result.size() - 1);
            }
        }
    }

    public static ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        restoreIpAddressHelper(s,0,1,result,results);
        return results;
    }

    private static void swap(ArrayList<Integer> result, int i, int j) {
        int m = result.get(i);
        int n = result.get(j);
        result.set(i,n);
        result.set(j,m);
        return;

    }
    private static void permuteHelper(int [] num, int t, ArrayList<Integer>result,
                               ArrayList<ArrayList<Integer>> results) {
        int len = num.length;
        if (t >= len) {
            results.add(new ArrayList<Integer>(result));
            return;
        } else {
            for (int i = t; i < len; i++) {
                if (i > t && result.get(i).equals(result.get(t)))continue;
                swap(result,i,t);
                permuteHelper(num,t+1,result,results);
                swap(result,i,t);
            }
        }
        return;

    }
    public static ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        Arrays.sort(num);
        for (int i = 0; i < num.length ; i++) {
            result.add(num[i]);
        }
        if (num.length == 0) return results;
        permuteHelper(num,0,result,results);
        return results;


    }
    public static ListNode partition(ListNode head, int x) {
        if (head == null)return null;
        ListNode dummy1 = new ListNode(-1);
        ListNode p1 = dummy1;
        ListNode dummy2 = new ListNode(-1);
        ListNode p2 = dummy2;
        ListNode dummy3 = new ListNode(-1);
        ListNode p3 = dummy3;
        ListNode p = head;
        ListNode q = p;
        while (p != null) {
            if (p.val == x) {
                p2.next = p;
                q = p.next;
                p.next = null;
                p2 = p2.next;

            } else if(p.val < x) {
                p1.next = p;
                q = p.next;
                p.next = null;
                p1 = p1.next;
            } else {

                p3.next = p;
                q = p.next;
                p.next = null;
                p3 = p3.next;
            }
            p = q;
        }
        p1 = dummy1.next;
        p2 = dummy2.next;
        p3 = dummy3.next;
        ListNode dummy = new ListNode(-1);
        ListNode res = dummy;
        while (p1 != null) {
            res.next = p1;
            p1 = p1.next;
            res = res.next;
        }
        while (p2 != null) {
            res.next = p2;
            p2 = p2.next;
            res = res.next;
        }
        while (p3 != null) {
            res.next = p3;
            p3 = p3.next;
            res = res.next;
        }
        return dummy.next;


    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null)return  null;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        boolean duplicate = false;
        ListNode pre = dummy;
        ListNode now = head;
        while (now != null) {
            if (now.next != null && now.val == now.next.val) {
                now.next = now.next.next;
                duplicate = true;
            } else if (duplicate) {
                pre.next = now.next;
                now = pre.next;
                duplicate = false;
             } else {
                pre = pre.next;
                now = now.next;
            }

        }
        return dummy.next;

    }

    public static ListNode rotateRight(ListNode head, int n) {
        if (head == null)return null;

        int len = 0;

        ListNode now = head;
        while (now != null) {
            len++;
            now = now.next;
        }
        n = n % len;
        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < n; i++) {
            if(fast != null)fast = fast.next;
        }
        if (fast == null)return head;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        fast.next = head;
        fast = slow.next;
        slow.next = null;
        return fast;

    }
    public static double findMedianSortedArrays(int A[], int B[]) {

        int [] C = merge(A,B);
        int m = A.length;
        int n = B.length;
        return (C[(m+n+1)/2 - 1] + C[(m+n+2)/2 - 1])/2.0;
    }

    private static int[] merge(int [] A, int [] B) {
        int la = A.length;
        int lb = B.length;
        if (la == 0)return B;
        if (lb == 0)return A;
        int [] C = new int[la + lb];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < la && j < lb) {
            if (A[i] < B[j]) {
                C[k] = A[i];
                i++;
            } else {
                C[k] = B[j];
                j++;
            }
            k++;
        }
        while (i < la) {
            C[k] = A[i];
            k++;
            i++;
        }
        while (j < lb) {
            C[k] = B[j];
            k++;
            j++;
        }
        return C;
    }
    public static ListNode reverseListII(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode now = head;
        while (now.next != null) {
            ListNode t = now.next;
            now.next = t.next;
            t.next = pre.next;
            pre.next = t;
        }
        return dummy.next;
    }
    public static int maxPoints(Point[] points) {
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            HashMap<Double,Integer> hashMap = new HashMap();
            int dup = 0;
            int ver = 1;
            int local = 0;
            int x1 = points[i].x;
            int y1 = points[i].y;
            for (int j = i + 1; j < points.length; j++) {
                int x2 = points[j].x;
                int y2 = points[j].y;
                if (x1 == x2) {
                    if (y1 == y2){
                        dup++;
                    } else {
                        ver++;
                    }
                } else {
                    double slope = (double) (y2 - y1) / (x2 - x1);
                    if (!hashMap.containsKey(slope)) {
                        hashMap.put(slope, 2);
                    } else {
                        hashMap.put(slope, hashMap.get(slope) + 1);

                    }
                    local = Math.max(local, hashMap.get(slope));

                }

            }
            local = ((Math.max((int) local + dup, ver + dup)));
            res = Math.max(res,(int)local);
        }
        return res;
    }
    private static int findMaxU(int [] arr, int bng, int end) {
        int max = Integer.MIN_VALUE;
        for (int i = bng; i < end; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        return max;
    }
    public static int getMaxABSLeftAndRight(int[] arr) {
        int len = arr.length;
        if (len == 2)return Math.abs(arr[1] - arr[0]);
        int left = 0;
        int right = 0;
        int leftMax = arr[0];
        int rightMax = findMaxU(arr,1,len);
        int res = Math.abs(leftMax - rightMax);
        for (int k = 1; k < len - 1; k++) {
            if (arr[k] > leftMax) {
                leftMax = arr[k];
            }

            if (arr[k] == rightMax) {
              rightMax = findMaxU(arr,k + 1, len);
            }
            res = Integer.max(Math.abs(rightMax - leftMax),res);
        }
        return res;


    }
    public void relocateList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)return ;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode left = head;
        ListNode right  = slow;
        while (left != right && right != null) {

        }
    }

    private static void preOrder(TreeNode root,StringBuilder builder) {
        if(root == null) {
            builder.append("#!");
            return;
        }
        builder.append(root.val);
        builder.append("!");
        preOrder(root.left,builder);
        preOrder(root.right,builder);
    }
    public static String Serialize(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        preOrder(root,builder);
        return builder.toString();
    }
    private static int index = -1;
    public static TreeNode Deserialize(String str) {
        index++;
        String[] strr = str.split("!");
        TreeNode node = null;
        if (!strr[index].equals("#")) {
            node = new TreeNode(Integer.valueOf(strr[index]));
            node.left = Deserialize(str);
            node.right = Deserialize(str);
        }
        return node;

    }
    public static void main(String[] args) {
//        HashSet set = new HashSet();
//        set.add(1);
//        set.add('a');
//
////        System.out.println(FirstNotRepeatingChar("google"));
////        int a[] = new int[]{1,2,4,7,11,15};
////        System.out.println(FindNumbersWithSum(a,15));;
////        System.out.println(LastRemaining_Solution(5,3));
//        //       System.out.println(GetLeastNumbers_Solution(new int[]{2,1,4,7,8},2));
//        //       System.out.println(GetLeastNumbers_Solution(new int[]{2,1,4,7,8},2));
////        System.out.println(GetUglyNumber_Solution(10));
//        int[] a = new int[]{1,2,3,4};
//        //System.out.println(GetLeastNumbers_Solution(a,7));
//        //System.out.println(FindGreatestSumOfSubArray(a));
//        char[] matrix = new char[]{'a', 'b', 'c', 'e', 's', 'f', 'c', 's', 'a', 'd', 'e', 'e'};
//        char[] str = new char[]{'a', 'b', 'c', 'c', 'e', 'd'};
//        char[] seq01 = new char[] {'1','1','0','0','1','1','1'};
//        //System.out.println(hasPath(matrix, 3, 4, str));
//        int num1[] = new int[1];
//        int num2[] = new int[1];
//        // FindNumsAppearOnce(a,num1,num2);
//        //System.out.println(ReverseSentence("student. a am I"));
//        System.out.println(FindContinuousSequence(3));
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList.add(2);
//        arrayList.add(1);
//        arrayList.add(3);
//        arrayList.add(-9);
//        arrayList.add(-1);
//        System.out.println(findMax(arrayList));
//        System.out.println(maxAliveDay(3,5,100,10));
//        System.out.println(findLongest01Seq(seq01));
//        char[] numString = new char[]{'1','0','0'};
//        //System.out.println(isNumeric(numString));
//        System.out.println(BigIntAdd("98","7"));
//        int w[] = new int[] {2,2,3};
//        int h[] = new int[] {3,1};
//
//        reverseN(a);
//        System.out.println(moreChildren(3,h,2,w));
//        char [] x1 = new char[] {'1','0','1','0','1','0'};
//        char [] x2 = new char[] {'1','0','1','0'};
//        kcopiedStringCompare(x1,2,x2,3);
        TreeNode ra = new TreeNode(1);
        TreeNode rb = new TreeNode(2);
        TreeNode rc = new TreeNode(3);
        TreeNode rd = new TreeNode(4);
        TreeNode re = new TreeNode(5);
        ra.left = rb;
        ra.right = rc;
        rb.left = rd;
        rd.left = re;
        System.out.println(Serialize(ra));
        String strr = Serialize(ra);
        TreeNode m = Deserialize(strr);
        System.out.println(Serialize(m));
//        String z = "#";
//        String [] zs = new String[]{"#"};
//        System.out.println(z.equals(zs[0]));
//        System.out.println(Serialize(m));
//        System.out.println(findN(18));
//        Bear bear1 = new Bear(34,4);
//        Bear bear2 = new Bear(35,2);
//        Food food1 = new Food(5);
//        Food food2 = new Food(6);
//        Food food3 = new Food(10);
//        Food food4 = new Food(20);
//        Food food5 = new Food(30);
//        Bear [] bears = new Bear[]{bear1,bear2};
//        Food [] foods = new Food[]{food1,food2,food3,food4,food5};
//        bearEatSoultion(bears,foods);

        int[] arr = {2, 1, 4, 6, 8, 9, 10};
        int[] temp = new int[arr.length];
        quickSort(arr);
        int map[][] = {{1, 0, 0}, {0, 0, 1}, {0, 0, 0}};
        System.out.println(arr[kthElement(arr, 0)]);
        System.out.println(numOfInsland(map));
        System.out.println('A' - 'a');
        System.out.println(huawei2("AABbBb"));
        mergeSort(arr, 0, arr.length - 1, temp);
        ListNode head = new ListNode(0);
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        head.next = a;
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = null;
        System.out.println(partition("aab"));
        System.out.println(min);

        //insertionSortList(head);
        //InsertionSort(arr);
        //reorderList(head);
        Set<String> s = new HashSet<>();
        s.add("leet");
        s.add("code");
        s.add("t");
        System.out.println(wordBreak("leettcode", s));
        //sortList(head);
        Point[] points = {
                new Point(2,3),
                new Point(3,3),
                new Point(5, 3),
                // new Point(2,2),
                //       new Point(4,4)
        };
        System.out.println(maxPoints(points));
        ArrayList<Integer> list = new ArrayList<>();
        Collections.reverse(list);
        System.out.println(arr);

        //System.out.println(Integer.MAX_VALUE);
        BigIntMultiple("0","0");

        //System.out.println(maxInWindows(a,3));
        System.out.println(restoreIpAddresses("010010"));

        System.out.println(permute(new int[]{1,0, 2,3}));
        RandomListNode node1 = new RandomListNode(1);
        node1.next = null;
        node1.random = null;
        //System.out.println(Clone(node1));
        //System.out.println(partition(head,3));
        //System.out.println(rotateRight(head,0));
        int [] A = new int[0];
        int [] B = new int[] {1};
        System.out.println(findMedianSortedArrays(new int[]{1,1},new int[]{1,2}));
        reverseListII(head);
        System.out.println(getMaxABSLeftAndRight(new int[]{4,6,0,7,3,3,5,0,9}));


    }
}