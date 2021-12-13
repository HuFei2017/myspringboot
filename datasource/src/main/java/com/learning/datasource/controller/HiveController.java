package com.learning.datasource.controller;

import com.learning.datasource.ddl.DatabaseOperation;
import com.learning.datasource.ddl.TableOperation;
import com.learning.datasource.dml.Manipulation;
import com.learning.datasource.dml.TableQuery;
import com.learning.publics.Publication;
import com.learning.schema.ColumnInfo;
import com.learning.schema.DatabaseDescription;
import com.learning.schema.TableDescription;
import com.learning.schema.TreeItem;
import com.learning.schema.input.QueryResultInput;
import com.learning.schema.input.TableCreateInput;
import com.learning.schema.output.DDLOperation;
import com.learning.schema.output.DMLOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.apache.parquet.Strings.isNullOrEmpty;

@RestController
public class HiveController {

    @Autowired
    HttpServletRequest request;

    @Qualifier("dataSource")
    @Autowired
    DataSource source;

    //基础数据操作
    @GetMapping("/datahouse/getDatabases/{expression}")
    public List<TreeItem> getDatabases(@PathVariable("expression") String expression, @PathVariable("expression") String isAll) throws SQLException {
        expression = (expression.equals("-1") || isNullOrEmpty(expression)) ? "" : expression;
        isAll = !isAll.equals( "-2") ?"LIKE":"EQUAL";
        DatabaseOperation result = new DatabaseOperation();
        try(Connection connection = source.getConnection()){
            return result.getDatabases(connection,expression,isAll);
        }
    }

    @GetMapping("/datahouse/getTables/{databaseName}")
    public List<TreeItem> getTables(@PathVariable("databaseName") String databaseName) throws SQLException {
        TableOperation result = new TableOperation();
        try(Connection connection = source.getConnection()) {
            return result.getTables(connection, databaseName, "");
        }

    }

    @GetMapping("/datahouse/getColumns/{databaseName}/{tableName}")
    public List<ColumnInfo> getColumnInfos(@PathVariable("databaseName") String databaseName, @PathVariable("tableName") String tableName)  {
        ArrayList<ColumnInfo> result = new ArrayList<>();
        try (Connection connection = source.getConnection(); Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            ResultSet rs = smt.executeQuery("DESCRIBE " + tableName);
            while (rs.next()) {
                if(isNullOrEmpty(rs.getString("col_name")))
                    break;
                ColumnInfo column = new ColumnInfo();
                column.setId(databaseName + "." + tableName + "." + rs.getString("col_name"));
                column.setColumnName(rs.getString("col_name"));
                column.setColumnType(rs.getString("data_type"));
                column.setComment(rs.getString("comment"));
                result.add(column);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/tableDescription/showDatabase/{databaseName}")
    public DatabaseDescription getDatabaseDescription(@PathVariable("databaseName") String databaseName){
        DatabaseDescription result = new DatabaseDescription();
        DatabaseOperation operation = new DatabaseOperation();
        try (Connection connection = source.getConnection(); Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            result.setDatabaseInfo(operation.getDatabaseDetails(connection,databaseName));
            result.setDatabaseCreation(operation.getDatabaseCreation(connection,databaseName));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/tableDescription/showTable/{id}/")
    public TableDescription getTableDescription(@PathVariable("id") String id){
        String databaseName = id.substring(0,id.indexOf("."));
        String tableName = id.substring(id.indexOf(".")+1);
        return getTableDescription(databaseName,tableName);
    }

    private TableDescription getTableDescription(String databaseName, String tableName){
        TableDescription result = new TableDescription();
        TableOperation operation = new TableOperation();
        try (Connection connection = source.getConnection(); Statement smt = connection.createStatement()) {
            smt.execute("use " + databaseName);
            List<ColumnInfo> list = operation.getTableDetails(connection,databaseName,tableName,true, new ArrayList<>());
            result.setTableInfo(list);
            result.setTableCreation(operation.getTableCreation(connection,databaseName,tableName,list));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/datahouse/databasecreate/ifexisted/{name}")
    public boolean ifExisted(@PathVariable("name") String name) throws SQLException {
        return getDatabases(name,"-2").size()>0;
    }

    @GetMapping("/datahouse/databasecreate/name/{databaseName}/{comment}")
    public DDLOperation createDatabase(@PathVariable("databaseName") String databaseName, @PathVariable("comment") String comment) throws SQLException {
        try (Connection connection = source.getConnection()) {
            comment = (comment.equals("-1") || isNullOrEmpty(comment)) ? "" : comment;
            return new DatabaseOperation().createDatabase(connection,databaseName,comment);
        }
    }

    @GetMapping("/datahouse/databasedrop/name/{databaseName}")
    public DDLOperation dropDatabase(@PathVariable("databaseName") String databaseName) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new DatabaseOperation().dropDatabase(connection,databaseName,"");
        }
    }

    @GetMapping("/datahouse/tabledrop/name/{databaseName}/{tableName}")
    public DDLOperation dropTable(@PathVariable("databaseName") String databaseName, @PathVariable("tableName") String tableName) throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new TableOperation().dropTable(connection,databaseName,tableName,"");
        }
    }

    @PostMapping("/datahouse/tablecreate")
    public DDLOperation createTable(@RequestBody TableCreateInput input)throws SQLException {
        try (Connection connection = source.getConnection()) {
            return new TableOperation().createTable(connection,input);
        }
    }

    @PostMapping("/datahouse/dataPreview/getQueryResult")
    public DMLOperation getQueryResult(@RequestBody QueryResultInput input) {
        String refMan = Objects.equals(input.getUserName(), "") ?"superadmin":input.getUserName();
        Manipulation operation = new Manipulation();
        DMLOperation result = new DMLOperation();
        String databaseName = input.getDatabaseName().contains(".")?input.getDatabaseName().substring(0,input.getDatabaseName().indexOf(".")):input.getDatabaseName();
        String[] hqlstring = input.getHql().replaceAll("[\r\n]", "").split(";");
        try (Connection connection = source.getConnection()) {
            long consuming = 0;
            for (int i=0;i<hqlstring.length;i++) {
                String sql = hqlstring[i];
                if(i== hqlstring.length-1){
                    result = operation.executeQuery(connection,databaseName,sql,input.getPageIndex(),input.getPageSize(),true);
                    consuming += result.getResultSet().getTimeConsuming();
                    result.getResultSet().setTimeConsuming(consuming);
                }
                else {
                    result = operation.executeQuery(connection, databaseName, sql, input.getPageIndex(), input.getPageSize(), false);
                    consuming += result.getResultSet().getTimeConsuming();
                }
                int currentConsuming = (int)result.getResultSet().getTimeConsuming();
                new TableQuery().saveQueryHistory(connection,new Date(),refMan, sql,currentConsuming,
                    (result.getOperationState().getStatus() != 500),
                    new Publication().beforeTransfer(result.getOperationState().getMessage()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @PostMapping("/datahouse/dataPreview/getQueryHistory")
    public DMLOperation getQueryHistory(@RequestBody QueryResultInput input) {
        String refMan = Objects.equals(input.getUserName(), "") ?"superadmin":input.getUserName();
        DMLOperation result = new DMLOperation();
        Manipulation operation = new Manipulation();
        try (Connection connection = source.getConnection()) {
            result = operation.executeQuery(connection,"users","select * from user_execution where operator='"+refMan+"' order by time desc",input.getPageIndex(),input.getPageSize(),true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
