package com.learning.datasource.ddl;

import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class FunctionCQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public FunctionCQL addCreateScalar(String functionName, boolean ifReplace){
        String cql = "CREATE "+(ifReplace?"OR REPLACE":"")+" FUNCTION IF NOT EXISTS\n "+functionName;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addParams(List<KeyMap> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0) {
            cql.append(" (");
            for(KeyMap km : list) {
                cql.append(km.getName()).append(" ").append(km.getValue()).append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
        }
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addisNullWithoutInput(boolean isNullWithoutInput){
        String cql = "\n "+(isNullWithoutInput?"RETURNS NULL":"CALLED")+" ON NULL INPUT";
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addReturn(String type){
        String cql = "\n RETURNS "+type;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addLanguage(String language){
        String cql = "\n LANGUAGE "+language;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addContent(String text){
        String cql = "\n AS $$\n "+text+"\n $$";
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addDrop(boolean isScalar, String functionName){
        String cql = "DROP "+(isScalar?"FUNCTION":"AGGREGATE")+" IF EXISTS "+functionName;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addTypes(List<String> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0) {
            cql.append(" (");
            for(String str : list) {
                cql.append(str).append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
        }
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addCreateAggregate(String functionName, boolean ifReplace){
        String cql = "CREATE "+(ifReplace?"OR REPLACE":"")+" AGGREGATE IF NOT EXISTS\n "+functionName;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addSource(String source_function, String type){
        String cql = "\n SFUNC "+source_function+"\n STYPE "+type;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addFinal(String final_function){
        String cql = isNullOrEmpty(final_function)?"":"\n FINALFUNC "+final_function;
        stringbuilder.append(cql);
        return this;
    }

    public FunctionCQL addInit(String initcond){
        String cql = isNullOrEmpty(initcond)?"":"\n INITCOND "+initcond;
        stringbuilder.append(cql);
        return this;
    }

}
