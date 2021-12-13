package com.learning.publics;

import com.datastax.driver.core.Cluster;
import com.learning.schema.KeyMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class ConnectionHelper {

    public Connection connectPostgresql(){
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://10.254.204.2:5432/jb",
                    "postgres",
                    "123");
//            conn = DriverManager.getConnection(
//                    "jdbc:postgresql://172.16.0.201:5432/jb09",
//                    "postgres",
//                    "123");
//            conn = DriverManager.getConnection(
//                    "jdbc:postgresql://10.74.3.160:5432/hnr",
//                    "postgres",
//                    "123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Connection connectOdbcSqlserver(){
        Connection conn = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection("jdbc:odbc:10.3.9.104");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Connection connectSqlserver(){
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(
                    "jdbc:sqlserver://172.16.8.108:1433;DatabaseName=mos",
                    "sa",
                    "123");
//            conn = DriverManager.getConnection(
//                    "jdbc:sqlserver://10.3.9.104:1433;DatabaseName=TreeRate",
//                    "rongzhi",
//                    "zhirong");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Cluster connectCassandra(){
        return Cluster.builder()
//                .addContactPoints("192.168.8.66")
                .addContactPoints("10.189.2.16")//node04.jinbo.com
//                .addContactPoint("172.16.4.201")//node00.ronds.com
                .withPort(9042)
                .build();
    }

    public String appendUrl(String path, String operation, List<KeyMap> list){
        String port = "50070";
        String url = "10.189.2.10";
//        String url = "172.16.4.201";
//        String url = "10.74.3.162";
        return "http://"+ url +":"+ port +"/webhdfs/v1/"+path+"?user.name=hdfs&op="+operation+getUrl(list);
    }

    private String getUrl(List<KeyMap> list){
        StringBuilder result = new StringBuilder();
        for(KeyMap k:list){
            result.append("&").append(k.getName()).append("=").append(k.getValue());
        }
        return result.toString();
    }
}
