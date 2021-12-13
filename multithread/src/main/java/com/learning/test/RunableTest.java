package com.learning.test;

import com.learning.unit.MyRunable;

/**
 * @ClassName RunableTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 18:46
 * @Version 1.0
 * <p>
 * a.覆写Runnable接口实现多线程可以避免单继承局限
 * b.当子类实现Runnable接口，此时子类和Thread的代理模式（子类负责真是业务的操作，thread负责资源调度与线程创建辅助真实业务
 */
public class RunableTest {
    public static void main(String[] args) {
        MyRunable run = new MyRunable();
        //同时运行16个线程
        for (int i = 0; i < 16; i++) {
            Thread thread = new Thread(run, "线程" + i);
            thread.start();
        }
    }
}
