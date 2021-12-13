package com.learning.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamplesApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExamplesApplication.class, args);

        //linux执行python脚本并捕获返回结果
//        SimulationTest test = new SimulationTest();
//        test.startNow();

        //JDBC测试
//        JDBCTest test = new JDBCTest();
//        test.connect();
//        test.testBatchInsert_3();
//        test.close();
    }

}
