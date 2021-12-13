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
    @ColumnRule(type = "VARCHAR", length = 255)
    private String entCode;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String entName;//企业名称
    @ColumnRule(type = "VARCHAR", length = 255)
    private String businessType;//行业
    @ColumnRule(type = "VARCHAR", length = 255)
    private String orgName;
    @ColumnRule(type = "UUID")
    private String deviceGuid;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String deviceName;
    @HttpRule(method = HttpMethod.POST, url = "http://172.16.0.131:5058/api/v1/devicemodel", isBodyArray = true, isValueInBody = true)
    @ColumnRule(type = "UUID")
    private String deviceModel;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String deviceCaseNature;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String machineType;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String bearingType;//轴承类型
    @ColumnRule(type = "VARCHAR", length = 255)
    private String bladeNum;//叶片数量
    @ColumnRule(type = "VARCHAR", length = 255)
    private String hasGear;//带不带齿轮
    @ColumnRule(type = "VARCHAR", length = 255)
    private String owner;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String projectName;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String projectState;

    //案例基本信息
    @PropertyRestrictRule(unique = true)
    @ColumnRule(key = true, type = "INT4")
    private int caseId;//案例ID
    @ColumnRule(type = "UUID")
    private String caseGuid;//案例ID
    @ColumnRule(type = "UUID")
    private String faultComponentGuid;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String faultComponentName;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String faultType;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String damageDescription;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String damageLevel;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String caseDataType;//有明显表现的测量定义
    @ColumnRule(type = "VARCHAR", length = 255)
    private String actionBeforeDown;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String rpm;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String rpmDetail;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String evaluation;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String alarmLevel;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String signalSummary;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String diagnosis;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String todo;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String remark;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String timeMissReason;
    @ColumnRule(type = "INT4")
    private int cleaned;
    @ColumnRule(type = "INT4")
    private int allowIgnore;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String componentName;
    @IntegerRule(min = 0)
    @ColumnRule(type = "INT4")
    private int componentNum;
    @ColumnRule(type = "BOOL")
    private boolean hasFeedbackPaper;
    @ColumnRule(type = "TIMESTAMP")
    private String createTime;

    //案例标注类
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String stateMonitoringDateTime;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String stateConfirmationDatetime;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String overHaultTime;
    @ColumnRule(type = "INT4")
    private int lv4;
    @ColumnRule(type = "INT4")
    private int steady;
    @ColumnRule(type = "VARCHAR", length = 255)
    private String relatedPoints;
    @TextRule(maxLength = 255)
    @ColumnRule(type = "VARCHAR", length = 255)
    private String majorPoint;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String normalSt;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String normalEd;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String earlySt;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String earlyEd;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String midSt;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String midEd;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String finalSt;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String finalEd;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String lv3St;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String lv3Ed;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String lv4St;
    @DateRule
    @ColumnRule(type = "TIMESTAMP")
    private String lv4Ed;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String normalDetail;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String earlyStDetail;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String earlyEdDetail;
    @ColumnRule(type = "VARCHAR", length = 10240)
    private String midstDetail;
    @ColumnRule(type = "VARCHAR", length = 10240)
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
    @ColumnRule(type = "JSONB")
    private AnalyseNodeTree data;
}
