package com.learning.examples.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author hufei
 * @Date 2020/10/23 21:55
 * @Version 1.0
 */
@RestController
public class TestController {

//    @Value("${config}")
//    private PropertyConfigurationEntity entity;
//
//    private PropertyConfiguration configuration;
//
//    public TestController(PropertyConfiguration configuration){
//        this.configuration=configuration;
//        System.out.println(entity);
//    }

    @PostMapping("/upload")
    public String test(@RequestBody byte[] data){

        String id = UUID.randomUUID().toString();

        if(data.length>0)
            System.out.println("接收到数据，返回："+id);
        else
            System.out.println("没有接收到数据，返回："+id);

        return id;
    }

}
