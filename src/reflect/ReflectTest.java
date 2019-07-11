package reflect;

import java.lang.module.ModuleFinder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.StreamSupport;

public class ReflectTest {
    private static void printFields() {
        Class pclass = ParentClass.class;
        System.out.println("name of class" + pclass.getName());
        Field [] fields = pclass.getFields();
        for (Field field :
                fields) {
            int modifiers = field.getModifiers();
            System.out.println(Modifier.toString(modifiers) + "");
            System.out.println(field.getType().getName() + "" + field.getName());
        }
    }
    private static void printMethods() {
        Class sclass = SonClass.class;
        System.out.println("name of class" + sclass.getName());
        Method [] methods = sclass.getMethods();
        for (Method method:
             methods) {
            int modifiers = method.getModifiers();
            System.out.println(Modifier.toString(modifiers)+"");
            Class returnType = method.getReturnType();
            System.out.println(returnType.getName() + "" + method.getName());

        }
    }
    private static void getPrivateMethod() throws Exception {
        SonClass sonClass = new SonClass();
        Class sclass = sonClass.getClass();
        Method privateMethod = sclass.getDeclaredMethod("getSonage");
        if (privateMethod != null) {
            privateMethod.setAccessible(true);
            System.out.println(privateMethod.invoke(sonClass));
        }
    }
    public static void main(String [] args) {
        printFields();
        printMethods();
        try {

            getPrivateMethod();
        } catch (Exception e) {

        }
    }

}
