package com.fangger.annotation;

import java.lang.annotation.*;

/**
 * Created by popo on 2014/10/6.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Reg {
    String value() default "";
}
