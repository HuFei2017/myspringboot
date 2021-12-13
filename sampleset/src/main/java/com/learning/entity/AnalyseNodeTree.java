package com.learning.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AnalyseNodeTree {
    //业务节点ID
    private String id;
    //业务节点名称
    private String title;
    //节点属性信息
    private Map props;
    //子节点
    private AnalyseNodeTree[] children;
    //节点绑定的数据
    private AnalyseNodeTreeData[] data;
    //是否为叶子结点
    @JsonProperty("isLeaf")
    private boolean isLeaf;
}
