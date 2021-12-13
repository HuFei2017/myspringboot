package com.learning.annotation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OneChildController
 * @Description TODO
 * @Author hufei
 * @Date 2020/5/15 20:39
 * @Version 1.0
 */
@RestController
@Api(tags = "模块1")
@RequestMapping("/annotation")
@ConditionalOnProperty(prefix = "annotation", name = "switch", havingValue = "true")
public class AnnotationController {

    @GetMapping("/test")
    @ApiOperation("接口测试")
    public String test() {
        return "OK";
    }

}
