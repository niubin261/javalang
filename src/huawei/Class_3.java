package huawei;


import java.util.*;
public class Class_3 {
    private static int To10(String s) {
        Map<Character, Integer> m = new HashMap<>();
        m.put('A', 10);
        m.put('B', 11);
        m.put('C', 12);
        m.put('D', 13);
        m.put('E', 14);
        m.put('F', 15);
        m.put('9', 9);
        m.put('8', 8);
        m.put('7', 7);
        m.put('6', 6);
        m.put('5', 5);
        m.put('4', 4);
        m.put('3', 3);
        m.put('2', 2);
        m.put('1', 1);
        m.put('0', 0);
        int l = s.length();
        int pos = l - 1;
        int t = 0;
        while (s.charAt(pos) != 'x') {
            int n = (int) (Math.pow(16, l - 1 - pos ) * (m.get(s.charAt(pos))));
            t = t + n;
            pos--;

        }
        return t;
    }
    public static void main(String [] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {//注意while处理多个case              int a = in.nextInt();
            String b = in.nextLine();
            System.out.println(To10(b));

        }
    }

}