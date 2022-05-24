package com.learning.test;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.time.Duration;

/**
 * @ClassName RestClient
 * @Description TODO
 * @Author hufei
 * @Date 2022/5/24 13:46
 * @Version 1.0
 */
public class RestClient {

    public static RestHighLevelClient initRestClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.1.179:9200")
                .withConnectTimeout(Duration.ofSeconds(3))
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
