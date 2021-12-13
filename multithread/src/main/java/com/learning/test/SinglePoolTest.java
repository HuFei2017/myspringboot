package com.learning.test;

import java.util.concurrent.*;

/**
 * @ClassName SinglePoolTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 19:02
 * @Version 1.0
 * <p>
 * 需要保证顺序执行各个任务的场景
 */
public class SinglePoolTest {
    public static void main(String[] args) throws Exception {
        //方式1
        Callable<String> callable = () -> {
            //使用sleep模拟耗时长的任务进行中
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
            }
            return "OK";
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        String result = futureTask.get(12, TimeUnit.SECONDS);
        System.out.println(result);

        //方式2
        ExecutorService ex = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 8; i++) {
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
