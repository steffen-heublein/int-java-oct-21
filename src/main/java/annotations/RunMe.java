package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD/*, ElementType.CONSTRUCTOR*/})
public @interface RunMe {
    String text() default "Default";
    String value() default "unset value";
}
