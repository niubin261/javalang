import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * c1...cn个硬币，面值各不相同，现要用最少的硬币数，使得钱数为k，给出方案
 * @author sky
 *
 * 设dp[i]为k为i时，最少需要的硬币个数
 * dp[0]=1
dp[i] = min(dp[i-cons[j]])+1
 */

public class MinIcons {
    private List<Integer> cn;
    private Integer k;
    public MinIcons(List<Integer> cn, int k){
        this.cn = cn;
        this.k = k;
    }

    public int getMinIconNumber(){
        Map<Integer,Integer> dp = new HashMap<Integer, Integer>();
        dp.put(0, 0);
        for(int i=1; i<=k;i++) {
            int tmp = Integer.MAX_VALUE;
            for(int j=0;j<cn.size();j++){
                if(i >= cn.get(j)) {
                    tmp = min(tmp,dp.get(i-cn.get(j))+1);
                }
            }
            dp.put(i,tmp);
        }
        return dp.get(this.k) == Integer.MAX_VALUE ? -1 : dp.get(this.k);
    }
    public int min(int x, int y){
        if(x>y) {
            return y;
        }
        return x;
    }
    public static void main(String[] args) {
        List<Integer> l = new ArrayList<Integer>();
        l.add(100);
        l.add(100);
        l.add(500);
        System.out.print(new MinIcons(l,12).getMinIconNumber());
    }
}