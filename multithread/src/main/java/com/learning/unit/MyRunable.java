package com.learning.unit;

/**
 * @ClassName MyRunable
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 18:45
 * @Version 1.0
 */
public class MyRunable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread() + ":" + i);
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }
}
