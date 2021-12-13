package com.learning.rules;

import java.lang.annotation.*;

/**
 * @ClassName PropertyRestrictRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 16:58
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface PropertyRestrictRule {
    boolean unique() default false;

    boolean optional() default false;

    boolean deprecated() default false;

    boolean visiable() default true;
}
