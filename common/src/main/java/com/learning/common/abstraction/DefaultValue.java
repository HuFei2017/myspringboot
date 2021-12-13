package com.learning.common.abstraction;

import java.lang.annotation.*;

/**
 * @InterfaceName DefaultValue
 * @Description 注解-用于配置默认值
 * @Author hufei
 * @Date 2020/10/20 9:13
 * @Version 1.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultValue {
    String value() default "";
}
