package com.learning.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/16 15:50
 * @Version 1.0
 */
@RestController
public class TestController {

    @PostMapping("/test1")
    public String test1(@RequestBody byte[] data) {
        return "file length：" + data.length;
    }

    @PostMapping("/test2")
    public String test2(@RequestBody MultipartFile[] files) {
        return "file number：" + files.length;
    }

    @PostMapping(value = "/test3", consumes = "application/octet-stream")
    public String test3(@RequestBody byte[] data) {
        return "file length：" + data.length;
    }

}
