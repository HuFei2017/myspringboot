package com.learning.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CollectionUtil
 * @Description 集合工具类, 封装所有的集合类操作
 * @Author hufei
 * @Date 2020/10/19 13:54
 * @Version 1.0
 */
public class CollectionUtil {

    /**
     * @Description 拼接集合元素
     * @Param [list, separator] list:集合; separator:连接符
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/10/19 14:01
     */
    public static String join(Iterable<?> list, char separator) {
        return null == list ? "" : StringUtils.join(list, separator);
    }

    /**
     * @Description 拼接集合元素
     * @Param [list, separator] list:集合; separator:连接符
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/10/19 14:01
     */
    public static String join(Iterable<?> list, String separator) {
        return null == list ? "" : StringUtils.join(list, separator);
    }

    /**
     * @Description 拼接集合元素
     * @Param [array, separator] array:集合; separator:连接符
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/10/19 14:01
     */
    public static String join(Object[] array, char separator) {
        return null == array ? "" : StringUtils.join(array, separator);
    }

    /**
     * @Description 拼接集合元素
     * @Param [array, separator] array:集合; separator:连接符
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/10/19 14:01
     */
    public static String join(Object[] array, String separator) {
        return null == array ? "" : StringUtils.join(array, separator);
    }

    /**
     * 将一组数据平均分成n组
     *
     * @param list 要分组的数据源
     * @param n    平均分成n组
     */
    public static <T> List<List<T>> averageAssign(List<T> list, int n) {
        List<List<T>> result = new ArrayList<>();
        int remainder = list.size() % n;  //(先计算出余数)
        int number = list.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = list.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = list.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

}
