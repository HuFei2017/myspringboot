package com.learning.cassandra.partitiontest;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

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
public class ReadPerPointTest {
    public static void main(String[] args) {
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress("172.16.0.78", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.79", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.80", 9042));
        CqlSessionBuilder cluster = builder.withKeyspace("samplesetdata").withLocalDatacenter("datacenter1");
        CqlSession session = cluster.build();
        long st = System.currentTimeMillis();
        List<Map<String, Object>> lines = new ArrayList<>();
        ResultSet data = session.execute("SELECT * FROM samplesetdata.test_vibdata2 WHERE id=3965cf4e-4ceb-46ed-a7fc-fc1fd50fda50 AND datatype=1 limit 40000");
        String[] columns = {"id", "datatype", "x", "comment", "extra", "name", "props", "xunit", "y", "yunit"};
        for (Row row : data) {
            Map<String, Object> line = new HashMap<>();
            for (String column : columns) {
                if (column.equals("props")) {
                    line.put(column, new String(row.getByteBuffer(column).array(), StandardCharsets.UTF_8));
                } else {
                    line.put(column, row.getObject(column));
                }
            }
            lines.add(line);
        }

        String[] var = {"x", "comment", "extra", "y"};
        //要聚合的属性
        List<String> columnNames = Arrays.asList(var);

        //最终结果
        Map<String, Object> result = new HashMap<>();
        //聚合的属性的内容先用List缓存一下
        Map<String, List<Object>> groupValue = new HashMap<>();

        //遍历原始数据, 组装数据：未聚合的属性只取第一行的，聚合的属性放缓存里
        for (Map<String, Object> line : lines) {
            for (Map.Entry<String, Object> entry : line.entrySet()) {
                String key = entry.getKey();
                //未聚合属性
                if (!columnNames.contains(key)) {
                    if (!result.containsKey(key)) {
                        result.put(key, entry.getValue());
                    }
                }
                //聚合属性
                else {
                    if (!groupValue.containsKey(key)) {
                        groupValue.put(key, new ArrayList<>());
                    }
                    groupValue.get(key).add(entry.getValue());
                }
            }
        }

        //缓存里的数据放回最终数据里, 补充聚合属性的值
        result.putAll(groupValue);
        long et = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("花费时间：" + (et - st) + "ms");
        session.close();
    }
}
