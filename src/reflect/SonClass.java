package reflect;

public class SonClass extends ParentClass {
    private String sonname;
    protected int sonage;
    public String sonBirthday;
    public void printSonMsg() {
        System.out.println("Son Msg - name: " + sonname + "age:" + age);
    }
    private void setSonname(String name) {
        sonname = name;
    }
    public void setSonage(int age) {
        sonage = age;


    }

    private int getSonage() {
        return sonage;
    }
    private String getSonname() {
        return sonname;
    }

}
