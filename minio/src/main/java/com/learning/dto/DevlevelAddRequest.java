package com.learning.dto;

import lombok.Data;

/**
 * @ClassName DevlevelAddRequest
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 19:35
 * @Version 1.0
 */
@Data
public class DevlevelAddRequest {
    private String ordernumber;
    private String filename;
    private String creator;
}
