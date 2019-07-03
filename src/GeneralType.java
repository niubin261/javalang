import javax.print.attribute.standard.NumberUp;

public class GeneralType<Type> {
    private Type a;
    public GeneralType(Type a) {
        this.a = a;
    }
    public void printType() {
        System.out.println(a);
    }

}
class GeneralMethod {
    <Type> void generalMethod(Type T) {
        System.out.println(T);
    }
}
class GeneralTypeExends<Type extends Number> {

}