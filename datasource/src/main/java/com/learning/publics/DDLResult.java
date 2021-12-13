package com.learning.publics;

import com.datastax.driver.core.Session;
import com.learning.schema.output.DDLOperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DDLResult {

    public DDLOperation getResult(Connection connection, String databaseName, String hql, String message){
        DDLOperation result = new DDLOperation();
        String sql = "use " + databaseName;
        try (Statement smt = connection.createStatement()) {
            smt.execute(sql);
            smt.execute(hql);
            result = result.getResult("OK", message);
        } catch (SQLException e) {
            result = result.getResult("Failed", e.getMessage());
        }
        return result;
    }

    public DDLOperation getResult(Session session, String databaseName, String cql, String message){
        DDLOperation result = new DDLOperation();
        try {
            session.execute("USE "+databaseName);
            session.execute(cql);
            result = result.getResult("OK", message);
        } catch (Exception e) {
            result = result.getResult("Failed", e.getMessage());
        }finally {
            if(session != null)
                session.close();
        }
        return result;
    }

}
