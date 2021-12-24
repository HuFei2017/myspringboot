package com.learning.rules;

import com.learning.enums.DataSourceTableOrderType;

import java.lang.annotation.*;

/**
 * @ClassName TableRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/12/24 11:10
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface TableRule {
    int length() default -1;

    String defaultValue() default "";

    boolean require() default true;

    boolean key() default false;

    DataSourceTableOrderType orderType() default DataSourceTableOrderType.None;

    String primitiveType() default "";
}
