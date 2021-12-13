package com.learning.controller;

import com.learning.configuration.CustomConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StaticFieldController
 * @Description TODO
 * @Author hufei
 * @Date 2021/6/9 16:32
 * @Version 1.0
 */
@RestController
public class StaticFieldController {

    private final String name;
    private final int port;

    public StaticFieldController(CustomConfiguration configuration) {
        this.name = configuration.getName();
        this.port = configuration.getPort();
    }

    @GetMapping("/static")
    public Object getFieldValue(String index) {
        if (index.equals("name"))
            return name;
        else
            return port;
    }

}