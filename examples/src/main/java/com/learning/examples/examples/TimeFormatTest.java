package com.learning.examples.examples;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName TimeFormatTest
 * @Description TODO
 * @Author hufei
 * @Date 2022/7/21 19:35
 * @Version 1.0
 */
public class TimeFormatTest {
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder("2022");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("-1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("-1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append(" 1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append(":1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append(":1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
        str.append("1");
        System.out.println(str.toString());
        System.out.println(format(str.toString()));
    }

    private static String format(String str) {
        if (str.matches("^\\d{4}(-\\d{2}){2} \\d{2}(:\\d{2}){2}$")) {
            return str;
        }
        str = str.replace("T", " ").replace("Z", "");
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        boolean hasSubStr = str.contains(" ");
        String dateStr;
        String timeStr;
        if (hasSubStr) {
            String[] tmp = str.split(" ");
            dateStr = tmp[0];
            timeStr = tmp[1];
        } else {
            if (str.contains(":")) {
                dateStr = "";
                timeStr = str;
            } else {
                dateStr = str;
                timeStr = "";
            }
        }
        return doCheckTimeStr(false, dateStr, "-", 1) +
                " " +
                doCheckTimeStr(true, timeStr, ":", 0);
    }

    private static String doCheckTimeStr(boolean isTime, String str, String regex, int initValue) {
        if (str.isEmpty()) {
            return isTime ? "00:00:00" : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        StringBuilder builder = new StringBuilder();
        String[] tmp = str.split(regex);
        int len = tmp.length;
        for (int i = 0; i < 3; i++) {
            int var;
            if (i < len) {
                var = Integer.parseInt(tmp[i]);
            } else {
                var = initValue;
            }
            if (i == 0) {
                builder.append(String.format("%0" + (isTime ? 2 : 4) + "d", var));
            } else {
                builder.append(String.format("%02d", var));
            }
            if (i < 2) {
                builder.append(regex);
            }
        }
        return builder.toString();
    }
}
