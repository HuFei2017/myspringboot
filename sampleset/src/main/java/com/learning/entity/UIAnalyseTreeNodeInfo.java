package com.learning.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName UIAnalyseTree
 * @Description UI页面展示的树结构, 支持多数样本集, 支持多行数据, 支持聚合
 * @Author hufei
 * @Date 2021/11/29 14:18
 * @Version 1.0
 */
@Data
public class UIAnalyseTreeNodeInfo {
    //每个节点的唯一主键
    @JsonProperty("key")
    private String _key;
    //节点从属的样本集ID
    private int _sid;
    //当前节点在样本集数据中的行定位符
    private String _res;
    //节点id遍历路由
    private List<String> _id;
    //节点title遍历路由
    private List<String> _title;
    //以上所有信息Base64编码
    private String _path;
}
