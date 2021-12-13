package com.learning.test;

import com.learning.unit.MyHttpThread;

/**
 * @ClassName HttpTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/10/22 17:13
 * @Version 1.0
 */
public class HttpTest {

    public static void main(String[] args) {
        //同时运行16个线程
        for (int i = 0; i < 16; i++) {
            MyHttpThread thread = new MyHttpThread("线程" + i);
            thread.start();
        }
    }

}
