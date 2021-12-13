package com.learning.schema.input;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IndexInput {
    private List<String> idList;
    private List<AnalyseItem> itemList;
    private String sTime;
    private String eTime;
    private String oilType;
    private String material;
    private int pageSize=0;
    private int pageIndex=0;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public List<AnalyseItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<AnalyseItem> itemList) {
        this.itemList = itemList;
    }

    public String getsTime() {
        return ADD8(sTime);
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return ADD8(eTime);
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
