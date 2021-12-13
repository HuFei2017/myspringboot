package com.learning.struct;

import com.learning.enums.DataSourceType;
import com.learning.rules.ColumnRule;
import com.learning.rules.DataSourceRule;
import com.learning.rules.DataSourceTypeRule;
import com.learning.rules.PropertyRestrictRule;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName VibDataSource
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 16:30
 * @Version 1.0
 */
@Data
@DataSourceTypeRule(type = DataSourceType.Cassandra)
@DataSourceRule(namespace = "Cassandra:Keyspace:TableName")
public class VibDataSource {
    //趋势序号
    @PropertyRestrictRule(unique = true)
    @ColumnRule(key = true, type = "UUID")
    private String id;
    //趋势名称
    @ColumnRule(type = "TEXT")
    private String name;
    //趋势时间轴
    @ColumnRule(type = "BLOB")
    private Long[] x;
    //时间单位
    @ColumnRule(type = "TEXT")
    private String xUnit;
    //趋势数值轴
    @ColumnRule(type = "BLOB")
    private Float[] y;
    //数值单位
    @ColumnRule(type = "TEXT")
    private String yUnit;
    //波形 resourcePath 集合
    @ColumnRule(type = "BLOB")
    private String[] extra;
    //每个点的备注说明
    @ColumnRule(type = "BLOB")
    private Map[] comment;
    //趋势属性信息
    @ColumnRule(type = "BLOB")
    private Map props;
}
