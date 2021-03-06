package com.learning.cassandra.partitiontest;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.learning.cassandra.util.TextUtil;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName ReadPerPointTest
 * @Description TODO
 * @Author hufei
 * @Date 2022/1/6 12:05
 * @Version 1.0
 */
public class ReadSomePointTest {
    public static void main(String[] args) {
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress("172.16.0.78", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.79", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.80", 9042));
        CqlSessionBuilder cluster = builder.withKeyspace("samplesetdata").withLocalDatacenter("datacenter1");
        CqlSession session = cluster.build();
        long st = System.currentTimeMillis();
        List<Map<String, Object>> lines = new ArrayList<>();
        ResultSet data = session.execute("SELECT * FROM samplesetdata.test_vibdata WHERE id=937f4aa3-436b-4be8-9dca-b28bc8a6a6f0 AND datatype=1");
        String[] columns = {"id", "datatype", "seq", "comment", "extra", "name", "props", "x", "xunit", "y", "yunit"};
        for (Row row : data) {
            Map<String, Object> line = new HashMap<>();
            for (String column : columns) {
                if (column.equals("comment") ||
                        column.equals("extra") ||
                        column.equals("props") ||
                        column.equals("x") ||
                        column.equals("y")) {
                    line.put(column, new String(row.getByteBuffer(column).array(), StandardCharsets.UTF_8));
                } else {
                    line.put(column, row.getObject(column));
                }
            }
            lines.add(line);
        }

        String[] var = {"x", "comment", "extra", "y"};
        //??????????????????
        List<String> columnNames = Arrays.asList(var);

        //????????????
        Map<String, Object> result = new HashMap<>();
        //??????????????????????????????List????????????
        Map<String, List<Object>> groupValue = new HashMap<>();

        //??????????????????, ?????????????????????????????????????????????????????????????????????????????????
        for (Map<String, Object> line : lines) {
            for (Map.Entry<String, Object> entry : line.entrySet()) {
                String key = entry.getKey();
                //???????????????
                if (!columnNames.contains(key)) {
                    if (!result.containsKey(key)) {
                        result.put(key, entry.getValue());
                    }
                }
                //????????????
                else {
                    if (!groupValue.containsKey(key)) {
                        groupValue.put(key, new ArrayList<>());
                    }
                    groupValue.get(key).addAll(Arrays.asList(TextUtil.parseJson(entry.getValue().toString(), Object[].class)));
                }
            }
        }

        //???????????????????????????????????????, ????????????????????????
        result.putAll(groupValue);
        long et = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("???????????????" + (et - st) + "ms");
        session.close();
    }
}
