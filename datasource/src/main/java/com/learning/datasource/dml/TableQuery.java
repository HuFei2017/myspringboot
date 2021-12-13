package com.learning.datasource.dml;

import com.learning.datasource.ddl.DatabaseOperation;
import com.learning.datasource.ddl.TableOperation;
import com.learning.schema.ColumnInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.input.TableCreateInput;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableQuery {

    public void saveQueryHistory(Connection connection , Date refDate, String refMan, String refHql, int refTime, boolean refState, String refDesc) {
        new DatabaseOperation().createDatabase(connection, "users", "USER\\'s infomation");
        List<ColumnInfo> columnList = new ArrayList<>();
        columnList.add(new ColumnInfo("time", "string", "execution time"));
        columnList.add(new ColumnInfo("execution", "string", "what the user did"));
        columnList.add(new ColumnInfo("consuming", "int", "how long the execution taken,unit of \\'ms\\'"));
        columnList.add(new ColumnInfo("result", "BOOLEAN", "whether the execution is successful or not"));
        columnList.add(new ColumnInfo("description", "string", "some extra descriptions"));
        List<ColumnInfo> columnPartition = new ArrayList<>();
        columnPartition.add(new ColumnInfo("operator", "string", "user\\'s id"));
        TableCreateInput input = new TableCreateInput("users", "", "user_execution", columnList, "", columnPartition,
                new ArrayList<>(), 0, new ArrayList<>(), new ArrayList<>(), "", "PARQUET");
        new TableOperation().createTable(connection, input);
        List<KeyMap> partitionList = new ArrayList<>();
        partitionList.add(new KeyMap("operator", ""));
        List<List<String>> valueList = new ArrayList<>();
        List<String> s = new ArrayList<>();
        s.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(refDate));
        s.add(refHql);
        s.add(Integer.toString(refTime));
        s.add(refState?"true":"");
        s.add(refDesc);
        s.add(refMan);
        valueList.add(s);
        new Manipulation().insertData(connection, "users", "user_execution", partitionList, valueList);
    }

}
