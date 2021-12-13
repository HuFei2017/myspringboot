package com.learning.test;

import com.learning.unit.MyThread;

/**
 * @ClassName MultiThreadTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 18:39
 * @Version 1.0
 * <p>
 * run()为线程类的核心方法，相当于主线程的main方法，是每个线程的入口
 * a.一个线程调用两次start()方法将会抛出线程状态异常，也就是start()只可以被调用一次 
 * b.native声明的方法只有方法名，没有方法体。是本地方法，不是抽象方法，而是调用c语言方法，registerNative()方法包含了所有与线程相关的操作系统方法
 * c.run()方法是由jvm创建完本地操作系统级线程后回调的方法，不可以手动调用（否则就是普通方法）
 */
public class ThreadTest {
    public static void main(String[] args) {
        //同时运行16个线程
        for (int i = 0; i < 16; i++) {
            MyThread thread = new MyThread("线程" + i);
            thread.start();
        }
    }
}
