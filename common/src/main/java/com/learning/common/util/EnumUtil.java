package com.learning.common.util;

import com.learning.common.abstraction.CustomEnum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName EnumUtil
 * @Description 枚举工具类, 封装所有通用的枚举相关的操作
 * @Author hufei
 * @Date 2020/7/7 9:45
 * @Version 1.0
 */
public class EnumUtil {

    /**
     * 通过name名称获取枚举对象
     *
     * @param name   枚举的名称
     * @param tClass 枚举类
     * @param <T>    枚举类型
     * @return 返回枚举
     */
    public static <T extends CustomEnum> T getByName(String name, Class<T> tClass) {
        for (T each : tClass.getEnumConstants()) {
            if (name.equals(each.getName())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 通过value值获取枚举对象
     *
     * @param value  枚举的值
     * @param tClass 枚举类
     * @param <T>    枚举类型
     * @return 返回枚举
     */
    public static <T extends CustomEnum> T getByValue(int value, Class<T> tClass) {
        for (T each : tClass.getEnumConstants()) {
            if (value == each.getValue()) {
                return each;
            }
        }
        return null;
    }

    /**
     * 获取枚举所有名称
     *
     * @param tClass 枚举类
     * @return 返回枚举名称集合
     */
    public static <T extends CustomEnum> List<String> getNames(Class<T> tClass) {
        List<String> list = new ArrayList<>();
        for (T each : tClass.getEnumConstants()) {
            list.add(each.getName());
        }
        return list;
    }

    /**
     * 获取枚举值
     */
    public static int getValue(CustomEnum custom) {
        if (null == custom)
            return 0;
        return custom.getValue();
    }

    /**
     * 获取枚举名称
     */
    public static String getName(CustomEnum custom) {
        if (null == custom)
            return "";
        return custom.getName();
    }

    /**
     * @Description 通过值反射获取名称
     * @Param [p, v]
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/11/4 19:04
     */
    public static <T extends CustomEnum> String getNameByValue(Class<T> p, int v) {
        Object[] objects = p.getEnumConstants();
        try {
            Method getName = p.getMethod("getName");
            Method getValue = p.getMethod("getValue");
            for (Object obj : objects) {
                if (getValue.invoke(obj).equals(v))
                    return getName.invoke(obj).toString();
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * @Description 通过名称反射获取值
     * @Param [p, v]
     * @Return int
     * @Author hufei
     * @Date 2020/11/4 19:04
     */
    public static <T extends CustomEnum> int getValueByName(Class<T> p, String v) {
        Object[] objects = p.getEnumConstants();
        try {
            Method getName = p.getMethod("getName");
            Method getValue = p.getMethod("getValue");
            for (Object obj : objects) {
                if (getName.invoke(obj).equals(v))
                    return Integer.parseInt(getValue.invoke(obj).toString());
            }
        } catch (Exception ignored) {
        }
        return -1;
    }

}
