import java.io.*;

public class FileTest {
    public static void main(String [] args) throws IOException {
        String fileName = "data.tat";
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
        out.writeInt(-1);
        out.close();
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        int a = in.readInt();
        System.out.println(Integer.toHexString(a));
        in.close();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int ab = reader.read();
        System.out.println(ab);

    }
}
