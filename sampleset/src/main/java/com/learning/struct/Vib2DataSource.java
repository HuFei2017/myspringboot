package com.learning.struct;

import com.learning.enums.DataSourceTableOrderType;
import com.learning.enums.DataSourceType;
import com.learning.rules.ColumnRule;
import com.learning.rules.DataSourceRule;
import com.learning.rules.DataSourceTypeRule;
import com.learning.rules.MultiLineGroupRule;
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
@MultiLineGroupRule(groupBy = {"x","y","extra","comment"})
public class Vib2DataSource {
    //uuid
    @ColumnRule(key = true, primitiveType = "UUID")
    private String id;
    //趋势类型
    @ColumnRule(key = true, primitiveType = "INT")
    private int dataType;
    //横轴坐标点
    @ColumnRule(key = true, primitiveType = "BIGINT", orderType = DataSourceTableOrderType.ASC)
    private long x;
    //横轴单位
    @ColumnRule(primitiveType = "TEXT")
    private String xUnit;
    //纵轴坐标点
    @ColumnRule(primitiveType = "FLOAT")
    private float y;
    //纵轴单位
    @ColumnRule(primitiveType = "TEXT")
    private String yUnit;
    //趋势点对应的其他数据资源定位符
    @ColumnRule(primitiveType = "TEXT")
    private String extra;
    //趋势点描述
    @ColumnRule(primitiveType = "TEXT")
    private String comment;
    //趋势名称
    @ColumnRule(primitiveType = "TEXT")
    private String name;
    //趋势属性信息
    @ColumnRule(primitiveType = "BLOB")
    private Map props;
}
