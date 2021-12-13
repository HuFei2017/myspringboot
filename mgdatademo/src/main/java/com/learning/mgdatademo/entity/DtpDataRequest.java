package com.learning.mgdatademo.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DtpDataRequest
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/16 10:40
 * @Version 1.0
 */
@Data
public class DtpDataRequest {
    private List<String> tagList;
    private String startTime;
    private String endTime;
}
