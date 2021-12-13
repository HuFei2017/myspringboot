package com.learning.rules;

import java.lang.annotation.*;

/**
 * @ClassName AnalyseRoleRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:38
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface AnalyseRoleRule {
    boolean analysable();
    boolean groupable();
}
