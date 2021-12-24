package com.learning.sampleset;

import com.learning.entity.AnalyseNodeTree;
import com.learning.enums.AnalyseDataType;
import com.learning.enums.DataSourceType;
import com.learning.enums.HttpMethod;
import com.learning.rules.*;
import lombok.Data;

/**
 * @ClassName CaseDataSet
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/29 14:01
 * @Version 1.0
 */
@Data
@DataSourceTypeRule(type = DataSourceType.PostgreSql)
@DataSourceRule(namespace = "PostgreSql:DbName:TableName")
public class CaseDataSet {

    //设备基本信息
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String entCode;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String entName;//企业名称
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String businessType;//行业
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String orgName;
    @ColumnRule(primitiveType = "UUID")
    private String deviceGuid;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String deviceName;
    @HttpRule(method = HttpMethod.POST, url = "http://172.16.0.131:5058/api/v1/devicemodel", isBodyArray = true, isValueInBody = true)
    @ColumnRule(primitiveType = "UUID")
    private String deviceModel;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String deviceCaseNature;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String machineType;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String bearingType;//轴承类型
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String bladeNum;//叶片数量
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String hasGear;//带不带齿轮
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String owner;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String projectName;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String projectState;

    //案例基本信息
    @PropertyRestrictRule(unique = true)
    @ColumnRule(key = true, primitiveType = "INT4")
    private int caseId;//案例ID
    @ColumnRule(primitiveType = "UUID")
    private String caseGuid;//案例ID
    @ColumnRule(primitiveType = "UUID")
    private String faultComponentGuid;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String faultComponentName;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String faultType;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String damageDescription;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String damageLevel;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String caseDataType;//有明显表现的测量定义
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String actionBeforeDown;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String rpm;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String rpmDetail;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String evaluation;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String alarmLevel;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String signalSummary;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String diagnosis;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String todo;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String remark;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String timeMissReason;
    @ColumnRule(primitiveType = "INT4")
    private int cleaned;
    @ColumnRule(primitiveType = "INT4")
    private int allowIgnore;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String componentName;
    @IntegerRule(min = 0)
    @ColumnRule(primitiveType = "INT4")
    private int componentNum;
    @ColumnRule(primitiveType = "BOOL")
    private boolean hasFeedbackPaper;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String createTime;

    //案例标注类
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String stateMonitoringDateTime;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String stateConfirmationDateTime;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String overHaultTime;
    @ColumnRule(primitiveType = "INT4")
    private int lv4;
    @ColumnRule(primitiveType = "INT4")
    private int steady;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String relatedPoints;
    @ColumnRule(primitiveType = "VARCHAR", length = 255)
    private String majorPoint;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String normalSt;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String normalEd;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String earlySt;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String earlyEd;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String midSt;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String midEd;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String finalSt;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String finalEd;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String lv3St;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String lv3Ed;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String lv4St;
    @DateRule
    @ColumnRule(primitiveType = "TIMESTAMP")
    private String lv4Ed;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String normalDetail;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String earlyStDetail;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String earlyEdDetail;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String midstDetail;
    @ColumnRule(primitiveType = "VARCHAR", length = 10240)
    private String lv4Detail;

    //案例数据
    @AnalyseRoleRule(analysable = true, groupable = false)
    //DataType约束了Config的类型, Config的具体内容约束了后续的数据链, 根据path即可完成数据的导航和获取
    @DataTypeRule(type = AnalyseDataType.RzTimeSerialData)
    @RzTimeSerialDataSourceConfigRule(
            vibDataNameSpace = "Cassandra:Keyspace:TimeSerialTable",
            waveInfoNameSpace = "Cassandra:Keyspace:WaveInfoTable",
            waveDataNameSpace = "Http:RDFS",
            tsDataNameSpace = "Cassandra:Keyspace:TsDataTable"
    )
    @ColumnRule(primitiveType = "JSONB")
    private AnalyseNodeTree data;
}
