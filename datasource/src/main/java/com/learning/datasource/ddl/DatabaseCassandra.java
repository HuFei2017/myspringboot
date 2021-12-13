package com.learning.datasource.ddl;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.learning.publics.DDLResult;
import com.learning.schema.CassTableInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCassandra {

    /**
     * @param options must have property:replication,which recommend as {'class': 'SimpleStrategy', 'replication_factor' : 1}
     */
    public DDLOperation createDatabase(Session session, String databaseName, List<KeyMap> options){
        DDLResult result = new DDLResult();
        DatabaseCQL operation = new DatabaseCQL();
        return result.getResult(session,"system",
                   operation.addCreate(databaseName).addOptions(options).getStringbuilder().toString(),
                   "create keyspace " + databaseName + " successful!");
    }

    public DDLOperation alterDatabase(Session session, String databaseName, List<KeyMap> options){
        DDLResult result = new DDLResult();
        DatabaseCQL operation = new DatabaseCQL();
        return result.getResult(session,databaseName,
                operation.addAlter(databaseName).addOptions(options).getStringbuilder().toString(),
                "alter keyspace " + databaseName + " successful!");
    }

    public DDLOperation dropDatabase(Session session, String databaseName){
        DDLResult result = new DDLResult();
        DatabaseCQL operation = new DatabaseCQL();
        return result.getResult(session,databaseName,
                operation.addDrop(databaseName).getStringbuilder().toString(),
                "drop keyspace " + databaseName + " successful!");
    }

    public List<CassTableInfo> descDatabase(Session session){
        List<CassTableInfo> result = new ArrayList<>();
        try {
            session.execute("USE system_schema");
            ResultSet rs = session.execute("select * from keyspaces");
            for(Row row:rs){
//                if(!row.getString("keyspace_name").equals("system_schema")&&
//                        !row.getString("keyspace_name").equals("system_auth")&&
//                        !row.getString("keyspace_name").equals("system")&&
//                        !row.getString("keyspace_name").equals("system_distributed")&&
//                        !row.getString("keyspace_name").equals("system_traces")) {
                    CassTableInfo km = new CassTableInfo();
                    km.setType("keyspace");
                    km.setTableName(row.getString("keyspace_name"));
                    km.setCreateCQL("CREATE KEYSPACE " + row.getString("keyspace_name") +
                            " WITH replication = " + row.getObject("replication").toString() +
                            "  AND durable_writes = " + row.getObject("durable_writes").toString() + ";");
                    result.add(km);
//                }
            }
        } finally {
            if(session != null)
                session.close();
        }
        return result;
    }

}
