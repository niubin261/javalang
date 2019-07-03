import java.io.FileNotFoundException;

public class ExpectionTest {
    public static void main(String[] args) {
        try {
            throw new FileNotFoundException();

        } catch (FileNotFoundException e) {
          System.out.println("error");
        }
    }
}
