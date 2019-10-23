package annotations;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

public class JUnitLike {
    public static void main(String[] args) throws Throwable {
        Properties props = new Properties();
        props.load(new FileReader("ToTest.properties"));
        props.values().stream().forEach(System.out::println);
        String toTest = (String)props.get("1");
        Class classToTest = Class.forName(toTest);

        Constructor zeroArg = classToTest.getConstructor();
        Object myTarget = zeroArg.newInstance();

//        System.setSecurityManager(new SecurityManager());

//        Method [] methods = classToTest.getMethods();
        Method [] methods = classToTest.getDeclaredMethods();

        for (Method m : methods) {
            System.out.println("> " + m);
            RunMe annot = m.getAnnotation(RunMe.class);
            if (annot != null) {
                System.out.println("****** Carries @RunMe text = "
                        + annot.text() + " value = " + annot.value());
                m.setAccessible(true);
                m.invoke(myTarget);
            }
        }
    }
}
