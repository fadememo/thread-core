package club.majoy.springmajoylock.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
public @interface RedisLock {
    /**
     *
     * @return
     */
    String[] keys() default "";

    /**
     *
     * @return
     */
    long expire() default Long.MIN_VALUE;

    /**
     *
     * @return
     */
    long tryTimeout() default Long.MIN_VALUE;
}
