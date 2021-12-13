package com.learning.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @ClassName TestInstance
 * @Description TODO
 * @Author hufei
 * @Date 2021/10/27 8:12
 * @Version 1.0
 */
public class TestInstance {

    private Logger log = LoggerFactory.getLogger(TestInstance.class);

    public String getValue() throws Exception{
        log.info("发起一次请求");
        Thread.sleep(10000);
        log.info("执行结束,返回结果");
        return UUID.randomUUID().toString();
    }

}
