package com.learning.rules;

import com.learning.enums.DataSourceTableOrderType;

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
public @interface ColumnRule {
    boolean key() default false;

    String type() default "";

    int length() default -1;

    DataSourceTableOrderType order() default DataSourceTableOrderType.None;

    boolean require() default true;
}
