package com.learning.entity;

import com.learning.enums.AnalyseFormat;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName AnalyseNodeTree
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:13
 * @Version 1.0
 */
@Data
public class AnalyseNodeTreeData {
    private String name;
    private String path;
    private AnalyseFormat format;
    private Map props;
}
