package huawei;


import java.util.Scanner;

public class Class_1 {
    private static int lenOfWord(String strs) {
        int len = strs.length();
        int res = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (strs.charAt(i) != ' ') {
                res++;
            } else {
                break;
            }
        }
        return res;

    }
    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.println(lenOfWord(s));
    }
}