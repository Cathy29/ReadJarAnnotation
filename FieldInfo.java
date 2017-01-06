import java.lang.annotation.*;

/**
 * Created by nzinfo on 16-12-30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FieldInfo {

        String type();
        String name();
        String[] value();
}