package com.learning.bootstrap.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author hufei
 * @Date 2020/6/24 14:56
 * @Version 1.0
 */
@Configuration
//@EnableSwagger2
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))//扫描的包路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger测试")//文档说明
                .description("SpringBoot整合Swagger，详细信息......")
                .version("1.0.0")//文档版本说明
                .build();
    }
}
