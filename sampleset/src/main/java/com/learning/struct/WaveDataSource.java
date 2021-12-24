package com.learning.struct;

import com.learning.enums.DataSourceType;
import com.learning.enums.HttpMethod;
import com.learning.enums.HttpResultType;
import com.learning.rules.*;
import lombok.Data;

/**
 * @ClassName WaveDataSource
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 16:33
 * @Version 1.0
 */
@Data
@DataSourceTypeRule(type = DataSourceType.Any)
@DataSourceRule(namespace = "Cassandra:Keyspace:TableName")
public class WaveDataSource {
    //波形ID
    @PropertyRestrictRule(unique = true)
    @ColumnRule(key = true, primitiveType = "UUID")
    private String wid;
    //波形采集时间
    @ColumnRule(primitiveType = "BIGINT")
    private long measDate;
    //波形总值
    @ColumnRule(primitiveType = "FLOAT")
    private float measValue;
    //转换系数
    @ColumnRule(primitiveType = "FLOAT")
    private float convertCoef;
    //采集频率
    @ColumnRule(primitiveType = "FLOAT")
    private float samplingRate;
    //原始频率
    @ColumnRule(primitiveType = "FLOAT")
    private float sourceRate;
    //波形长度
    @ColumnRule(primitiveType = "INT")
    private int dataLen;
    //转速
    @ColumnRule(primitiveType = "FLOAT")
    private float runSpeed;
    //波形横轴单位
    @ColumnRule(primitiveType = "TEXT")
    private String xUnit;
    //波形纵轴单位
    @ColumnRule(primitiveType = "TEXT")
    private String yUnit;
    //时标ID
    @ColumnRule(primitiveType = "UUID")
    private String tsId;
    //时标数据路由
    @DataSourceRefTypeRule(namespace = "Cassandra:Keyspace:TsDataTable", type = DataSourceType.Cassandra)
    @TableDataSourceRefConfigRule(key = "tsId")
    @ColumnRule(primitiveType = "UUID")
    private String tsPath;
    //时标偏移量
    @ColumnRule(primitiveType = "INT")
    private int tsOffset;
    //波形数据路由
//    @DataSourceRefTypeRule(namespace = "Http:RDFS", type = DataSourceType.Http)
//    @ColumnRule(type = "TEXT")
//    private String wavePath;
    //波形本体
    @HttpRule(
            method = HttpMethod.GET,
            url = "http://172.16.2.1:5010/DFS/TNode/File?fqQileName=",
            bakUrl = "http://172.16.2.2:5010/DFS/TNode/File?fileName=",
            resultType = HttpResultType.Byte
    )
    private String wavePath;
}
