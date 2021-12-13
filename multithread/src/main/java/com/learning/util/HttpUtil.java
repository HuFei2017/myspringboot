package com.learning.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpUtil
 * @Description TODO
 * @Author hufei
 * @Date 2021/10/22 17:15
 * @Version 1.0
 */
public class HttpUtil {

    private static RestTemplate restTemplate;
    private static HttpHeaders header;

    static {

        //初始化restTemplate
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(15000);
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setReadTimeout(15000);
        restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        //初始化header
        header = addHeader(new HashMap<>());
    }

    /**
     * @Description 通用get请求
     * @Param [url, params]
     * @Return
     * @Author jiashudong
     * @Date 2020/7/21 14:32
     */
    public static <T> T doGet(String url, Map<String, String> params, Class<T> tClass) throws RestClientException {
        return restTemplate.getForObject(addParams(url, params), tClass);
    }

    /**
     * @Description 通用get请求, 默认返回String
     * @Param [url, param, body]
     * @Return
     * @Author jiashudong
     * @Date 2020/7/21 14:32
     */
    public static String doGet(String url, Map<String, String> params) throws RestClientException {
        return restTemplate.getForObject(addParams(url, params), String.class);
    }

    /**
     * @Description 通用post请求
     * @Param [url, params, body]
     * @Return
     * @Author jiashudong
     * @Date 2020/7/21 14:32
     */
    public static <T> T doPost(String url, Map<String, String> params, Object body, Class<T> tClass) throws RestClientException {
        return restTemplate.postForObject(addParams(url, params), body, tClass);
    }

    /**
     * @Description 通用post请求, 默认返回String
     * @Param [url, params, body]
     * @Return
     * @Author jiashudong
     * @Date 2020/7/21 14:32
     */
    public static String doPost(String url, Map<String, String> params, Object body) throws RestClientException {
        return restTemplate.postForObject(addParams(url, params), body, String.class);
    }

    /**
     * @Description 通用put请求, 无返回
     * @Param [url, params, body]
     * @Return void
     * @Author hufei
     * @Date 2020/7/22 21:06
     */
    public static void doPut(String url, Map<String, String> params, Object body) throws RestClientException {
        restTemplate.put(addParams(url, params), body);
    }

    /**
     * @Description 通用delete请求, 无返回
     * @Param [url, params]
     * @Return void
     * @Author hufei
     * @Date 2020/7/22 21:07
     */
    public static void doDelete(String url, Map<String, String> params) throws RestClientException {
        restTemplate.delete(addParams(url, params));
    }

    /**
     * @Description 通用请求
     * @Param [method, url, headers, params, body, tClass]
     * @Return T
     * @Author hufei
     * @Date 2020/9/4 11:33
     */
    public static <T> T doExecute(String method, String url, Map<String, String> headers, Map<String, String> params, Object body, Class<T> tClass) throws RestClientException {
        if (null == body)
            body = "";
        HttpEntity<Object> entity = new HttpEntity<>(body, addHeader(headers));
        return restTemplate.exchange(addParams(url, params), HttpMethod.valueOf(method.toUpperCase()), entity, tClass).getBody();
    }

    /**
     * @Description 通用请求, 默认返回String
     * @Param [method, url, headers, params, body]
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/9/4 11:40
     */
    public static String doExecute(String method, String url, Map<String, String> headers, Map<String, String> params, Object body) throws RestClientException {
        if (null == body)
            body = "";
        HttpEntity<Object> entity = new HttpEntity<>(body, addHeader(headers));
        return restTemplate.exchange(addParams(url, params), HttpMethod.valueOf(method.toUpperCase()), entity, String.class).getBody();
    }

    /**
     * @Description 通用请求, 带路径变量
     * @Param [method, url, headers, params, body, uriVariables, tClass]
     * @Return T
     * @Author hufei
     * @Date 2020/9/4 11:42
     */
    public static <T> T doExecute(String method, String url, Map<String, String> headers, Map<String, String> params, Object body, Map<String, Object> uriVariables, Class<T> tClass) throws RestClientException {
        if (null == body)
            body = "";
        HttpEntity<Object> entity = new HttpEntity<>(body, addHeader(headers));
        return restTemplate.exchange(addParams(url, params), HttpMethod.valueOf(method.toUpperCase()), entity, tClass, uriVariables).getBody();
    }

    /**
     * @Description prometheus多个{}参数get请求
     * @Param [url, headers, map]
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/9/4 11:42
     */
    @Deprecated
    public static String doPlaceholderGets(String url, Map<String, String> headers, Map<String, Object> map) {
        HttpEntity<Map> entity = new HttpEntity<>(addHeader(headers));
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, map).getBody();
    }

    private static String addParams(String url, Map<String, String> param) {

        if (null == param || param.isEmpty())
            return url;

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private static HttpHeaders addHeader(Map<String, String> headers) {

        if (null == headers)
            return header;

        HttpHeaders tmp = new HttpHeaders();
        tmp.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        for (String key : headers.keySet()) {
            tmp.add(key, headers.get(key));
        }
        return tmp;
    }

}
