package com.learning.rules;

import com.learning.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * @ClassName DataSourceTypeRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 16:48
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface DataSourceTypeRule {
    DataSourceType type() default DataSourceType.Any;
}
