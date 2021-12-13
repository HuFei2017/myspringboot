package com.learning.mgdatademo.entity;

import lombok.Data;

/**
 * @ClassName GzipDtpInfo
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/10 10:23
 * @Version 1.0
 */
@Data
public class GzipDtpInfo {
    private String startTime = "2021-10-01 01:33:33";
    private String endTime = "2021-11-10 01:33:36";
    private String path;
    private String dfsPath;
}
