package com.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制类
 *
 * @ClassName TestController
 * @Description TODO
 * @Author hufei
 * @Date 2022/1/4 17:18
 * @Version 1.0
 */
@RestController
public class TestController {

    /**
     * XX方法
     *
     * @param param 参数
     * @return 结果
     */
    @GetMapping("/test")
    public String test(String param) {
        return param;
    }

}
