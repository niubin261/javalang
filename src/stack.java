import java.util.LinkedList;
import java.util.Queue;

public class stack {
    private Queue<Integer> queue1 = new LinkedList<>();
    private Queue<Integer> queue2 = new LinkedList<>();
    public stack(){}
    public int pop() {
        if (!queue1.isEmpty()) {
            int size = queue1.size();
            int num = size - 1;
            while (num != 0) {
                int t = queue1.poll();
                queue2.offer(t);
                num--;
            }
            return queue1.poll();
        } else if (!queue2.isEmpty()){
            int size = queue2.size();
            int num = size - 1;
            while (num != 0) {
                int t = queue2.poll();
                queue1.offer(t);
                num--;
            }
            return queue2.poll();
        } else {
            return -1;
        }
    }
    public boolean push(int elem) {
        if (!queue1.isEmpty()) {
           return queue1.offer(elem);
        } else {
            return queue2.offer(elem);
        }


    }
    public static void main(String [] args) {
        stack s = new stack();
        System.out.println(s.pop());
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
    }
}
