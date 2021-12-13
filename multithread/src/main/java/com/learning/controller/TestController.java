package com.learning.controller;

import com.learning.entity.TestInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author hufei
 * @Date 2021/10/27 8:09
 * @Version 1.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() throws Exception{
        TestInstance instance = (TestInstance) Class.forName("com.learning.entity.TestInstance").getDeclaredConstructor().newInstance();
        return instance.getValue();
    }

}
