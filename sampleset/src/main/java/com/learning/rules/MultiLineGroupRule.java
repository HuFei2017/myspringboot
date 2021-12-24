package com.learning.rules;

import com.learning.enums.MultiLineMode;

import java.lang.annotation.*;

/**
 * @ClassName LineToRowRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/12/24 11:10
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface MultiLineGroupRule {
    String[] groupBy() default {};
    MultiLineMode mode() default MultiLineMode.ReadOnly;
}
