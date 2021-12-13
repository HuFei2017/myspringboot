package com.learning.rules;

import com.learning.enums.AnalyseDataType;

import java.lang.annotation.*;

/**
 * @ClassName AnalyseDataTypeRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:46
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface DataTypeRule {
    AnalyseDataType type();
}
