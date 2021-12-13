package com.learning.examples.examples;

import java.sql.*;
import java.util.UUID;

/**
 * @ClassName JDBCTest
 * @Description TODO
 * @Author hufei
 * @Date 2020/10/14 9:00
 * @Version 1.0
 */
public class JDBCTest {

    private Connection conn = null;

    public void connect() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
    }

    public void testBatchInsert_1() throws SQLException {

        if (null != conn) {

            long startTime = System.currentTimeMillis();

            //提升性能
            conn.setAutoCommit(false);

            String table = "test";

            conn.prepareStatement("TRUNCATE TABLE " + table).execute();

            String sql = "INSERT INTO " + table + " VALUES (?,?,?)";

            PreparedStatement cmd = conn.prepareStatement(sql);

            for (int i = 0; i < 100000; i++) {
                cmd.setObject(1, UUID.randomUUID());
                cmd.setObject(2, "name" + i);
                cmd.setObject(3, i);

                cmd.addBatch();

                if (i % 1000 == 0) {
                    cmd.executeBatch();
                    cmd.clearBatch();
                }
            }

            cmd.executeBatch();
            cmd.clearBatch();

            conn.commit();

            long endTime = System.currentTimeMillis(); //获取结束时间

            System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        }
    }

    public void testBatchInsert_2() throws SQLException {

        if (null != conn) {

            long startTime = System.currentTimeMillis();

            //提升性能
            conn.setAutoCommit(false);

            String table = "test";

            conn.prepareStatement("TRUNCATE TABLE " + table).execute();

            Statement cmd = conn.createStatement();

            for (int i = 0; i < 100000; i++) {

                cmd.addBatch("INSERT INTO " + table + " VALUES('" + UUID.randomUUID() + "','name" + i + "','" + i + "')");

                if (i % 1000 == 0) {
                    cmd.executeBatch();
                    cmd.clearBatch();
                }
            }

            cmd.executeBatch();
            cmd.clearBatch();

            conn.commit();

            long endTime = System.currentTimeMillis(); //获取结束时间

            System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        }
    }

    public void testBatchInsert_3() throws SQLException {

        if (null != conn) {

            long startTime = System.currentTimeMillis();

            //提升性能
            conn.setAutoCommit(false);

            String table = "test";

            conn.prepareStatement("TRUNCATE TABLE " + table).execute();

            Statement cmd = conn.createStatement();

            for (int i = 0; i < 10; i++) {

                StringBuilder builder = new StringBuilder();

                for (int j = 0; j < 1000; j++) {
                    builder.append("('").append(UUID.randomUUID()).append("','name").append(i).append("','").append(i).append("'),");
                }

                if (builder.length() > 0)
                    cmd.addBatch("INSERT INTO " + table + " VALUES" + builder.substring(0, builder.length() - 1));

            }

            cmd.executeBatch();
            cmd.clearBatch();

            conn.commit();

            long endTime = System.currentTimeMillis(); //获取结束时间

            System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        }
    }

    public void close() throws SQLException {
        if (null != conn) {
            conn.close();
            conn = null;
        }
    }

}
