package com.learning.datasource.ddl;

import com.datastax.driver.core.Session;
import com.learning.publics.DDLResult;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;

import java.util.List;

public class FunctionCassandra {

    public DDLOperation createScalarFunction(Session session, String databaseName, String functionName, boolean ifReplace,
                                             List<KeyMap> paramList, boolean isNullWithoutInput, String type, String language, String text){
        DDLResult result = new DDLResult();
        FunctionCQL operation = new FunctionCQL();
        return result.getResult(session,databaseName,
                operation.addCreateScalar(functionName,ifReplace)
                        .addParams(paramList)
                        .addisNullWithoutInput(isNullWithoutInput)
                        .addReturn(type)
                        .addLanguage(language)
                        .addContent(text)
                        .getStringbuilder().toString(),
                "create scalar function " + functionName + " successful!");
    }

    public DDLOperation dropScalarFunction(Session session, String databaseName, String functionName, List<String> typeList){
        DDLResult result = new DDLResult();
        FunctionCQL operation = new FunctionCQL();
        return result.getResult(session,databaseName,
                operation.addDrop(true,functionName)
                        .addTypes(typeList)
                        .getStringbuilder().toString(),
                "drop scalar function " + functionName + " successful!");
    }

    public DDLOperation createAggregateFunction(Session session, String databaseName, String functionName, boolean ifReplace,
                                                List<KeyMap> paramList, String source_function, String type, String final_function, String initcond){
        DDLResult result = new DDLResult();
        FunctionCQL operation = new FunctionCQL();
        return result.getResult(session,databaseName,
                operation.addCreateAggregate(functionName,ifReplace)
                        .addParams(paramList)
                        .addSource(source_function,type)
                        .addFinal(final_function)
                        .addInit(initcond)
                        .getStringbuilder().toString(),
                "create aggregate function " + functionName + " successful!");
    }

    public DDLOperation dropAggregateFunction(Session session, String databaseName, String functionName, List<String> typeList){
        DDLResult result = new DDLResult();
        FunctionCQL operation = new FunctionCQL();
        return result.getResult(session,databaseName,
                operation.addDrop(false,functionName)
                        .addTypes(typeList)
                        .getStringbuilder().toString(),
                "drop aggregate function " + functionName + " successful!");
    }

}
