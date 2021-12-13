package com.learning.datasource.ddl;

import com.learning.publics.DDLResult;
import com.learning.schema.ColumnInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;

import java.sql.Connection;
import java.util.List;

public class ViewOperation {

    public DDLOperation createView(Connection connection, String databaseName, String viewName, List<ColumnInfo> columnList, String comment, String hql){
        DDLResult result = new DDLResult();
        ViewHQL operation = new ViewHQL();
        return result.getResult(connection,
                databaseName,
                operation.addViewName(databaseName,viewName)
                        .addColumns(columnList)
                        .addComment(comment)
                        .addSelect(hql).getStringbuilder().toString(),
                "create view "+viewName+" successful!");

    }

    public DDLOperation dropView(Connection connection, String databaseName, String viewName){
        DDLResult result = new DDLResult();
        ViewHQL operation = new ViewHQL();
        return result.getResult(connection, databaseName,
                operation.dropView(databaseName, viewName).getStringbuilder().toString(),
                "drop view " + viewName + " successful!");
    }

    public DDLOperation alterViewProperties(Connection connection, String databaseName, String viewName, List<KeyMap> properties){
        DDLResult result = new DDLResult();
        ViewHQL operation = new ViewHQL();
        return result.getResult(connection, databaseName,
                operation.alterViewName(databaseName, viewName)
                        .alterViewProperties(properties).getStringbuilder().toString(),
                "alter view " + viewName + " properties successful!");
    }

    public DDLOperation alterViewSelect(Connection connection, String databaseName, String viewName, String hql){
        DDLResult result = new DDLResult();
        ViewHQL operation = new ViewHQL();
        return result.getResult(connection, databaseName,
                operation.alterViewName(databaseName, viewName)
                        .addSelect(hql).getStringbuilder().toString(),
                "alter view " + viewName + " properties successful!");
    }

}
