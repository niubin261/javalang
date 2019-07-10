public class MyException extends Exception{
    public MyException(){}
    public MyException(String exceptionMessage) {
        super(exceptionMessage);
    }
    public static void main(String [] args) throws Exception{
        try {
            String a = null;
            try {
                a.equals("a");
            } catch (Exception e) {
                throw new MyException("a == null");
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        throw new MyException("out");
    }
}
