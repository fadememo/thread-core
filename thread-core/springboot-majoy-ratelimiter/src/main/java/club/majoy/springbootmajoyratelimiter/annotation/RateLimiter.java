package club.majoy.springbootmajoyratelimiter.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//用在方法上
@Retention(RetentionPolicy.RUNTIME)//
@Documented
public @interface RateLimiter {
    int limit() default 3;
    int timeout() default 1000;
}
