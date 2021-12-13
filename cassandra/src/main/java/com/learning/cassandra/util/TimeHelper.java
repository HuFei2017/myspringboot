package com.learning.cassandra.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ClassName TimeUtil
 * @Description TODO
 * @Author hufei
 * @Date 2021/4/1 19:30
 * @Version 1.0
 */
public class TimeHelper {

    private final SimpleDateFormat df;

    public TimeHelper() {
        this.df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public int compare(String time1, String time2) {

        if (null == time1 || null == time2 || time1.isEmpty() || time2.isEmpty())
            return 0;

        try {
            Date date1 = df.parse(time1);
            Date date2 = df.parse(time2);

            if (date1.before(date2))
                return -1;
            else if (date1.after(date2))
                return 1;
        } catch (ParseException ignored) {
        }

        return 0;
    }

    public String addTime(String time, int minute) {

        if (null != time) {
            try {
                Date date = df.parse(time);
                date.setTime(date.getTime() + minute * 60 * 1000);
                return df.format(date);
            } catch (ParseException ignored) {
            }
        }

        return df.format(new Date());
    }

}
