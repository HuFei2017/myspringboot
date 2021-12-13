package com.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ConsulServer2Controller
 * @Description TODO
 * @Author hufei
 * @Date 2021/6/9 15:51
 * @Version 1.0
 */
@RestController
public class ConsulServer2Controller {

    @GetMapping("/hello")
    public String hello() {
        return "hello consul 2";
    }

}
