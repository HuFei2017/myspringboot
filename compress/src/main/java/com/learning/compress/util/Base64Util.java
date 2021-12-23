package com.learning.compress.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @ClassName Base64Util
 * @Description TODO
 * @Author hufei
 * @Date 2021/12/23 17:07
 * @Version 1.0
 */
public class Base64Util {

    public static String compress(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8)).replace('-', '+').replace('_', '/');
    }

    public static String uncompress(String base64Str) {
        return new String(Base64.getDecoder().decode(base64Str.replaceAll("[^A-Za-z0-9+/]", "+")), StandardCharsets.UTF_8);
    }

}
