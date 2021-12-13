package com.learning.cassandra.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtils {

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
        header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
    }

    public static <T> T doGet(String url, Map<String, String> params, Class<T> tClass) throws RestClientException {
        return restTemplate.getForObject(addParams(url, params), tClass);
    }

    public static String doGet(String url, Map<String, String> params) throws RestClientException {
        return restTemplate.getForObject(addParams(url, params), String.class);
    }

    public static <T> T doPost(String url, Map<String, String> params, Object body, Class<T> tClass) throws RestClientException {
        return restTemplate.postForObject(addParams(url, params), body, tClass);
    }

    public static String doPost(String url, Map<String, String> params, Object body) throws RestClientException {
        return restTemplate.postForObject(addParams(url, params), body, String.class);
    }

    public static void doPut(String url, Map<String, String> params, Object body) throws RestClientException {
        restTemplate.put(addParams(url, params), body);
    }

    public static void doDelete(String url, Map<String, String> params) throws RestClientException {
        restTemplate.delete(addParams(url, params));
    }

    public static <T> T doExecute(String method, String url, Map<String, String> params, Object body, Class<T> tClass) throws RestClientException {
        if (null == body)
            body = "";
        HttpEntity<Object> entity = new HttpEntity<>(body, header);
        return restTemplate.exchange(addParams(url, params), HttpMethod.valueOf(method.toUpperCase()), entity, tClass).getBody();
    }

    public static String doExecute(String method, String url, Map<String, String> params, Object body) throws RestClientException {
        if (null == body)
            body = "";
        HttpEntity<Object> entity = new HttpEntity<>(body, header);
        return restTemplate.exchange(addParams(url, params), HttpMethod.valueOf(method.toUpperCase()), entity, String.class).getBody();
    }

    public static <T> T doExecute(String method, String url, Map<String, String> params, Object body, Map<String, Object> uriVariables, Class<T> tClass) throws RestClientException {
        if (null == body)
            body = "";
        HttpEntity<Object> entity = new HttpEntity<>(body, header);
        return restTemplate.exchange(addParams(url, params), HttpMethod.valueOf(method.toUpperCase()), entity, tClass, uriVariables).getBody();
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
}
