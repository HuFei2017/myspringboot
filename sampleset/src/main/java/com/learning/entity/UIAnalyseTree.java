package com.learning.entity;

import lombok.Data;

/**
 * @ClassName UIAnalyseTree
 * @Description UI页面展示的树结构, 支持多数样本集, 支持多行数据, 支持聚合
 * @Author hufei
 * @Date 2021/11/29 14:18
 * @Version 1.0
 */
@Data
public class UIAnalyseTree extends AnalyseNodeTree {
    //节点隐藏信息
    private UIAnalyseTreeNodeInfo _info;
    private UIAnalyseTreeNodeLayout _layout;
}
