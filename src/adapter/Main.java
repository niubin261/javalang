package adapter;
import java.util.*;
public class Main {
    public int getMaxDiff(int[] array) {
        int length = array.length;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        int[][] dp = new int[length + 1][sum / 2 + 1];
        for (int i = 0; i < length; i++) {
            for (int j = 1; j <= sum / 2; j++) {
                dp[i + 1][j] = dp[i][j];
                if (array[i] <= j && dp[i][j - array[i]] + array[i] > dp[i][j]) {
                    dp[i + 1][j] = dp[i][j - array[i]] + array[i];
                }
            }
        }
        return sum - 2 * dp[length][sum / 2];
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        int n=sc.nextInt();
        int[] array=new int [n];
        for(int i=0;i<n;i++) {
            array[i]=sc.nextInt();
        }
        Main m = new Main();
        System.out.println(m.getMaxDiff(array));

    }



}

