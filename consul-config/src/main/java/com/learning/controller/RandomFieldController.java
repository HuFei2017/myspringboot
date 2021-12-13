package com.learning.controller;

import com.learning.configuration.CustomConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RandomFieldController
 * @Description TODO
 * @Author hufei
 * @Date 2021/6/9 16:32
 * @Version 1.0
 */
@RestController
public class RandomFieldController {

    private CustomConfiguration configuration;

    public RandomFieldController(CustomConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/field")
    public Object getFieldValue(String index) {
        if (index.equals("name"))
            return configuration.getName();
        else
            return configuration.getPort();
    }

}
