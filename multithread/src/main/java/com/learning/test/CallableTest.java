package com.learning.test;

import com.learning.unit.MyCallable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @ClassName CallableTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/23 18:50
 * @Version 1.0
 * <p>
 * a.核心方法叫call()方法，有返回值
 * b.有返回值
 */
public class CallableTest {
    public static void main(String[] args) throws Exception {
        Callable<String> callable = new MyCallable();
        FutureTask<String> run = new FutureTask<>(callable);
        //实际上只会运行一个线程,并不会运行16个
        for (int i = 0; i < 16; i++) {
            new Thread(run, "线程" + i).start();
        }
        System.out.println(run.get());
    }
}
