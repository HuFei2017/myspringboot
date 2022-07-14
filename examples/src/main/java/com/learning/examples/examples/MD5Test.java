package com.learning.examples.examples;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName MD5Test
 * @Description TODO
 * @Author hufei
 * @Date 2022/7/14 12:43
 * @Version 1.0
 */
public class MD5Test {
    public static void main(String[] args) {
        String str = "/phm/analysis-web/wavecallback_1657758531000_qweq1";
        String targetStr = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
        System.out.println(targetStr);
    }
}
