package com.learning.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CashPollTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 19:14
 * @Version 1.0
 * <p>
 * 当提交任务速度高于线程池中任务处理速度时，缓存线程池会不断的创建线程
 * 适用于提交短期的异步小程序，以及负载较轻的服务器
 */
public class CashPollTest {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newCachedThreadPool();
        for (int i = 0; i < 16; i++) {
            ex.submit(() -> {
                //延时5秒
                for (int j = 0; j < 1; j++) {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ignored) {
                    }
                }
            });
        }
        ex.shutdown();
    }
}
