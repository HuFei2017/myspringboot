package com.learning.examples.examples;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName PropertyConfiguration
 * @Description TODO
 * @Author hufei
 * @Date 2020/10/23 21:51
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "config")
public class PropertyConfiguration {

    private String name;

    private PropertyConfigurationEntity entity;

}
