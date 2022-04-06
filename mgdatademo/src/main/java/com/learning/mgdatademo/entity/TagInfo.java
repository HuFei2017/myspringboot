package com.learning.mgdatademo.entity;

import lombok.Data;

/**
 * @ClassName TagInfo
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/10 10:20
 * @Version 1.0
 */
@Data
public class TagInfo {
    private String id;
    private String name;
    private String reName;
    private String unit;
    private String property;
    private String function;
    private String lastTime;
    private String startTime;
    private String endTime;
    private String data;
}
