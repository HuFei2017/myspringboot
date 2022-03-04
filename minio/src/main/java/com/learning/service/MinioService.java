package com.learning.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @ClassName MinioService
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 14:17
 * @Version 1.0
 */
@Service
public class MinioService {

    private MinioClient minioClient = null;

    public String addDevlevelData(byte[] data) {

        MinioClient client = getClient();

        if (null != client) {
            try (InputStream in = new ByteArrayInputStream(data)) {
                System.out.println("开始传输文件：" + System.currentTimeMillis());
                minioClient.putObject(PutObjectArgs.builder()
                                .bucket("matlab-data")
                                .object("test/devlevel/try")
                                .stream(in, in.available(), -1)
//                                .stream(in, -1, 10485760)
                                .contentType("text/plain")
                                .build()
                );
                System.out.println("传输文件完成：" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    private MinioClient getClient() {
        if (null == minioClient) {
            this.minioClient = MinioClient.builder()
//                    .endpoint("http://172.16.0.78:9006/")
                    .endpoint("http://127.0.0.1:9000/")
//                    .credentials("minio", "minio123")
                    .credentials("minioadmin", "minioadmin")
                    .build();
        }
        return minioClient;
    }

}
