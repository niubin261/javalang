import javax.swing.plaf.BorderUIResource;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public enum  EnumTest {
    ADD("+") {
        @Override
        public int cal(int a, int b) {
            return a + b;
        }
    },
    SUB("-") {
        @Override
        public int cal(int a, int b) {
            return 0;
        }
    },
    MUL("*") {
        @Override
        public int cal(int a, int b) {
            return 0;
        }
    },
    DIV("/") {
        @Override
        public int cal(int a, int b) {
            if (b == 0) {
                throw new IllegalArgumentException("devide 0");
            }
            return a / b;
        }
    };
    private String operator;
    EnumTest(String operator) {
        this.operator = operator;
    }
    public abstract int cal(int a, int b);
    public static void main(String [] args) {
        HashMap<String, String> m = new HashMap<>();
        m.put(null,"1");
        m.put(null,"2");
        //m.put(null,null);
        ConcurrentHashMap<String, String> hm = new ConcurrentHashMap<>();
        hm.put("1",null);

        System.out.println(m.get(null));
        int a = EnumTest.ADD.cal(1,2);
        System.out.println(a);
    }


}
