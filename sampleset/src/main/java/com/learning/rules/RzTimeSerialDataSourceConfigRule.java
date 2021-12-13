package com.learning.rules;

import java.lang.annotation.*;

/**
 * @ClassName TimeSerialConfigRule
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:48
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface RzTimeSerialDataSourceConfigRule {
    String vibDataNameSpace();

    String waveInfoNameSpace();

    String waveDataNameSpace();

    String tsDataNameSpace();
}
