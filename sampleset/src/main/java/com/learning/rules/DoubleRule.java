package com.learning.rules;

import java.lang.annotation.*;

/**
 * @ClassName DateRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:56
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface DoubleRule {
    double min() default Double.MIN_VALUE;
    double max() default Double.MAX_VALUE;
    double step() default 0.1;
    double defaultValue() default 0.0;
}
