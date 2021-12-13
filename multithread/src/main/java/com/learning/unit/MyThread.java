package com.learning.unit;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 16:55
 * @Version 1.0
 */
public class MyThread extends Thread {

    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(name + "正在运行...");
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread() + ":" + i);
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
        System.out.println(name + "运行完成...");
    }

}
