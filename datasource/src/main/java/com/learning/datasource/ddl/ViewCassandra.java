package com.learning.datasource.ddl;

import com.datastax.driver.core.Session;
import com.learning.publics.DDLResult;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;

import java.util.List;

public class ViewCassandra {

    public DDLOperation createView(Session session, String databaseName, String viewName, String statement,
                                   List<String> primaryList, List<KeyMap> optionList){
        DDLResult result = new DDLResult();
        ViewCQL operation = new ViewCQL();
        return result.getResult(session,databaseName,
                operation.addCreate(viewName,statement)
                        .addPrimary(primaryList)
                        .addOptions(optionList)
                        .getStringbuilder().toString(),
                "create view " + viewName + " successful!");
    }

    public DDLOperation alterView(Session session, String databaseName, String viewName, List<KeyMap> optionList){
        DDLResult result = new DDLResult();
        ViewCQL operation = new ViewCQL();
        return result.getResult(session,databaseName,
                operation.addAlter(viewName)
                        .addOptions(optionList)
                        .getStringbuilder().toString(),
                "alter view " + viewName + " successful!");
    }

    public DDLOperation dropView(Session session, String databaseName, String viewName){
        DDLResult result = new DDLResult();
        ViewCQL operation = new ViewCQL();
        return result.getResult(session,databaseName,
                operation.addDrop(viewName)
                        .getStringbuilder().toString(),
                "drop view " + viewName + " successful!");
    }

}
