package com.learning.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName HttpUtil
 * @Description TODO
 * @Author hufei
 * @Date 2020/7/16 19:44
 * @Version 1.0
 */
public class ApacheHttpUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T doGet(String url, Map<String, String> params, Class<T> tClass) throws IOException, URISyntaxException {
        try (
                //创建HttpClient对象
                CloseableHttpClient client = HttpClients.createDefault()
        ) {

            //创建URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);

            //创建HttpGet
            HttpGet httpGet = new HttpGet(addParams(uriBuilder, params).build());

            setHeaders(httpGet);

            //请求服务
            return parseJson(getResponse(client.execute(httpGet)), tClass);

        }
    }

    public static <T> T doPost(String url, Map<String, String> params, Object body, Class<T> tClass) throws IOException, URISyntaxException {
        try (
                //创建HttpClient对象
                CloseableHttpClient client = HttpClients.createDefault()
        ) {

            //创建URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);

            //创建HttpPost
            HttpPost httpPost = new HttpPost(addParams(uriBuilder, params).build());

            setHeaders(httpPost);

            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(15000).setConnectionRequestTimeout(15000)
                    .setSocketTimeout(15000).build();
            httpPost.setConfig(config);

            if (!Objects.isNull(body))
                httpPost.setEntity(new StringEntity(mapper.writeValueAsString(body), StandardCharsets.UTF_8));

            //请求服务
            return parseJson(getResponse(client.execute(httpPost)), tClass);

        }
    }

    public static <T> T doPut(String url, Map<String, String> params, Object body, Class<T> tClass) throws IOException, URISyntaxException {
        try (
                //创建HttpClient对象
                CloseableHttpClient client = HttpClients.createDefault()
        ) {

            //创建URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);

            //创建HttpPut
            HttpPut httpPut = new HttpPut(addParams(uriBuilder, params).build());

            setHeaders(httpPut);

            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(15000).setConnectionRequestTimeout(15000)
                    .setSocketTimeout(15000).build();
            httpPut.setConfig(config);

            if (!Objects.isNull(body))
                httpPut.setEntity(new StringEntity(mapper.writeValueAsString(body), StandardCharsets.UTF_8));

            //请求服务
            return parseJson(getResponse(client.execute(httpPut)), tClass);

        }
    }

    public static Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private static void setHeaders(HttpRequestBase request) {
        //设置请求头部编码
        request.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
        //设置返回编码
        request.setHeader(new BasicHeader("Accept", "*/*;charset=utf-8"));
    }

    private static URIBuilder addParams(URIBuilder uriBuilder, Map<String, String> params) {
        if (!Objects.isNull(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return uriBuilder;
    }

    private static String getResponse(CloseableHttpResponse response) throws IOException {

        String result = "";

        if (Objects.isNull(response))
            return "";

        //获取响应码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            //第一种方法：使用EntityUtils工具类
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            //关闭流
            EntityUtils.consume(entity);
            response.close();

            /*
            //第二种方法：使用流（使用流的时候注意最后要关闭流）
            is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
            */

        }
        return result;
    }

    private static <T> T parseJson(String str, Class<T> tClass) {
        try {
            return mapper.readValue(str, tClass);
        } catch (JsonProcessingException ex) {
            System.out.println("the string " + str + " can not be deserialize successfully.");
        }
        return null;
    }

}