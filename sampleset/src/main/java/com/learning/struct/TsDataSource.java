package com.learning.struct;

import com.learning.enums.DataSourceType;
import com.learning.rules.ColumnRule;
import com.learning.rules.DataSourceRule;
import com.learning.rules.DataSourceTypeRule;
import com.learning.rules.PropertyRestrictRule;
import lombok.Data;

/**
 * @ClassName TsDataSource
 * @Description TODO
 * @Author hufei
 * @Date 2021/12/2 11:27
 * @Version 1.0
 */
@Data
@DataSourceTypeRule(type = DataSourceType.Cassandra)
@DataSourceRule(namespace = "Cassandra:Keyspace:TsDataTable")
public class TsDataSource {
    @PropertyRestrictRule(unique = true)
    @ColumnRule(key = true, primitiveType = "UUID")
    private String tsId;
    @PropertyRestrictRule(unique = true)
    @ColumnRule(primitiveType = "INT")
    private int dataType;
    @ColumnRule(primitiveType = "BLOB")
    private Long[] datas;
    @ColumnRule(primitiveType = "BIGINT")
    private long measDate;
    @ColumnRule(primitiveType = "BIGINT")
    private int timeBase;
}
