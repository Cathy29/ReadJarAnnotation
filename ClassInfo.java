import java.lang.annotation.*;

/**
 * Created by nzinfo on 17-1-5.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ClassInfo {
    String author() default "Wang";
    String date();
    String comments();
}
