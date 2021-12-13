package com.learning.datasource.dml;

import com.learning.schema.KeyMap;
import com.learning.schema.Operation;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class DMLCQL {

    private StringBuilder stringbuilder = new StringBuilder();

    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public DMLCQL addSelect(){
        String cql = "SELECT ";
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addFormat(String format){
        String cql = "";
        if(format.toUpperCase().equals("JSON"))
            cql += " JSON ";
        else if(format.toUpperCase().equals("DISTINCT"))
            cql += " DISTINCT ";
        stringbuilder.append(cql);
        return this;
    }

    /**
     * select_clause    ::=  selector [ AS identifier ] ( ',' selector [ AS identifier ] )
     * selector         ::=  column_name
     *                      | term
     *                      | CAST '(' selector AS cql_type ')'
     *                      | function_name '(' [ selector ( ',' selector )* ] ')'
     *                      | COUNT '(' '*' ')'
     */
    public DMLCQL addClause(boolean isAll, List<KeyMap> list){
        StringBuilder cql = new StringBuilder();
        if(isAll)
            cql.append("*");
        else {
            for(KeyMap km : list) {
                cql.append(km.getName());
                if(!isNullOrEmpty(km.getValue()))
                    cql.append(" AS ").append(km.getValue());
                cql.append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1));
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addFrom(String databaseName, String tableName){
        String cql = "\n FROM "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    /**
     * where_clause     ::=  relation ( AND relation )*
     * relation         ::=  column_name operator term
     *                      '(' column_name ( ',' column_name )* ')' operator tuple_literal
     *                      TOKEN '(' column_name ( ',' column_name )* ')' operator term
     * operator         ::=  '=' | '<' | '>' | '<=' | '>=' | '!=' | IN | CONTAINS | CONTAINS KEY
     */
    public DMLCQL addWhere(List<Operation> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n WHERE ");
            for (Operation op : list){
                cql.append(op.getSrc()).append(" ").append(op.getOperator()).append(" '").append(op.getDst()).append("'\n AND ");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 5));
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addGroup(List<String> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n GROUP BY ");
            for (String str:list){
                cql.append(str).append(",");
            }
            cql = new StringBuilder(new StringBuilder(cql.substring(0, cql.length() - 1)));
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addOrder(List<KeyMap> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n ORDER BY ");
            for (KeyMap km :list){
                cql.append(km.getName());
                if(!isNullOrEmpty(km.getValue()))
                    cql.append(" ").append(km.getValue());
                cql.append(",");
            }
            cql = new StringBuilder(new StringBuilder(new StringBuilder(cql.substring(0, cql.length() - 1))));
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addPartitionLimit(int limit){
        String cql = "";
        if(limit>0){
            cql += "\n PER PARTITION LIMIT "+limit;
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addLimit(int limit){
        String cql = "";
        if(limit>0){
            cql += "\n LIMIT "+limit;
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addFilter(boolean isFiltering){
        String cql = "";
        if(isFiltering){
            cql += "\n ALLOW FILTERING";
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addInsert(String databaseName, String tableName){
        String cql = "INSERT INTO "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addValues(List<String> columnList, List<List<String>> valueList){
        StringBuilder cql = new StringBuilder(" ");
        if(columnList.size()>0) {
            cql.append("(");
            for (String column : columnList) {
                cql.append(column).append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")\n ");
        }
        cql.append("VALUES ");
        for(List<String> s :valueList){
            cql.append("(");
            for(String k : s){
                cql.append("'").append(k).append("',");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + "),");
        }
        cql = new StringBuilder(cql.substring(0, cql.length() - 1));
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addIfNot(boolean exist) {
        String cql = "\n IF "+(exist?"":"NOT")+" EXISTS";
        stringbuilder.append(cql);
        return this;
    }

    /**
     * update_parameter ::=  ( TIMESTAMP | TTL ) ( integer | bind_marker )
     */
    public DMLCQL addParameter(List<KeyMap> list){
        StringBuilder cql= new StringBuilder();
        if(list.size()>0){
            cql.append("\n USING");
            for(KeyMap km : list){
                cql.append(" ").append(km.getName()).append(" ").append(km.getValue()).append("\n AND");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 4));
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addJson(String json, String def){
        String cql=" JSON '"+json+"'";
        if(def.toUpperCase().equals("NULL"))
            cql += " DEFAULT NULL";
        else if(def.toUpperCase().equals("UNSET"))
            cql += " DEFAULT UNSET";
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addUpdate(String databaseName, String tableName){
        String cql = "UPDATE "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    /**
     * assignment       ::=  simple_selection '=' term
     *                      | column_name '=' column_name ( '+' | '-' ) term
     *                      | column_name '=' list_literal '+' column_name
     * simple_selection ::=  column_name
     *                      | column_name '[' term ']'
     *                      | column_name '.' `field_name
     */
    public DMLCQL addSet(List<KeyMap> valueList){
        StringBuilder cql = new StringBuilder("\n SET ");
        for(KeyMap km : valueList){
            cql.append("\n ").append(km.getName()).append("=").append(km.getValue()).append(",");
        }
        cql = new StringBuilder(cql.substring(0, cql.length() - 1));
        stringbuilder.append(cql);
        return this;
    }

    /**
     * condition        ::=  simple_selection operator term
     */
    public DMLCQL addConditions(List<Operation> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n IF");
            for (Operation op : list){
                cql.append(" ").append(op.getSrc()).append(" ").append(op.getOperator()).append(" ").append(op.getDst()).append("\n AND");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 4));
        }
        else
            cql.append("\n IF EXISTS");
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addDelete(){
        String cql = "DELETE ";
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addSimple(List<String> simple){
        StringBuilder cql = new StringBuilder();
        if(simple.size()>0){
            for(String str : simple) {
                cql.append(str).append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1) + " ");
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addBatch(String format){
        String cql = "BEGIN "+(format.toUpperCase().equals("UNLOGGED")?"UNLOGGED":format.toUpperCase().equals("COUNTER")?"COUNTER":"")+" BATCH ";
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addCql(List<String> list){
        StringBuilder cql = new StringBuilder();
        for(String str : list){
            cql.append("\n ").append(str).append(";");
        }
        stringbuilder.append(cql);
        return this;
    }

    public DMLCQL addApply(){
        String cql = "\n APPLY BATCH";
        stringbuilder.append(cql);
        return this;
    }

}
