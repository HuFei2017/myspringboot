package com.learning.unit;

import com.learning.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyHttpThread
 * @Description TODO
 * @Author hufei
 * @Date 2021/10/22 17:14
 * @Version 1.0
 */
public class MyHttpThread extends Thread {

    private String name;

    public MyHttpThread(String name) {
        this.name = name;
    }

    public void run() {
        Map<String,String> param=new HashMap<>();
        param.put("name",name);
        String name= HttpUtil.doGet("http://192.168.1.210:8080/test",param);
        System.out.println(name);
    }

}
