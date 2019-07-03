import java.util.*;

public class LeetCode {
    //DP
    public static int minCut(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = s.length();
        int[] cut = new int[len];
        boolean[][] pal = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 1; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i) && (j + 1 > i - 1 || pal[j + 1][i - 1])) {
                    pal[j][i] = true;
                    min = (j == 0 ? 0 : Math.min(min, cut[j - 1] + 1));
                }
            }
            cut[i] = min;
        }

        return cut[len - 1];
    }

    public static int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {

        ArrayList<ArrayList<Integer>> dp = new ArrayList<>(triangle);
        int row = triangle.size();
        for (int i = row - 2; i >= 0 ; i--) {

            for (int j = 0; j < triangle.get(i).size() ; j++) {

                int min = Integer.min(dp.get(i+1).get(j),dp.get(i+1).get(j+1)) + triangle.get(i).get(j);
                dp.get(i).set(j,min);

            }



        }
        return dp.get(0).get(0);
    }

    public static int numDistinct(String S, String T) {
        int l1 = T.length();
        int l2 = S.length();
        int [][] dp = new int[l1+1][l2+1];
        for (int i = 0; i <= l1; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= l2; i++) {
            dp[0][i] = 1;
        }
        dp[0][0] = 1;
        for (int i = 1; i <= l1 ; i++) {
            for (int j = 1; j <= l2 ; j++) {
                if (T.charAt(i - 1) == S.charAt(j - 1)) {
                    dp[i][j] = dp[i][j-1]+dp[i-1][j-1];
                } else {
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[l1][l2];


    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length();
        int l2 = s2.length();
        int l3 = s3.length();
        if ((l1 + l2) != l3)return  false;
        boolean [][] dp = new boolean[l1+1][l2+1];
        dp[0][0] = true;
        for (int i = 1; i <= l1; i++ ) {
            dp[i][0] = dp[i - 1][0] && (s1.charAt(i - 1) == s3.charAt(i - 1));
        }
        for (int j = 1; j <= l2; j++) {
            dp[0][j] = dp[0][j - 1] && (s2.charAt(j - 1) == s3.charAt(j - 1));
        }

        for (int i = 1; i <= l1 ; i++) {

            for ( int j = 1; j<= l2; j++) {
                int k = i + j ;
                if (s3.charAt(k - 1) != s1.charAt(i - 1) && s3.charAt(k - 1) != s2.charAt(j - 1)) {
                    dp[i][j] = false;

                } else if (s3.charAt(k - 1) == s1.charAt(i - 1) && s3.charAt(k - 1) == s2.charAt(j - 1)){
                    dp[i][j] = dp[i-1][j] || dp[i][j-1];
                } else if(s3.charAt(k - 1) == s1.charAt(i - 1)&&s3.charAt(k - 1) != s2.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = dp[i][j-1];
                }

            }
        }
        return dp[l1][l2];
    }

    public static void subsetsHelper(int [] S, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> arr, int start) {
        int end = S.length;
        if (start >= end )return;
        for (int i = start; i < end; i++) {
            arr.add(S[i]);
            res.add(new ArrayList<>(arr));
            subsetsHelper(S,res,arr,i+1);
            arr.remove(arr.size()-1);
        }

    }
    public static int numDecodings(String s) {

        int len = s.length();
        if (len <= 1) return 1;
        int [] dp = new int[len+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2;i <= len; i++) {
            if (((s.charAt(i-2) - '0')*10 + s.charAt(i-1) - '0' )<= 26) {
                dp[i] = dp[i-2]+dp[i-1];
            } else {
                dp[i] = dp[i-1];
            }
        }
        return dp[len];
    }
    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();
        Arrays.sort(S);
        res.add(a);
        subsetsHelper(S,res,a,0);
        return res;
    }
    public static int min(int a, int b, int c) {
        int t = 0;
        if ( a > b) {
            t = b;
        } else {
            t = a;

        }
        if (t > c) {
            t = c;

        }
        return t;
    }

    public static int minDistance(String word1, String word2) {
        int l1 = word1.length();
        int l2 = word2.length();
        int [][] dp = new int[l1+1][l2+1];
        for (int i = 0; i <= l1; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i <= l2 ; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2 ; j++) {
                int a = dp[i-1][j]+1;
                int b = dp[i][j-1]+1;
                int c = word1.charAt(i-1) == word2.charAt(j-1) ? dp[i-1][j-1] : 1+dp[i-1][j-1];
                dp[i][j] = min(a,b,c);
            }
        }
        return dp[l1][l2];

    }
    public static int climbStairs(int n) {
        int [] dp = new int[n+1];
        dp[0] = 0;
        if (n == 0) return 0;
        dp[1] = 1;
        if (n == 1) return 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
    public static int res = 0;
    public static void backTrack(int m, int n, boolean [][] visited, int posR, int posC) {
        int [][] next = {
                {0,1},
                {1,0}
        };

        if (posR == m - 1&& posC == n - 1) {
            res = res + 1;
            return;
        }
        for (int i = 0; i < 2; i++) {
            int nextR = posR + next[i][0];
            int nextC = posC + next[i][1];
            if (nextR >= m || nextC >= n || visited[nextR][nextC]) continue;
            visited[posR][posC] = true;

            backTrack(m,n,visited,nextR,nextC);


            visited[posR][posC] = false;
        }


    }
    public static int uniquePaths(int m, int n) {
        boolean [][] visited = new boolean[m][n];
        backTrack(m,n,visited,0,0);
        return res;
    }
    public static String simplifyPath(String path) {

        String [] paths = path.split("/");
        Stack<String> s = new Stack<>();
        int len = paths.length;
        for (int i = 1; i < len; i++) {
            if (paths[i].equals(".") || paths[i].equals("")) {
                ;;
            } else if (paths[i].equals("..") ) {
                if (!s.isEmpty()) {

                    s.pop();
                }
            } else {
                s.push(paths[i]);
            }

        }
        StringBuilder builder = new StringBuilder();
        if (s.isEmpty()) {
            builder.append("/");
            ;

        }
        Stack<String> t = new Stack<>();
        while (!s.isEmpty()) {
            t.push(s.pop());
        }
        while (!t.isEmpty()) {
            builder.append("/");
            builder.append((t.isEmpty() ? "" : t.pop()));
        }

        return builder.toString();
    }
    public static String addBinary(String a, String b) {
        int carry = 0;

        int la = a.length();
        int lb = b.length();
        int l = Integer.max(la,lb);
        int [] res = new int[l + 1];
        int n = 0;
        for (int i = la - 1,j = lb - 1; i >= 0 && j >= 0; i--,j--) {
            int t = a.charAt(i) - '0' + b.charAt(j) - '0' + carry;
            res[n] = t % 2 + '0';
            carry = t / 2;
            n++;
        }
        if ( la > lb ) {
            for (int i = la - lb - 1; i >= 0 ; i--) {
                int t = a.charAt(i) - '0' + carry;
                res[n] = t %2 + '0';
                carry = t / 2;
                n++;
            }
        } else {
            for (int i = lb - la - 1; i >= 0; i--) {
                int t = b.charAt(i) - '0' + carry;
                res[n] =  t % 2 +'0';
                carry = t / 2;
                n++;
            }

        }
        if (carry == 1) {
            res[l] = 1 + '0';
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (res[l + 1 - i - 1] == 0) i++;
        while (i < l + 1) {
            builder.append((char)res[l + 1 - i - 1]);
            i++;
        }
        return builder.toString();

    }
    public ArrayList<String> fullJustify(String[] words, int L) {
        ArrayList<String> res = new ArrayList<>();
        if (words == null || words.length == 0)return res;
        int count = 0;
        int last = 0;
        for (int i = 0; i < words.length; i++){
            if (count + words[i].length() + (i - last) > L) {
                int spaceNum = 0;
                int extraNum = 0;
                if (i - last - 1 > 0) {
                    spaceNum = (L - count)/(i - last - 1);
                    extraNum = (L - count) % (i - last - 1);
                }
                StringBuilder str = new StringBuilder();
                for (int j = last; j < i; j++) {
                    str.append(words[j]);
                    if (j <i - 1) {
                        for (int k = 0; k < spaceNum; k++)
                            str.append(" ");
                        if (extraNum > 0)
                            str.append(" ");
                        extraNum--;
                    }

                }
                for (int j = str.length();j<L;j++) {
                    str.append(" ");
                }
                res.add(str.toString());
                count = 0;
                last = i;
            }
            count += words[i].length();
        }
        StringBuilder str = new StringBuilder();
        for (int i = last; i < words.length;i++) {
            str.append(words[i]);
            if (str.length() < L) {
                str.append(" ");
            }
        }
        for (int i = str.length(); i< L; i++) {
            str.append(" ");
        }
        res.add(str.toString());
        return res;
    }
    public static int lengthOfLastWord(String s) {
        int len = s.length();
        if(len == 0)return 0;
        int i = len - 1;
        int res = 0;

        while(i >= 0 && s.charAt(i) == ' ')i--;
        while(i >= 0 && s.charAt(i) != ' ' ){
            i--;
            res++;
        }

        return res;
    }

    public static ArrayList<String> anagrams(String[] strs) {
        ArrayList<String> res = new ArrayList<>();
        HashMap<String,ArrayList<String>> m = new HashMap<>();
        int len = strs.length;
        for (int i = 0; i < len; i++) {
            char [] array = strs[i].toCharArray();
            Arrays.sort(array);
            String sortedStr = new String(array);
            if (!m.containsKey(sortedStr)) {

                m.put(sortedStr,new ArrayList<>());
                m.get(sortedStr).add(strs[i]);
            } else {
                m.get(sortedStr).add(strs[i]);
            }
        }
        Iterator iter = m.values().iterator();
        while (iter.hasNext()) {
            ArrayList<String> item = (ArrayList<String>)iter.next();
            if(item.size()>1)
                res.addAll(item);
        }
        return res;
    }
    public static String countAndSay(int n) {
        if (n == 1)return "1";
        String current = "11 ";
        for (int i = 1; i < n - 1; i++) {
            StringBuilder next = new StringBuilder();
            int len = current.length();
            for (int j = 0; j < len - 1; j++) {
                int cnt = 1;
                char c = current.charAt(j);
                while ((j < len - 1) && current.charAt(j+1) == current.charAt(j) ){
                    cnt++;
                    j++;
                }
                next.append(cnt);
                next.append(c);
            }
            next.append(" ");
            current = next.toString();
        }
        return current.substring(0,current.length() - 1);
    }

    public static long divideHelper(long dividend, long divisor) {
        if (dividend < divisor)return 0;
        long sum = divisor;
        long a = dividend;
        long b = divisor;
        long res = 0;
        while (a >= b) {

            long divide = 1;
            while((b + b) <= a) {
                b += b;
                divide += divide;

            }
            a -= b;
            b = divisor;
            res += divide;

        }
        return res;
    }
    public static int divide(int dividend, int divisor) {
        boolean negative = dividend < 0 != divisor < 0;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        long result = negative ? -1 * divideHelper(dividend, divisor) : divideHelper(dividend,divisor);
        return result > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) result;
    }
    public static String strStr(String haystack, String needle) {
        int l1 = haystack.length();
        int l2 = needle.length();
        if (l2 == 0)return "";
        if (l1 < l2)return null;
        for(int i = 0; i <= l1 - l2; i++) {
            int j = 0;
            for (j = 0; j < l2; j++) {
                if (haystack.charAt(i+j)!=needle.charAt(j)){
                    break;
                }

            }
            if (j == l2)return haystack.substring(i);
        }
        return null;
    }

    public ArrayList<Integer> findSubstring(String S, String[] L) {
        return null;
    }
    public static int lengthOfLongestSubstring(String s) {
        if(s.isEmpty())return 0;

        int len = s.length();
        HashSet set = new HashSet();
        int left = 0;
        int right = 0;
        int res = 0;
        int cnt = 0;
        while(right < len) {
            while(set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(s.charAt(right));
            right++;
            cnt = right - left;
            res = Integer.max(res,cnt);
        }
        return res;
    }
    public  static void subsetsIIHelper(int [] S, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> arr, int start) {
        int end = S.length;
        if (start >= end )return;
        for (int i = start; i < end; i++) {
            if (i > start && S[i] == S[i - 1])
                continue;
            arr.add(S[i]);
            res.add(new ArrayList<>(arr));
            subsetsIIHelper(S,res,arr,i+1);
            arr.remove(arr.size()-1);

        }

    }
    public static ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();
        Arrays.sort(num);
        res.add(a);
        subsetsIIHelper(num,res,a,0);
        return res;
    }
    public static int sqrt(int x) {
        if (x == 0)return 0;

        int left = 1;
        int right = x/2 + 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            int t = x / middle;
            if ( t == middle) {
                return middle ;
            }

            else if (t > middle) {
                left = middle + 1;
            }
            else {
                right = middle - 1;
            }

        }
        return right;
    }
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)return "";
        if (strs.length == 1) return strs[0];
        StringBuilder builder = new StringBuilder();
        int len = strs.length;
        int i ;
        for (i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < len; j++) {
                System.out.println(strs[j].charAt(i) != c);
                if ( i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return builder.toString();
                }

            }
            builder.append(strs[0].charAt(i));
        }

        return builder.toString();
    }
    public static int reverse(int x) {
        if (x == 0)return 0;
        ArrayList<Integer> a = new ArrayList<>();

        while (x != 0) {
            a.add(x % 10);
            x = x / 10;
        }
        long longresult = 0;
        for (int i = 0; i < a.size(); i++) {
            long t =(long) (a.get(i) * Math.pow(10,a.size() - i - 1));
            longresult += t;


        }
        int result = 0;
        if (longresult > Integer.MAX_VALUE) {
            result = Integer.MAX_VALUE;
        }
        else if (longresult < Integer.MIN_VALUE) {
            result = Integer.MIN_VALUE;
        } else result = (int)longresult;

        return result;
    }

    public static int atoi(String str) {
        int len = str.length();
        if(str == null || len == 0) return 0;
        int i = 0;
        while(str.charAt(i) == ' ')i++;
        int sign = 0;
        if(str.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if(str.charAt(i) == '+') {
            sign = 1;
            i++;
        } else sign = 1;

        StringBuilder builder = new StringBuilder();
        while(i < len) {
            if (  '9' < str.charAt(i) || '0' > str.charAt(i)) break;
            builder.append(str.charAt(i));
            i++;
        }
        long result = 0;
        int size = builder.length();
        for (int j = 0; j < size; j++) {
            result += (builder.charAt(j) - '0')*Math.pow(10,size - 1 - j);
        }

        result = result * sign;
        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;

        } else if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else {
            return (int)result;
        }
    }

    public static String longestPalindrome(String s) {
        int len = s.length();
        int maxLen = Integer.MIN_VALUE;
        boolean [][] dp = new boolean[len][len];
        int left = 0;
        int right = 0;
        for (int j = 0; j < len; j++) {

            for (int i = 0; i <= j; i++) {
                if (i == j) {
                    dp[i][j] = true;
                } else if(j == (i + 1)) {
                    dp[i][j] = s.charAt(j) == s.charAt(i);
                } else {
                    dp[i][j] = dp[i+1][j-1] && (s.charAt(i) == s.charAt(j));
                }
                if (dp[i][j]) {

                    if (j - i + 1 > maxLen) {
                        maxLen = j - i + 1;
                        left = i;
                        right = j;
                    }
                }


            }

        }
        String res = s.substring(left,right+1);
        return res;
    }
    private static String delSpace(String s) {
        int i = 0;
        int len = s.length();
        StringBuilder builder = new StringBuilder();
        while (i < len && s.charAt(i) == ' ')i++;
        int j = len - 1;
        while (j > i && s.charAt(j) == ' ')j--;
        return s.substring(i,j+1);


    }

    public static boolean isNumber(String s) {
        s = delSpace(s);
        if (s.isEmpty())return false;
        int i = 0;
        int len = s.length();
        boolean exp = false;
        boolean digital = false;
        boolean dot = false;
        while (i < len - 1) {
            if (i == 0) {
                if (s.charAt(i) > '9' || s.charAt(i) < '0') {
                    if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '.') {
                        if (len == 1) return false;
                        if (s.charAt(i) == '.') {
                            dot = true;
                        }
                    } else return false;
                } else {
                    digital = true;
                }
                i++;
                continue;

            }


            if (i > 0) {
                switch (s.charAt(i)) {
                    case 'e' :
                        if (!exp && s.charAt(i - 1) != '+' && s.charAt(i -1 ) != '-' && digital && i != s.length() -1){
                            exp = true;
                        } else {
                            return false;
                        }
                        break;
                    case 'E' :
                        if (!exp && s.charAt(i - 1) != '+' && s.charAt(i -1 ) != '-' && digital && i != s.length() -1){
                            exp = true;
                        } else {
                            return false;
                        }
                        break;
                    case '.':
                        if (!dot && !exp) {
                            dot = true;

                        } else {
                            return false;
                        }
                        break;
                    case '+':
                        if (s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E'){}else{
                            return false;
                        }
                        break;
                    case '-':
                        if (s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E'){}else{
                            return false;
                        }
                        break;
                        default:
                            if (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                                digital = true;
                            } else {
                                return  false;
                            }
                            break;
                }
                i++;
                continue;
            }


        }
        if (s.charAt(s.length() - 1) >= '0' && s.charAt(s.length() - 1) <= '9') {
            return true;
        }
        if (s.charAt(s.length() - 1) == '.' && !dot && !exp && digital && s.charAt(s.length() - 2) >= '0' && s.charAt(s.length() - 2) <= '9' ){
            return true;
        }
        return false;
    }
    public static boolean isMatch(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        int ls = s.length();
        int lp = p.length();
        if (lp == 1) {
            return ls == 1 && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        }
        if (p.charAt(1) != '*') {
            if (s.isEmpty())
                return false;
            return (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.') && isMatch(s.substring(1),p.substring(1));
        }
        while (!s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.')) {
            if (isMatch(s,p.substring(2)))return true;
            s = s.substring(1);
        }
        return isMatch(s,p.substring(2));

    }
    public static int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) return 0;
        int [] leftProfit = new int [len];
        int [] rightProfit = new int [len];
        int leftMin = prices[0];
        int rightMin = prices[len - 1];
        for (int i = 1; i < len; i++) {
            leftProfit[i] = Math.max(leftProfit[i - 1],prices[i] - leftMin);
            leftMin = Math.min(leftMin,prices[i]);
        }
        for (int i = len - 2; i >= 0; i--) {
            rightProfit[i] = Math.max(rightProfit[i + 1],rightMin - prices[i]);
            rightMin = Math.max(rightMin, prices[i]);
        }
        int res = 0;
        for (int i = 0; i < len; i++) {
            if (leftProfit[i] + rightProfit[i] > res) {
                res = leftProfit[i] + rightProfit[i];
            }
        }
        return res;
    }
    public static int romanToInt(String s) {
        if (s == null || s.length() == 0)return 0;
        HashMap<Character,Integer> m = new HashMap<>();
        m.put('I',1);
        m.put('V',5);
        m.put('X',10);
        m.put('L',50);
        m.put('C',100);
        m.put('D',500);
        m.put('M',1000);
        int preValue = 0;
        int curValue = 0;
        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            curValue = m.get(s.charAt(i));
            if (curValue < preValue) {
                res -= curValue;
            } else {
                res += curValue;
            }
            preValue = curValue;

        }
        return res;
    }
    public static void intToRomanHelper(StringBuilder builder, int t, int p) {
        HashMap<Integer,Character> hashMap = new HashMap<>();
        hashMap.put(1,'I');
        hashMap.put(5,'V');
        hashMap.put(10,'X');
        hashMap.put(50,'L');
        hashMap.put(100,'C');
        hashMap.put(500,'D');
        hashMap.put(1000,'M');
        if (1 <= t && t < 4) {
            while (t-- != 0) builder.append(hashMap.get(p));
        } else if (t == 4) {
            builder.append(hashMap.get(p));
            builder.append(hashMap.get(p * 5));
        }
        else if (t == 5) {
            builder.append(hashMap.get(p*5));
        }
        else if (5 < t && t < 9) {
            builder.append(hashMap.get(p * 5));
            t -= 5;
            while (t-- != 0)builder.append(hashMap.get(p));
        }
        else {
            builder.append(hashMap.get(p));
            builder.append(hashMap.get(p * 10));
        }


    }
    public static String intToRoman(int num) {

        StringBuilder res = new StringBuilder();
        int i = 0;
        int j = 0;
        int k = 0;
        i = num / 1000;
        if (i != 0) {
            num = num % 1000;
            intToRomanHelper(res,i,1000);
        }
        j = num / 100;
        if (j != 0) {
            num = num % 100;
            intToRomanHelper(res,j,100);
        }
        k = num / 10;
        if (k != 0) {
            num = num % 10;
            intToRomanHelper(res,k,10);
        }
        if (num != 0) {

            intToRomanHelper(res,num,1);
        }
        return res.toString();

    }
    class Interval {
        public int start;
        public int end;
        public Interval() {start = 0; end = 0;}
        public Interval(int start, int end){
            start = start;
            end = end;
        }
    }

    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        int size = intervals.size();
        int i;
        for (i = 0; i < size; i++) {
            if(intervals.get(i).start > newInterval.start)break;
        }
        intervals.add(i , newInterval );
        size = intervals.size();
        for (int j = 1; j < size; j++ ) {
            int preStart = intervals.get(j - 1).start;
            int preEnd = intervals.get(j - 1).end;
            int curStart = intervals.get(j).start;
            int curEnd = intervals.get(j).end;
            if (curStart <= preEnd) {
                intervals.set(j,new Interval(preStart, Math.max(preEnd,curEnd)));
                intervals.set(j -1 ,null);
            }
        }
        while (intervals.remove(null));
        return intervals;
    }
    public static boolean isPalindrome(int x) {
        int m = x;
        if (x < 0)return false;
        long t = 0;
        while (x != 0) {
            t = x % 10 + t * 10;
            if (t > Integer.MAX_VALUE)return false;
            x = x / 10;
        }
        return (int)t == m;
    }

    public static void dfs(char [][] board, boolean [][] transfer, int i, int j) {
        int [][] next = {
                {1 , 0},
                {-1, 0},
                {0, 1},
                {0 , -1}
        };
        int tx ;
        int ty;
        for (int ii = 0; ii < 4; ii++) {
            tx = i + next[ii][0];
            ty = j + next[ii][1];
            if (tx >= 0 && tx < board.length && ty >= 0 && ty < board[0].length) {
                if (board[tx][ty] == 'O' && !transfer[tx][ty]) {
                    transfer[tx][ty] = true;
                    dfs(board, transfer, tx, ty);
                }
            }
        }

    }

    public void solve(char[][] board) {
        if (board == null)return;
        int row = board.length;
        int col = board[0].length;
        boolean [][] transfer = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                    if (board[i][j] == 'O') {
                        transfer[i][j] = true;
                        dfs(board,transfer,i,j);
                    }

                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O' && !transfer[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }
    public int longestConsecutive(int[] num) {

        int res = 0;
        Set set = new HashSet();
        for (int i = 0; i < num.length;i++) {
            set.add(num[i]);
        }
        int pre = 0;
        int next = 0;

        for (int i = 0; i < num.length; i++) {
            if (set.remove(num[i])){
                pre = num[i] - 1;
                next = num[i] + 1;
                while (set.remove(pre))pre--;
                while (set.remove(next))next++;
                res = Integer.max(res,next - pre + 1);
            }
        }
        return res;


    }

    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });
        ArrayList<Interval> res = new ArrayList<>();
        int len = intervals.size();
        Interval pre = intervals.get(0);
        for (int i = 1; i < len; i++) {
            int perStart = intervals.get(i - 1).start;
            int perEnd = intervals.get(i - 1).end;
            int curStart = intervals.get(i).start;
            int curEnd = intervals.get(i).end;
            if (curStart <= perEnd) {
                intervals.set(i,new Interval(perStart, Math.max(perEnd,curEnd)));
                intervals.set(i - 1, null);

            }

        }
        while (intervals.remove(null));

        return intervals;

    }
    public static int[][] generateMatrix(int n) {
        int [][] array = new int[n][n];
        int next [][] = {
                {0,1},
                {1,0},
                {0,-1},
                {-1,0}
        };
        int direction = 0;
        int x = 0;
        int y = 0;

        for (int i = 1; i <= n * n; i++) {

            array[x][y] = i;
            int tx = x + next[direction][0];
            int ty = y + next[direction][1];
            if(tx < 0 || tx > n - 1 || ty < 0 || ty > n-1 || array[tx][ty] != 0) {
                direction = (direction + 1) % 4;
                x = x + next[direction][0];
                y = y + next[direction][1];
            } else {
                x = tx;
                y = ty;
            }
        }
        return array;
    }


    public static int firstMissingPositive(int[] A) {
        if (A == null)return -1;
        if (A.length == 0)return 1;

        int [] B = new int[A.length + 1];
        for (int aA : A) {
            if (aA > 0 && aA <= A.length) {
                B[aA] = aA;
            }
        }
        for (int i = 1; i <= A.length; i++ ) {
            if (B[i] != i) {
                return i;
            }
        }
        return A.length + 1;
    }

    class UndirectedGraphNode {
      int label;
      ArrayList<UndirectedGraphNode> neighbors;
      UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
  };
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null)return null;
        HashMap<UndirectedGraphNode, UndirectedGraphNode>hashMap = new HashMap<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
        queue.offer(node);
        hashMap.put(node,clone);
        while (!queue.isEmpty()) {
            UndirectedGraphNode t = queue.poll();
            List<UndirectedGraphNode> list = t.neighbors;
            for (UndirectedGraphNode tt : list) {
                if (!hashMap.containsKey(tt)) {

                    hashMap.put(tt,new UndirectedGraphNode(tt.label));
                    queue.offer(tt);
                }
                hashMap.get(t).neighbors.add(hashMap.get(tt));

            }

        }
        return clone;
    }
    public static void Helper(int [] a, int step, int t ,int k) {
        if(step == k) {
            return;
        }
        if(t == a.length) {
            System.out.println(Arrays.toString(a));
            return;
        }
        for(int i = t; i < a.length; i++) {
            swap(a,i,t);
            Helper(a,step+1,t+1, k);
            swap(a,i,t);
        }
        return;

    }
    public static void swap(int [] a, int i , int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;

    }
    public static String getPermutation(int n, int k) {
        if(k == 0)return "";
        int [] a = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        Helper(a,1,0, k);
        return Arrays.toString(a);

    }
    public static int threeSumClosest(int[] num, int target) {
        Arrays.sort(num);
        int res = 0;
        int t = Integer.MAX_VALUE;
        for (int i = 0; i < num.length - 2; i++) {
            int a = twoSumClosest(num,target - num[i], i + 1);
            int b = a + num[i];
            if (Math.abs(b - target) < t) {
                t = Math.abs(b - target);
                res = b;
            }
        }
        return res;
    }

    private static int twoSumClosest(int[] num, int target, int start) {
        int res = 0;
        int i = start;
        int j = num.length - 1;
        int t = Integer.MAX_VALUE;
        while (i < j) {
            int s = num[i] + num[j];
            if (s > target) {
                j--;
                if (s - target < t) {
                    res = s;
                    t = s - target;
                }
            } else {
                i++;
                if (target - s < t) {
                    res = s;
                    t = target - s;
                }

            }
        }
        return res;
    }
    public ArrayList<Integer> grayCode(int n) {
        ArrayList<Integer> res = new ArrayList();
        int len = (int) Math.pow(2,n);
        for (int i = 0; i < len; i++) {
            int code = ((i >> 1) ^ i) ;
            res.add(code);
        }
        return res;
    }

    public static ArrayList<ArrayList<Integer>> generate(int numRows) {


        ArrayList<ArrayList<Integer>> res = new ArrayList();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        res.add(a);
        if (numRows == 1)return res;
        ArrayList<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(1);
        res.add(b);
        if (numRows == 2)return res;
        for (int i = 2 ; i < numRows; i++) {
            ArrayList<Integer> t = new ArrayList();
            for (int j = 0; j <= i; j++) {
                if (j == 0 ){
                    t.add(1);

                } else if (j == i) {
                    t.add(1);
                } else {

                    int t1 = res.get(i - 1).get(j);
                    int t2 = res.get(i - 1).get(j - 1);
                    t.add(t1 + t2);
                }

            }
            res.add(t);
        }
        return res;
    }
    public static ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (num == null || num.length < 2)return res;
        Arrays.sort(num);
        int len = num.length;
        for (int i = 0; i < len - 2; i++) {
            if ( i != 0 && num[i] == num[i - 1])continue;
            int t = -num[i];
            int left = i + 1;
            int right = len - 1;
            while (left >= 0 && right < len && left < right) {
                if (num[left] + num[right] < t) {
                    left++;

                } else if (num[left] + num[right] == t) {
                    ArrayList<Integer> a = new ArrayList<>();
                    a.add(num[i]);
                    a.add(num[left]);
                    a.add(num[right]);
                    res.add(a);
                    left++;
                    right--;
                    while(left < right && num[left]==num[left-1])left++;
                    while(left < right && num[right]==num[right+1])right--;

                } else right--;
            }

        }
        return res;
    }
    private static boolean eq(char optr1, char optr2) {
        if (optr1 == '(' && optr2 == ')' || (optr1 == '[' && optr2 == ']') || optr1 == '{' && optr2 == '}') {
            return true;
        }
        return false;

    }
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        int len = s.length();
        if (len == 0)return true;
        stack.push(s.charAt(0));
        int i = 1;


        while (i < len) {
            if (!stack.isEmpty() && eq(stack.peek(),s.charAt(i))) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
            i++;
        }
        return stack.isEmpty();
    }

    private boolean isOP(String c) {
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/");
    }
    private int cacl(int d1, String c, int d2 ) {
        int result;
        switch (c){
            case "+":
                result = d2 + d1;
                break;
            case "-" :
                result = d2 - d1;
                break;
            case "*" :
                result = d2 * d1;
                break;
            case "/" :
                result = d2 / d1;
                break;
            default:
                result = 0;
                break;
        }

        return result;
    }
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        int len = tokens.length;
        for (int i = 0; i < len; i++ ) {
            int t = 0;
            if (isOP(tokens[i])) {
                int d1 = stack.pop();
                int d2 = stack.pop();
                t = cacl(d1,tokens[i],d2);
            } else {
              t = Integer.valueOf(tokens[i]);

            }
            stack.push(t);
        }
        return stack.pop();

    }
    static class TreeNode {
        public int val ;
        public TreeNode left = null;
        public TreeNode right = null;
        public TreeNode(int val) {
            this.val = val;

        }
    }
    private void pathSumHelper(TreeNode root, int sum, ArrayList<ArrayList<Integer>>res, ArrayList<Integer>a) {
        if (root == null) return;
        int t = sum - root.val;
        a.add(root.val);
        if (root.left == null && root.right == null && t == 0) {

            res.add(new ArrayList<>(a));
            a.remove(a.size() - 1);
        }
        if (root.left == null && root.right == null && t != 0)a.remove(a.size() - 1);
        pathSumHelper(root.left,t,res,a);
        pathSumHelper(root.right,t,res,a);
        a.remove(a.size() - 1);

    }
    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer>a = new ArrayList<>();
        pathSumHelper(root,sum,res,a);
        return res;
    }
    public static int trap(int[] A) {
        Stack<Integer> stack = new Stack<>();
        int len = A.length;
        if(len == 0)return 0;
        int res = 0;
        for(int i = 0; i < len;) {
            if (stack.isEmpty() || A[i] <= A[stack.peek()]){
                stack.push(i++);

            } else {
                int t = stack.pop();
                if (stack.isEmpty())continue;
                int area = (Math.min(A[i],A[stack.peek()])-A[t])*(i - stack.peek() - 1);
                res += area;
            }
        }
        return res;
    }

    public static int longestValidParentheses(String s) {
        int len = s.length();
        int res = 0;
        int [] dp = new int[len+1];
        dp[0] = 0;
        for (int i = 1; i < len + 1; i++) {
            int j = i - 1 - dp[i - 1] - 1;
            if (s.charAt(i - 1) == '(' || j < 0 || s.charAt(j) == ')' )
                dp[i] = 0;
            else {
                dp[i] = dp[i - 1] + dp[j] + 2;

            }

        }

        for (int i = 1; i < len + 1; i++) {
            res = Math.max(res,dp[i]);
        }
        return res;

    }

    private static boolean isValid(char[][] strs, int row , int col) {
        int len = strs.length;
        for (int i = 0; i < len; i++ ) {
            for (int j = 0; j < len; j++) {
                if (Math.abs((row - i)) == Math.abs((col - j))) {
                    if (strs[i][j] == 'Q')
                        return false;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            if (strs[i][col] == 'Q')
                return false;
        }
        for (int j = 0; j < len; j++) {
            if (strs[row][j] == 'Q')
                return false;
        }
        return true;
    }
    private static void backTrack(ArrayList<String []> res, char [][] strs, int row, int n) {
        if (row == n){
            String [] r = new String[n];
            for (int i = 0; i < n; i++) {
                String t = new String(strs[i]);
                r[i] = t;
            }
            res.add(r);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (isValid(strs,row,i)) {
                strs[row][i]=('Q');
                backTrack(res,strs,row+1,n);
                strs[row][i]=('.');
            }
        }

    }

    public static ArrayList<String[]> solveNQueens(int n) {
        ArrayList<String []> res = new ArrayList<>();
        if (n < 1)return res;
        char [][] strs = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                strs[i][j] = '.';
            }
        }
        backTrack(res,strs,0,n);
        return res;
    }
    private void backTrack(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> r, int [] candidates, int target) {
        if (target == 0){
            res.add(new ArrayList<Integer>(r));
            return;
        }

        for (int i = 0; i < candidates.length; i++) {
            if (target - candidates[i] < 0)continue;
            r.add(candidates[i]);
            backTrack(res,r,candidates,target - candidates[i]);
            r.remove(r.size() - 1);
        }


    }
    public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (candidates.length == 0)return res;
        if (target < 0)return res;
        ArrayList<Integer> r = new ArrayList<>();
        Arrays.sort(candidates);
        backTrack(res,r,candidates,target);
        return res;
    }

    public static int ladderLength(String start, String end, HashSet<String> dict) {
        if (dict.isEmpty())return -1;
        if (start.equals(end))return 0;
        HashMap<String,Integer> map = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        map.put(start,1);
        while (!queue.isEmpty()) {
            String word = queue.poll();
            assert word != null;
            for (int i = 0; i < word.length(); i++) {
                char [] newword = word.toCharArray();
                for (char c = 'a'; c < 'z';c++) {
                    newword[i] = c;
                    String t = String.valueOf(newword);
                    if (dict.contains(t) && t.equals(end))
                        return map.get(word) + 1;
                    if (dict.contains(t) && !map.containsKey(t)){
                        queue.offer(t);
                        map.put(t,map.get(word) + 1);

                    }
                }
            }
        }
        return -1;

    }

    public ArrayList<ArrayList<String>> findLadders(String start, String end, HashSet<String> dict) {

        ArrayList<ArrayList<String>> res = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();
        t.add(start);
        Queue<ArrayList<String>> paths = new LinkedList<>();
        paths.offer(t);
        int level = 1;
        int minLevel = Integer.MAX_VALUE;
        HashSet<String> words = new HashSet<>();
        while (!paths.isEmpty()) {
            ArrayList<String> a = paths.poll();
            if (a.size() > level) {

            }
        }
        return null;

    }
    public static int search(int[] A, int target) {
       int start = 0;
       int end = A.length -1 ;
       while (start <= end) {
           int mid = (start + end)/2;
           if (A[mid] == target)return mid;
           if (A[start] <= A[mid]) {
               if (target >= A[start] && target < A[mid]) {
                   end = mid - 1;

               } else
                   start = mid + 1;
           } else  {
               if (target > A[mid] && target <= A[end])
                   start = mid + 1;
               else
                   end = mid - 1;
           }
       }
       return -1;
    }

    private static int binarySearch(int [] A, int target) {
        int left = 0;
        int right = A.length -1 ;
        while (left <= right) {
            int mid = (left + right)/2;
            if (A[mid] == target)
                right = mid - 1;
            else if (A[mid] > target)
                right = mid - 1;
            else
                left = mid + 1;
        }
        if(left < A.length && A[left] == target)
            return left;
        return -1;

    }
    public static int[] searchRange(int[] A, int target) {
        int t = binarySearch(A,target);
        int [] res = new int[]{-1,-1};
        if(A.length == 0)return res;
        if(t == -1)
            return res;
        else {
            res[0] = t;
            res[1] = t;
            while(++t < A.length && A[t] == target);
            res[1] = t - 1;

        }
        return res;
    }

    private static void backTrack(ArrayList<ArrayList<Integer>> res, ArrayList<Integer>a, int n, int k ,int start,int layer) {
        if (layer == k){
            res.add(new ArrayList<Integer>(a));
            return;
        }
        for (int i = start; i < n; i++) {
            a.add(i + 1);
            backTrack(res,a,n,k,i + 1,layer + 1);
            a.remove(a.size() - 1);
        }
    }

    public static ArrayList<ArrayList<Integer>> combine(int n, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<>();
        backTrack(res,a,n,k,0,0);
        return res;
    }

    public boolean isValidSudoku(char[][] board) {
        if (board.length == 0 || board[0].length == 0)return false;
        int m = board.length;
        int n = board[0].length;
        boolean [][] rowFlag = new boolean[m][n];
        boolean [][] colFlag = new boolean[m][n];
        boolean [][] cellFlag = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n;j++) {
                if (board[i][j] >= '1' && board[i][j] <= '9') {
                    int c = board[i][j] - '1';
                    if (rowFlag[i][c] || colFlag[c][j] || cellFlag[3 * (i / 3) + j / 3][c]) return false;
                    rowFlag[i][c] = true;
                    colFlag[c][j] = true;
                    cellFlag[3 * (i / 3) + j / 3][c] = true;
                }
            }
        }
        return true;
    }
    public ArrayList<Integer> getRow(int rowIndex) {
        ArrayList<ArrayList<Integer>> res = new ArrayList();
        if (rowIndex < 0)return null;
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        res.add(a);
        if (rowIndex == 0)return a;
        ArrayList<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(1);
        res.add(b);
        if (rowIndex == 1)return b;
        for (int i = 2 ; i < rowIndex + 1; i++) {
            ArrayList<Integer> t = new ArrayList();
            for (int j = 0; j <= i; j++) {
                if (j == 0 ){
                    t.add(1);

                } else if (j == i) {
                    t.add(1);
                } else {

                    int t1 = res.get(i - 1).get(j);
                    int t2 = res.get(i - 1).get(j - 1);
                    t.add(t1 + t2);
                }

            }
            res.add(t);
        }
        return res.get(rowIndex);
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null)return l2;
        if (l2 == null)return l1;
        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode dummy = new ListNode(0);
        ListNode p = dummy;
        int carry = 0;
        int t = 0;
        while (p1 != null && p2 != null) {
            t = p1.val + p2.val + carry;
            ListNode node = new ListNode(t % 10);
            carry = t / 10;
            p.next = node;
            p = node;
            p1 = p1.next;
            p2 = p2.next;

        }
        while (p1 != null) {
            t = p1.val + carry;
            ListNode node = new ListNode( t % 10);
            carry = t / 10;
            p.next = node;
            p = node;
            p1 = p1.next;
        }
        while (p2 != null) {
            t = p2.val + carry;
            ListNode node = new ListNode(t % 10);
            carry = t / 10;
            p.next = node;
            p = node;
            p2 = p2.next;

        }

        if (carry != 0) {
            ListNode node = new ListNode(carry);
            p.next = node;

        }
        return dummy.next;
    }

    public static ArrayList<String> letterCombinations(String digits) {

        ArrayList<String> res = new ArrayList<>();
        if(digits.length() == 0)return res;

        ArrayList<String> dict = new ArrayList<>();
        dict.add("");
        dict.add("");
        dict.add("abc");
        dict.add("def");
        dict.add("ghi");
        dict.add("jkl");
        dict.add("mno");
        dict.add("pqrs");
        dict.add("tuv");
        dict.add("wxyz");
        for (int i = 0; i < digits.length(); i++) {
            ArrayList<String> t = new ArrayList<>();
            String str = dict.get(digits.charAt(i) - '0');
            for (int j = 0; j < str.length(); j++) {
                for (String s : res) {
                    t.add(new String(s + str.charAt(i)));
                }
            }
            res = t;
        }
        return res;

    }
    public static ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null)return res;
        Queue<TreeNode> queue = new LinkedList<>();
        int curLayer = 0;
        int nextLayer = 0;
        queue.offer(root);
        curLayer = 1;
        ArrayList<Integer> a = new ArrayList<>();
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            a.add(node.val);
            curLayer--;
            if (node.left != null) {
                queue.offer(node.left);
                nextLayer++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextLayer++;
            }
            if (curLayer == 0) {
                curLayer = nextLayer;
                nextLayer = 0;
                res.add(a);
                a = new ArrayList<Integer>();
            }
        }
        Collections.reverse(res);
        return res;
    }

    public static boolean isValidBST(TreeNode root) {
        if (root == null)return true;
        Stack<TreeNode> stack = new Stack<>();
        int cur = 0;
        int pre = Integer.MIN_VALUE;
        TreeNode t = root;
        while (true) {

            while (t != null){
                stack.push(t);
                t = t.left;
            }
            if (stack.isEmpty())break;
            t = stack.pop();
            if (t.val <= pre)
                return false;
            else
                pre = t.val;
            t = t.right;

        }
        return true;
    }

    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        s = s.toLowerCase();
        while (left < right) {
            if (left < right && !isChar(s.charAt(left)))left++;
            else if (left < right && !isChar(s.charAt(right)))right--;
            else if (Math.abs(s.charAt(left)-s.charAt(right)) != 0)return false;
            else {
                left++;
                right--;
            }
        }
        return true;
    }
    private static boolean isChar(char ch) {
        if ( 'a' <= ch && ch <= 'z' )return true;
        else if ('0' <= ch && ch <= '9')return true;
        else return false;

    }
    private TreeNode buildTreeHelper(int [] inorder, int inorderbng, int inorderend, int [] postorder, int postorderbng, int postorderend) {
        if (inorderbng > inorderend){
            return null;
        }
        if (postorderbng > postorderend) {
            return null;

        }
        TreeNode root = new TreeNode(postorder[postorderend]);
        int i = inorderbng;
        for (; i <= inorderend; i++) {
            if (inorder[i] == postorder[postorderend])
                break;
        }
        int len = i - inorderbng;
        root.left = buildTreeHelper(inorder, inorderbng, i - 1, postorder, postorderbng, postorderbng + len - 1);
        root.right = buildTreeHelper(inorder, i + 1, inorderend, postorder, postorderbng + len, postorderend - 1);
        return root;

    }
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int l1 = inorder.length;
        int l2 = postorder.length;
        if (l1 == 0 )
            return null;
        if (l2 == 0)
            return null;
        if (l1 != l2)
            return null;
        return buildTreeHelper(inorder,0,l1 - 1, postorder, 0,l2 - 1);

    }

    TreeNode first = null;
    TreeNode second = null;
    TreeNode pre = null;
    private void swap (TreeNode node1 , TreeNode node2) {
        int t = node1.val;
        node1.val = node2.val;
        node2.val = t;
    }
    public void recoverTree(TreeNode root) {
        inorder(root);
        if (first != null && second != null)
            swap(first,second);
    }
    private void inorder(TreeNode root) {
        if (root == null)
            return ;
        inorder(root.left);
        if (pre == null)pre  = root;
        else {
            if (pre.val > root.val) {
                if (first == null)first = pre;
                second = root;
            }
            pre = root;
        }


        inorder(root.right);
    }
    public static boolean canJump(int[] A) {
        int len = A.length;
        if (len == 1)
            return true;
        int reach = 0;
        for(int i = 0; i < len ; i++) {
            if (i > reach) {
                break;
            }
            if (reach >= len-1)
                break;
            reach = Math.max(reach,i + A[i]);
        }
        return reach >= len - 1;
    }




    public static void main(String [] args) {

        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
        ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(1);
        ArrayList<Integer> a2 = new ArrayList<>();
        a2.add(2);
        a2.add(3);
        arrayLists.add(a1);
        arrayLists.add(a2);
        System.out.println(minimumTotal(arrayLists));
        System.out.println(minCut("abb"));
        System.out.println(numDistinct("rabbbit","rabbit"));
        System.out.println(isInterleave("aabcc","dbbca","aadbbbaccc"));
        System.out.println(numDecodings("10"));
        System.out.println(minDistance("ab","a"));
        System.out.println(climbStairs(4));
        System.out.println(uniquePaths(2,2));
        System.out.println(simplifyPath("/home/foo/.ssh/../.ssh2/authorized_keys/"));
        System.out.println(addBinary("0","0"));
        System.out.println(divide(-11,11));
        int [] a= new int[]{1,2,2};
        System.out.println(subsetsWithDup(a));
        System.out.println(sqrt(2));
        String [] strs = new String[]{"a","b"};
        System.out.println(longestCommonPrefix(strs));
        System.out.println(reverse(-1233333333));
        System.out.println(atoi("  c12 2cyuan"));
        System.out.println(longestPalindrome("babad"));
        System.out.println(isNumber("e9"));
       // System.out.println(isMatch("aaa","aa"));
        System.out.println(maxProfit(new int[]{6,1,3,2,4,7}));
        System.out.println(intToRoman(3));
        System.out.println(isMatch("ab","a*b"));
        System.out.println(isPalindrome(1));
        System.out.println(generateMatrix(3));
        System.out.println(firstMissingPositive(new int[]{3,4,5}));
        System.out.println(getPermutation(9,2));
        int z = 0x4c4b4d40;
        double zz = z;
        System.out.println(zz);
        System.out.println(threeSumClosest(new int[]{0,1,2,-3},1));
        System.out.println(generate(7));
        System.out.println(threeSum(new int[] {-2,0,1,1,2}));
        System.out.println(isValid("("));
        System.out.println(trap(new int[]{4,2,3}));
        System.out.println(longestValidParentheses("()"));
        System.out.println(solveNQueens(4));
        HashSet<String> set = new HashSet<>();
        set.add("a");;
        set.add("b");;
        set.add("c");;

        System.out.println(ladderLength("a","c",set));
        System.out.println(search(new int[]{3,1},3));
        System.out.println(searchRange(new int[]{5,7,7,8,8,10},8));
        System.out.println(combine(4,2));
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4).next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6).next = new ListNode(6);
        System.out.println(addTwoNumbers(l1,l2));
        System.out.println(letterCombinations("23"));
        TreeNode root = new TreeNode(3);
        TreeNode child1 = new TreeNode(9);
        TreeNode child2 = new TreeNode(20);
        TreeNode child3 = new TreeNode(15);
        TreeNode child4 = new TreeNode(7);
        root.left = child1;
        root.right = child2;
        child2.left = child3;
        child2.right = child4;
        System.out.println(levelOrderBottom(root));
        TreeNode root1 = new TreeNode(1);
        TreeNode leftchild1 = new TreeNode(1);
        root1.left = leftchild1;
        System.out.println(isValidBST(root1));
        System.out.println(isPalindrome("3?8044''0''tt08?3"));
        System.out.println(canJump(new int[]{3,2,1,0,4}));




    }
}
