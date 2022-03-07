package com.learning.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ElasticConfiguration
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 18:50
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticConfiguration {
    private String connection;
}
