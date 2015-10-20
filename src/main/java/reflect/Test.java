package reflect;

import java.lang.reflect.Field;

/**
 * Created by wana on 2015/10/19.
 */
public class Test {
    private static final String INT = "int";
    private static final String STRING = "string";
    private static final String CHAR = "char";
    private static final String BYTE = "byte";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final String BOOLEAN = "boolean";
    private static final String LONG = "long";
    private static final String SHORT = "short";
    private static final String BYTES = "byte[]";


    public static void main(String[] args) {
        try {
            Class c = Class.forName("reflect.Student");
            Student s = (Student) c.newInstance();
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
//                System.out.println(f.getType().getSimpleName());
                switch (f.getType().getSimpleName()) {
                    case Test.BOOLEAN:
                        System.out.println(f.getName() + "的类型为boolean，值为:" + f.getBoolean(s));
                        break;
                    case Test.BYTE:
                        System.out.println(f.getName() + "的类型为byte，值为:" + f.getByte(s));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
