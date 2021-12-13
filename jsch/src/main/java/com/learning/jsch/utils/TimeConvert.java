package com.learning.jsch.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName TimeConvert
 * @Description TODO
 * @Author hufei
 * @Date 2020/11/4 15:58
 * @Version 1.0
 */
public class TimeConvert {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] formarts =
            {
                    "yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy/MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd'T'HH:mm:ss"
            };

    public static Timestamp convertTimestamp(String time) {
        if (time == null || time.isEmpty())
            //return Timestamp.valueOf(LocalDateTime.now());
            return null;
        if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseTimestamp(time, formarts[4]);
        } else if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseTimestamp(time, formarts[3]);
        } else if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")) {
            return parseTimestamp(time, formarts[2]);
        } else if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2}")) {
            return parseTimestamp(time, formarts[1]);
        } else if (time.matches("^\\d{4}-\\d{1,2}")) {
            return parseTimestamp(time, formarts[0]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseTimestamp(time, formarts[9]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseTimestamp(time, formarts[8]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}")) {
            return parseTimestamp(time, formarts[7]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2}")) {
            return parseTimestamp(time, formarts[6]);
        } else if (time.matches("^\\d{4}/\\d{1,2}")) {
            return parseTimestamp(time, formarts[5]);
        } else
            return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String convertString(String time) {
        if (time == null || time.isEmpty())
            //return sdf.format(new Date());
            return null;
        if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseString(time, formarts[4]);
        } else if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseString(time, formarts[3]);
        } else if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")) {
            return parseString(time, formarts[2]);
        } else if (time.matches("^\\d{4}-\\d{1,2}-\\d{1,2}")) {
            return parseString(time, formarts[1]);
        } else if (time.matches("^\\d{4}-\\d{1,2}")) {
            return parseString(time, formarts[0]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseString(time, formarts[9]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.?[0-9]*")) {
            return parseString(time, formarts[8]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}")) {
            return parseString(time, formarts[7]);
        } else if (time.matches("^\\d{4}/\\d{1,2}/\\d{1,2}")) {
            return parseString(time, formarts[6]);
        } else if (time.matches("^\\d{4}/\\d{1,2}")) {
            return parseString(time, formarts[5]);
        } else
            return sdf.format(new Date());
    }

    private static Timestamp parseTimestamp(String time, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return Timestamp.valueOf(sdf.format(dateFormat.parse(time)));
        } catch (ParseException ex) {
            return null;
        }
    }

    private static String parseString(String time, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return sdf.format(dateFormat.parse(time));
        } catch (ParseException ex) {
            return null;
        }
    }
}
