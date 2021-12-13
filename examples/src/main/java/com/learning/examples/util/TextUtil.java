package com.learning.examples.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @ClassName TextUtil
 * @Description 文本工具类, 封装所有的文本转换及对象反序列化
 * @Author hufei
 * @Date 2020/7/17 10:09
 * @Version 1.0
 */
public class TextUtil {

    private static final Logger log = LoggerFactory.getLogger(TextUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 字符串截取数字
     *
     * @param text 枚举的名称
     * @return 返回整型数字
     */
    public static int parseInt(String text) {

        if (text.isEmpty()) {
            return 0;
        }

        try {
            String temp = text.replace(" ", "");
            if (temp.contains("PB")) {
                String value = temp.replace("PB", "");
                if (value.isEmpty()) {
                    return 0;
                }
                return value.equals("-1") ? -1 : Integer.parseInt(value) * 1024 * 1024 * 1024 * 1024 * 1024;
            } else if (temp.contains("TB")) {
                String value = temp.replace("TB", "");
                if (value.isEmpty()) {
                    return 0;
                }
                return value.equals("-1") ? -1 : Integer.parseInt(value) * 1024 * 1024 * 1024 * 1024;
            } else if (temp.contains("GB")) {
                String value = temp.replace("GB", "");
                if (value.isEmpty()) {
                    return 0;
                }
                return value.equals("-1") ? -1 : Integer.parseInt(value) * 1024 * 1024 * 1024;
            } else {
                return Integer.parseInt(temp);
            }
        } catch (Exception e) {
            log.info("the text " + text + " can not be parse to int.");
            return 0;
        }
    }

    /**
     * 字符串截取数字
     *
     * @param text 枚举的名称
     * @return 返回浮点类型数字
     */
    public static float parseFloat(String text) {

        if (text.isEmpty()) {
            return 0.0f;
        }

        try {
            String temp = text.replace(" ", "");
            if (temp.contains("PB")) {
                String value = temp.replace("PB", "");
                if (value.isEmpty()) {
                    return 0.0f;
                }
                return value.equals("-1") ? -1.0f : Float.parseFloat(value) * 1024 * 1024 * 1024 * 1024 * 1024;
            } else if (temp.contains("TB")) {
                String value = temp.replace("TB", "");
                if (value.isEmpty()) {
                    return 0.0f;
                }
                return value.equals("-1") ? -1.0f : Float.parseFloat(value) * 1024 * 1024 * 1024 * 1024;
            } else if (temp.contains("GB")) {
                String value = temp.replace("GB", "");
                if (value.isEmpty()) {
                    return 0.0f;
                }
                return value.equals("-1") ? -1.0f : Float.parseFloat(value) * 1024 * 1024 * 1024;
            } else if (temp.contains("MB")) {
                String value = temp.replace("MB", "");
                if (value.isEmpty()) {
                    return 0.0f;
                }
                return value.equals("-1") ? -1.0f : Float.parseFloat(value) * 1024 * 1024;
            } else if (temp.contains("KB")) {
                String value = temp.replace("KB", "");
                if (value.isEmpty()) {
                    return 0.0f;
                }
                return value.equals("-1") ? -1.0f : Float.parseFloat(value) * 1024;
            } else if (temp.contains("B")) {
                String value = temp.replace("B", "");
                if (value.isEmpty()) {
                    return 0.0f;
                }
                return value.equals("-1") ? -1.0f : Float.parseFloat(value);
            } else {
                return Float.parseFloat(temp);
            }
        } catch (Exception e) {
            log.info("the text " + text + " can not be parse to int.");
            return 0.0f;
        }
    }

    /**
     * @Description 文本转换实体类
     * @Param [str, tClass] str:文本内容; tClass:实体类
     * @Return T
     * @Author hufei
     * @Date 2020/8/19 11:16
     */
    public static <T> T parseJson(String str, Class<T> tClass) {
        try {
            return mapper.readValue(str, tClass);
        } catch (JsonProcessingException ex) {
            log.info("the string " + str + " can not be deserialize successfully.");
        }
        return null;
    }

    /**
     * @Description 对象序列化
     * @Param [obj] 对象实例
     * @Return java.lang.String 序列化文本
     * @Author hufei
     * @Date 2020/8/19 11:16
     */
    public static String fromJson(Object obj) {

        if (null == obj) {
            return "";
        }

        if (obj.getClass().getName().equals("java.lang.String")) {
            return String.valueOf(obj);
        }

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            log.info("the object " + obj + " can not be deserialize successfully.");
        }
        return "";
    }

    /**
     * @Description 字符串转Json对象
     * @Param [str] 文本内容
     * @Return com.fasterxml.jackson.databind.JsonNode Json对象
     * @Author hufei
     * @Date 2020/9/25 15:49
     */
    public static JsonNode parseJsonNode(String str) {
        try {
            return mapper.readTree(str);
        } catch (JsonProcessingException ex) {
            log.info("the string " + str + " can not be formatted successfully.");
        }
        return mapper.nullNode();
    }

    /**
     * @Description Json对象获取指定字段值
     * @Param [node, pos] node:Json对象; pos:字段位置
     * @Return java.lang.String 字段值
     * @Author hufei
     * @Date 2020/11/25 13:47
     */
    public static String extractFieldValue(JsonNode node, String pos) {

        if (null == node || node.isEmpty()) {
            return "";
        }

        if (pos.startsWith(".") || pos.endsWith(".")) {
            return "";
        }

        if (pos.contains(".")) {
            return extractFieldValue(node.get(pos.substring(0, pos.indexOf("."))), pos.substring(pos.indexOf(".") + 1));
        } else {
            return node.has(pos) ? (node.get(pos).isTextual() ? node.get(pos).asText() : node.get(pos).toString()) : pos;
        }
    }

    /**
     * @Description 获取Json某字段的值（字符串类型）
     * @Param [data, key]
     * @Return java.lang.String
     * @Author hufei
     * @Date 2021/4/23 9:54
     */
    public static String getFieldStringValue(JsonNode data, String key) {

        if (null == data || data.isNull()) {
            return "";
        }

        JsonNode value = data.get(key);

        if (null == value || value.isNull()) {
            return "";
        }

        if (value.isInt()) {
            return String.valueOf(value.intValue());
        } else if (value.isTextual()) {
            return value.textValue();
        } else {
            return fromJson(value);
        }
    }

    /**
     * @Description 获取Json某字段的值（INT类型）
     * @Param [data, key]
     * @Return int
     * @Author hufei
     * @Date 2021/4/23 9:54
     */
    public static int getFieldIntValue(JsonNode data, String key) {

        if (null == data || data.isNull()) {
            return -1;
        }

        JsonNode value = data.get(key);

        if (null == value || value.isNull()) {
            return -1;
        }

        if (value.isInt()) {
            return value.intValue();
        }

        return -1;
    }

    /**
     * @Description 文本Base64加密
     * @Param [str] 加密前内容
     * @Return java.lang.String 加密后内容
     * @Author hufei
     * @Date 2020/8/19 11:17
     */
    public static String parseBase64(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8)).replace('-', '+').replace('_', '/');
    }

    /**
     * @Description 文本Base64解密
     * @Param [base64Str] 解密前内容
     * @Return java.lang.String 解密后内容
     * @Author hufei
     * @Date 2020/8/19 11:17
     */
    public static String fromBase64(String base64Str) {
        return new String(Base64.getDecoder().decode(base64Str.replaceAll("[^A-Za-z0-9+/]", "+")), StandardCharsets.UTF_8);
    }

    /**
     * @Description 获取异常堆栈信息
     * @Param [throwable] 异常
     * @Return java.lang.String 堆栈信息
     * @Author hufei
     * @Date 2020/10/19 17:21
     */
    public static String getStackTrace(Throwable throwable) {

        StringBuilder builder = new StringBuilder();

        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            builder.append(sw.toString());
        } catch (IOException ignored) {
        }

        return builder.toString();
    }

}
