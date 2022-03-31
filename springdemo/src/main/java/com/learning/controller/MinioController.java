package com.learning.controller;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @ClassName MinioController
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/21 17:04
 * @Version 1.0
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    private final MinioClient client;
    private static final Logger log = LoggerFactory.getLogger(MinioController.class);

    public MinioController() {
        this.client = MinioClient.builder()
                .endpoint("http://172.16.0.78:9006/")
                .endpoint("http://172.16.0.79:9006/")
                .endpoint("http://172.16.0.80:9006/")
                .credentials("minio", "minio123")
                .build();
    }

    @PostMapping("/test1")
    public void test1(@RequestBody byte[] data) {
        try {
            InputStream in = new ByteArrayInputStream(data);
            client.putObject(PutObjectArgs.builder()
                    .bucket("matlab-data")
                    .object("/testdir/test")
                    .stream(in, in.available(), -1)
                    .contentType("text/plain")
                    .build()
            );
            in.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
