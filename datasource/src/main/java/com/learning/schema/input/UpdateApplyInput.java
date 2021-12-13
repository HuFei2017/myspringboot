package com.learning.schema.input;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateApplyInput {
    private AnalyseItem item;
    private String sampleTime;
    private String descomment;

    public AnalyseItem getItem() {
        return item;
    }

    public void setItem(AnalyseItem item) {
        this.item = item;
    }

    public String getSampleTime() {
        return ADD8(sampleTime);
    }

    public void setSampleTime(String sampleTime) {
        this.sampleTime = sampleTime;
    }

    public String getDescomment() {
        return descomment;
    }

    public void setDescomment(String descomment) {
        this.descomment = descomment;
    }

    private static String ADD8(String time) {
        try {
            Date d = null;
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sd.parse(time);
            long rightTime = (long) (d.getTime() + 8 * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
            return sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
        }catch (Exception ex){
            return time;
        }
    }
}
