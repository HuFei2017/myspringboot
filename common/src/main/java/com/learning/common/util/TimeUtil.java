package com.learning.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @ClassName TimeUtil
 * @Description 时间工具类, 负责时间序列化及转换
 * @Author jiashudong
 * @Date 2020/5/18 16:15
 * @Version 1.0
 */
public class TimeUtil {

    private static final SimpleDateFormat df;

    static {
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    /**
     * @Description 获取当前时间前多少秒时间
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    @Deprecated
    public static String getCurrentTimeSkip(int amount) {
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.SECOND, amount);
        date = c.getTime();
        return df.format(date);
    }

    /**
     * @Description 获取当前时间
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    public static String getCurrentTime() {
        return df.format(new Date());
    }

    /**
     * @Description 时间戳转时间
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    public static String getTime(long timestamp) {
        return df.format(new Date(timestamp));
    }

    /**
     * @Description 获取当前时间指定跳转时间
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    public static String getSkipTime(int amount) {
        return getSkipTime(Calendar.SECOND, amount);
    }

    /**
     * @Description 获取当前时间指定跳转时间
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    public static String getSkipTime(int field, int amount) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(field, amount);
        return df.format(calendar.getTime());
    }

    /**
     * @Description 时间转时间戳
     * @Param []
     * @Return long
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    public static long getTimeStamp(String time) {
        try {
            return df.parse(time.replace("T", " ")).getTime();
        } catch (ParseException pe) {
            System.out.println("the date string " + time + " can not get timestamp");
        }
        return 0L;
    }

    /**
     * @Description Date转时间戳
     * @Param []
     * @Return long
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    public static long getTimeStamp(Date date) {
        return date.getTime();
    }

    /**
     * @Description 时间转Date
     * @Param []
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/8/11 17:04
     */
    public static Date getDate(String time) {
        try {
            return df.parse(refreshTime(time));
        } catch (ParseException pe) {
            System.out.println("the date string " + time + " can not get timestamp");
        }
        return new Date();
    }

    /**
     * @Description 时间戳转Date
     * @Param []
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/8/11 17:04
     */
    public static Date getDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * @Description Date转时间
     * @Param []
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/8/11 17:04
     */
    public static String getTime(Date date) {
        return df.format(date);
    }

    /**
     * @Description 时间字符串验证
     * @Param []
     * @Return java.lang.String
     * @Author hufei
     * @Date 2020/8/11 17:04
     */
    private static String refreshTime(String time) {
        return time.replace("T", " ");
    }

    /**
     * @Description 将时间转换成时间戳
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    @Deprecated
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts / 1000);
        return res;
    }

    /**
     * @Description 将时间戳转换成时间
     * @Param []
     * @Return String
     * @Author jiashudong
     * @Date 2020/5/6 15:32
     */
    @Deprecated
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        float t = Float.parseFloat(s) * 1000;
        Date date = new Date((long) t);
        res = simpleDateFormat.format(date);
        return res;
    }
}