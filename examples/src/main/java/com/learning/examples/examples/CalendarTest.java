package com.learning.examples.examples;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ClassName CalendarTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/10/29 12:05
 * @Version 1.0
 */
public class CalendarTest {
    public static void main(String[] args){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        Calendar before = Calendar.getInstance();
        before.add(Calendar.HOUR,-24);
        System.out.println(df.format(now.getTime()));
        System.out.println(df.format(before.getTime()));
    }
}
