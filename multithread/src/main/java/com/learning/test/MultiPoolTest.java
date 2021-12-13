package com.learning.test;

import java.util.concurrent.*;

/**
 * @ClassName MultiPoolTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 19:05
 * @Version 1.0
 * <p>
 * 使用于为了满足资源管理需求而需要限制当前线程数量的场合。使用于负载比较重的服务器
 */
public class MultiPoolTest {
    public static void main(String[] args) throws Exception {
        //创建16个线程的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Callable<String> callable = () -> {
            System.out.println("线程" + Thread.currentThread().getName());
            //使用sleep模拟耗时长的任务进行中
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
            }
            return Thread.currentThread().getName();
        };

        FutureTask[] futureTask = new FutureTask[16];
        for (int i = 0; i < 16; i++) {
            futureTask[i] = new FutureTask<>(callable);
            executorService.submit(futureTask[i]);
        }
        for (int i = 0; i < 16; i++) {
            String threadName = futureTask[i].get(10, TimeUnit.SECONDS).toString();
            System.out.println(threadName);
        }
        //关闭线程池
        executorService.shutdown();
    }
}
