package com.learning.metadata.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author hufei
 * @Date 2021/2/5 9:26
 * @Version 1.0
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parseJson(Object obj, Class<T> tClass) {

        if (null == obj)
            return null;

        try {
            return mapper.readValue(writeValue(obj), tClass);
        } catch (JsonProcessingException ignored) {
        }

        return null;
    }

    public static JsonNode parseJson(Object data) {

        if (null == data)
            return null;

        try {
            return mapper.readTree(writeValue(data));
        } catch (JsonProcessingException ignored) {
        }

        return null;
    }

    public static String getStringValue(JsonNode data, String key) {

        if (null == data || data.isNull())
            return "";

        JsonNode value = data.get(key);

        if (null == value || value.isNull())
            return "";

        if (value.isInt())
            return String.valueOf(value.intValue());
        else if (value.isTextual())
            return value.textValue();
        else
            return writeValue(value);
    }

    public static int getIntValue(JsonNode data, String key) {

        if (null == data || data.isNull())
            return -1;

        JsonNode value = data.get(key);

        if (null == value || value.isNull())
            return -1;

        if (value.isInt())
            return value.intValue();

        return -1;
    }

    public static boolean contains(String[] data, String key) {

        if (data.length == 0)
            return false;

        for (String item : data) {
            if (item.equals(key))
                return true;
        }

        return false;
    }

    public static String writeValue(Object value) {

        if (null == value)
            return "";

        if (value.getClass().getName().equals("java.lang.String"))
            return String.valueOf(value);

        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException ignored) {
        }

        return "";
    }
}
