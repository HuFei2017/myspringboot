package com.learning.sampleset;

import com.learning.entity.AnalyseNodeTree;
import com.learning.enums.AnalyseDataType;
import com.learning.enums.DataSourceType;
import com.learning.enums.HttpMethod;
import com.learning.rules.*;
import lombok.Data;

/**
 * @ClassName QiTingJiDataSet
 * @Description TODO
 * @Author hufei
 * @Date 2021/12/2 14:10
 * @Version 1.0
 */
@Data
@DataSourceTypeRule(type = DataSourceType.PostgreSql)
@DataSourceRule(namespace = "PG:DbName:TableName")
public class FreqDataSet {
    private String assetId;
    private String assetName;
    private int assetCode;
    @HttpRule(method = HttpMethod.POST, url = "http://172.16.0.131:5058/api/v1/devicemodel", isBodyArray = true, isValueInBody = true)
    private String assetModel;
    private String assetStructure;
    private String entCode;
    private String entName;
    private String minSpeed;
    private String maxSpeed;
    private String parts;
    @AnalyseRoleRule(analysable = true, groupable = false)
    @DataTypeRule(type = AnalyseDataType.RzFreqData)
    @RzTimeSerialDataSourceConfigRule(
            vibDataNameSpace = "Cassandra:Keyspace:TimeSerialTable",
            waveInfoNameSpace = "Cassandra:Keyspace:WaveInfoTable",
            waveDataNameSpace = "Http:RDFS",
            tsDataNameSpace = "Cassandra:Keyspace:TsDataTable"
    )
    private AnalyseNodeTree data;
}
