package huawei;

import java.util.*;

public class Class_4 {
    private static void helper(int [][] arr) {


    }

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        String ns = in.nextLine();
        int n = Integer.parseInt(ns);
        Map<Integer, Integer> m = new TreeMap<>();
        int [][] input = new int[n][2];
        for (int i = 0; i < n; i++) {
            String s = in.nextLine();
            String [] t = s.split(" ");
            input[i][0] = Integer.parseInt(t[0]);
            input[i][1] = Integer.parseInt(t[1]);
        }
        for(int i = 0; i < n; i++) {

            if (m.containsKey(input[i][0])) {
                int v = m.get(input[i][0]) + input[i][1];
                m.put(input[i][0], v);
            } else {
                m.put(input[i][0], input[i][1]);
            }
        }
        for (Integer k : m.keySet()) {
            System.out.print(k);
            System.out.print(" ");
            System.out.print(m.get(k));
            System.out.print("\n");
        }

    }
}
