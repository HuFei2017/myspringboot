package com.learning.common.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName Main
 * @Description TODO
 * @Author hufei
 * @Date 2020/8/18 17:35
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(15000);
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setReadTimeout(15000);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        //初始化header
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
//        header.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IlpveXBXenU5dy0tSHJxU3ZjdUNqRkEiLCJ0eXAiOiJhdCtqd3QifQ.eyJuYmYiOjE1OTc2NTQ1MjgsImV4cCI6MTYyOTE5MDUyOCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4yNDA6OTk5OS9pZGVudGl0eXNlcnZlciIsImF1ZCI6ImVwbSIsImNsaWVudF9pZCI6ImVwbSIsInN1YiI6IjA0YWZiODEyLTA0NGEtZTc0Yi02NTliLTM5ZjcwZDU2YTNhZSIsImF1dGhfdGltZSI6MTU5NzY1NDUyOCwiaWRwIjoibG9jYWwiLCJyb2xlIjoiYWRtaW4iLCJuYW1lIjoiYWRtaW4iLCJlbWFpbCI6ImFkbWluQGFicC5pbyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwic2NvcGUiOlsiZXBtIl0sImFtciI6WyJwd2QiXX0.od78kqdutXwcSfDsEG8c671FX83tgH12ncGwUH11mbEOGO6zzE-qSngey6rMLNHnUaODVzAt4nawImBtK-fQEjgvpDeGy4FgtuFL4vT1nmKW3iCaxJ6h7C4qeP2WQM_mmyjYEAivrJkMCDw4VCXOzdzuTaEF79TbjUrhMKbROB0NmL8c2TxmBXIvQPR9Mm5F_QwfJmKKfeBlul1ZoGINWMUTukUbVEPeivUf6UNilan1J-nTz1eqCkSza-6fcwHUxhChOnypilFhAK1gY3ZyH8Wi-947JKfT6ybGBCRFt7XIe7AGmKvlFBf0-Gk8svCXJAorS5hdV2tBHhzoGpgpwA");
//        Map<String, String> map = new HashMap<>();
//        map.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IlpveXBXenU5dy0tSHJxU3ZjdUNqRkEiLCJ0eXAiOiJhdCtqd3QifQ.eyJuYmYiOjE1OTc2NTQ1MjgsImV4cCI6MTYyOTE5MDUyOCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4yNDA6OTk5OS9pZGVudGl0eXNlcnZlciIsImF1ZCI6ImVwbSIsImNsaWVudF9pZCI6ImVwbSIsInN1YiI6IjA0YWZiODEyLTA0NGEtZTc0Yi02NTliLTM5ZjcwZDU2YTNhZSIsImF1dGhfdGltZSI6MTU5NzY1NDUyOCwiaWRwIjoibG9jYWwiLCJyb2xlIjoiYWRtaW4iLCJuYW1lIjoiYWRtaW4iLCJlbWFpbCI6ImFkbWluQGFicC5pbyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwic2NvcGUiOlsiZXBtIl0sImFtciI6WyJwd2QiXX0.od78kqdutXwcSfDsEG8c671FX83tgH12ncGwUH11mbEOGO6zzE-qSngey6rMLNHnUaODVzAt4nawImBtK-fQEjgvpDeGy4FgtuFL4vT1nmKW3iCaxJ6h7C4qeP2WQM_mmyjYEAivrJkMCDw4VCXOzdzuTaEF79TbjUrhMKbROB0NmL8c2TxmBXIvQPR9Mm5F_QwfJmKKfeBlul1ZoGINWMUTukUbVEPeivUf6UNilan1J-nTz1eqCkSza-6fcwHUxhChOnypilFhAK1gY3ZyH8Wi-947JKfT6ybGBCRFt7XIe7AGmKvlFBf0-Gk8svCXJAorS5hdV2tBHhzoGpgpwA");
//        String k = restTemplate.getForObject(addParams("http://192.168.1.240:3100/api/User/GetUser", map), String.class);
//        System.out.println(k);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IlpveXBXenU5dy0tSHJxU3ZjdUNqRkEiLCJ0eXAiOiJhdCtqd3QifQ.eyJuYmYiOjE1OTc2NTQ1MjgsImV4cCI6MTYyOTE5MDUyOCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4yNDA6OTk5OS9pZGVudGl0eXNlcnZlciIsImF1ZCI6ImVwbSIsImNsaWVudF9pZCI6ImVwbSIsInN1YiI6IjA0YWZiODEyLTA0NGEtZTc0Yi02NTliLTM5ZjcwZDU2YTNhZSIsImF1dGhfdGltZSI6MTU5NzY1NDUyOCwiaWRwIjoibG9jYWwiLCJyb2xlIjoiYWRtaW4iLCJuYW1lIjoiYWRtaW4iLCJlbWFpbCI6ImFkbWluQGFicC5pbyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwic2NvcGUiOlsiZXBtIl0sImFtciI6WyJwd2QiXX0.od78kqdutXwcSfDsEG8c671FX83tgH12ncGwUH11mbEOGO6zzE-qSngey6rMLNHnUaODVzAt4nawImBtK-fQEjgvpDeGy4FgtuFL4vT1nmKW3iCaxJ6h7C4qeP2WQM_mmyjYEAivrJkMCDw4VCXOzdzuTaEF79TbjUrhMKbROB0NmL8c2TxmBXIvQPR9Mm5F_QwfJmKKfeBlul1ZoGINWMUTukUbVEPeivUf6UNilan1J-nTz1eqCkSza-6fcwHUxhChOnypilFhAK1gY3ZyH8Wi-947JKfT6ybGBCRFt7XIe7AGmKvlFBf0-Gk8svCXJAorS5hdV2tBHhzoGpgpwA");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange("http://192.168.1.240:3100/api/User/GetUser", HttpMethod.GET, requestEntity, String.class);
        String sttr = response.getBody();
        System.out.println(sttr);
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
