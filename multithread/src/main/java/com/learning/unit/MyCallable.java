package com.learning.unit;

import java.util.concurrent.Callable;

/**
 * @ClassName MyCallable
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 18:49
 * @Version 1.0
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread() + ":" + i);
            Thread.sleep(1000);
        }
        return "OK";
    }
}
