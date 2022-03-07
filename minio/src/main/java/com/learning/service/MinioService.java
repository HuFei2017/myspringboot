package com.learning.service;

import com.learning.configuration.MinioConfiguration;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    private final MinioConfiguration configuration;
    private final Logger log = LoggerFactory.getLogger(MinioService.class);

    MinioService(MinioConfiguration configuration) {
        this.configuration = configuration;
        getClient();
    }

    void uploadFileToMinio(byte[] data, String filePath) {

        MinioClient client = getClient();

        if (null != client) {
            try (InputStream in = new ByteArrayInputStream(data)) {
                long start = System.currentTimeMillis();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(configuration.getBucketName())
                        .object(filePath)
                        .stream(in, in.available(), -1)
                        .contentType("text/plain")
                        .build()
                );
                long end = System.currentTimeMillis();
                log.info("文件传输耗时：" + (end - start) + "ms");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    byte[] getByteContent(String filepath) {
        try (InputStream in = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(configuration.getBucketName())
                        .object(filepath)
                        .build()
        );
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            int bsize;
            while ((bsize = in.read()) != -1) {
                os.write(bsize);
            }
            return os.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new byte[0];
    }

    private MinioClient getClient() {
        if (null == minioClient) {
            if (null != configuration) {
                this.minioClient = MinioClient.builder()
                        .endpoint(configuration.getPoint())
                        .credentials(configuration.getAccessKey(), configuration.getSecretKey())
                        .build();
            }
        }
        return minioClient;
    }

}
