package com.learning.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

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
    public String test2(@RequestParam("file") MultipartFile[] files) throws IOException {
        return "file number：" + files[0].getBytes().length;
    }

    @PostMapping(value = "/test3", consumes = "application/octet-stream")
    public String test3(@RequestBody byte[] data) {
        return "file length：" + data.length;
    }

    @PostMapping(value = "/test4")
    public String test4(HttpServletRequest request) {
        return "success";
    }

    @GetMapping("/test5")
    public byte[] test5(@RequestParam("path") String path) throws IOException {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return FileUtils.readFileToByteArray(file);
        }
        return new byte[0];
    }

}
