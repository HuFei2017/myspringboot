package com.learning.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CustomConfiguration
 * @Description TODO
 * @Author hufei
 * @Date 2021/6/9 16:37
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomConfiguration {
    private String name;
    private int port;
}
