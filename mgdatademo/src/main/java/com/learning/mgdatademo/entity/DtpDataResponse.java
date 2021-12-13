package com.learning.mgdatademo.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DtpDataResponse
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/16 10:41
 * @Version 1.0
 */
@Data
public class DtpDataResponse {
    private String id;
    private List<byte[]> data;
}
