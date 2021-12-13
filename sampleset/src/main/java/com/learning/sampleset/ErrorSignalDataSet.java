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
public class ErrorSignalDataSet {
    private String assetId;
    private String assetName;
    private int assetCode;
    @HttpRule(method = HttpMethod.POST, url = "http://172.16.0.131:5058/api/v1/devicemodel", isBodyArray = true, isValueInBody = true)
    private String assetModel;
    private String entName;
    private String entCode;
    private String pointId;
    private String pointName;
    private String caption;
    private String indexName;
    private String collectionType;
    private int sensorType;
    private String sensorStatus;
    @DateRule
    private String measDate;
    @AnalyseRoleRule(analysable = true, groupable = false)
    @DataTypeRule(type = AnalyseDataType.RzWaveData)
    @RzTimeSerialDataSourceConfigRule(
            vibDataNameSpace = "Cassandra:Keyspace:TimeSerialTable",
            waveInfoNameSpace = "Cassandra:Keyspace:WaveInfoTable",
            waveDataNameSpace = "Http:RDFS",
            tsDataNameSpace = "Cassandra:Keyspace:TsDataTable"
    )
    private AnalyseNodeTree data;
}
