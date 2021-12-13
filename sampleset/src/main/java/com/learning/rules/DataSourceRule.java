package com.learning.rules;

import java.lang.annotation.*;

/**
 * @ClassName DataSourceRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:45
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface DataSourceRule {
    String namespace();
}
