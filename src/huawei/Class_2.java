package huawei;

import java.util.*;

public class Class_2 {
    private static ArrayList<StringBuilder> splite8(String s1) {
        ArrayList<StringBuilder> ss = new ArrayList<>();
        if (s1.length() == 0)return ss;
        int l1 = s1.length();
        int t = l1 / 8;
        for (int i = 0; i < t; i++) {
            StringBuilder builder = new StringBuilder();
            builder.append(s1.substring(8*i, 8*(i + 1) - 1));
            ss.add(builder);

        }
        if (l1 - t * 8 > 0) {
            StringBuilder s = ss.get(t - 1);
            for (int i = 0; i < l1 - t * 8 ; i++) {

                s.append('0');
            }
        }
        return ss;


    }
    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();
        String s2 = in.nextLine();
        ArrayList<StringBuilder> ss1 = splite8(s1);
        ArrayList<StringBuilder> ss2 = splite8(s2);
        for (int i = 0; i < ss1.size(); i++) {
            System.out.println(ss1.get(i).toString());
        }
        for (int i = 0; i < ss2.size(); i++) {
            System.out.println(ss2.get(i).toString());
        }

    }
}
