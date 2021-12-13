package com.learning.rules;

import com.learning.enums.HttpMethod;
import com.learning.enums.HttpResultType;

import java.lang.annotation.*;

/**
 * @ClassName HttpRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:52
 * @Version 1.0
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface HttpRule {
    String childProperty() default "";
    HttpMethod method();
    String url() default "";
    String bakUrl() default "";
    boolean isValueInBody() default false;
    boolean isBodyArray() default false;
    HttpResultType resultType() default HttpResultType.Object;
}
