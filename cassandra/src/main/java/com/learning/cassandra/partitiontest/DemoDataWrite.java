package com.learning.cassandra.partitiontest;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.learning.cassandra.util.TextUtil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName DemoDataWrite
 * @Description TODO
 * @Author hufei
 * @Date 2022/1/6 15:24
 * @Version 1.0
 */
public class DemoDataWrite {
    public static void main(String[] args) {
        String str = getFileContent("C:\\Files\\test.json");
        Map var1 = TextUtil.parseJson(str, Map.class);
        Object value = var1.get("data");
        Map data = TextUtil.parseJson(TextUtil.fromJson(value), Map.class);
        List<String> comment = Arrays.asList(TextUtil.parseJson(TextUtil.fromJson(data.get("comment")), String[].class));
        List<String> extra = Arrays.asList(TextUtil.parseJson(TextUtil.fromJson(data.get("extra")), String[].class));
        List<Long> x = Arrays.asList(TextUtil.parseJson(TextUtil.fromJson(data.get("x")), Long[].class));
        List<Float> y = Arrays.asList(TextUtil.parseJson(TextUtil.fromJson(data.get("y")), Float[].class));
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress("172.16.0.78", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.79", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.80", 9042));
        CqlSessionBuilder cluster = builder.withKeyspace("samplesetdata").withLocalDatacenter("datacenter1");
        CqlSession session = cluster.build();
        PreparedStatement prepare = session.prepare("INSERT INTO samplesetdata.test_vibdata(id,datatype,seq,comment,extra,name,props,x,xunit,y,yunit) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        int start = 0;
        int size = x.size();
        int seq = 1;
        UUID id = UUID.fromString(data.get("id").toString());
        int type = Integer.parseInt(data.get("dataType").toString());
        String name = data.get("name").toString();
        while (start < size) {
            int end = Math.min(start + 10000, size);
            List<Object> objList = new ArrayList<>(10);
            objList.add(id);
            objList.add(type);
            objList.add(seq++);
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(comment.subList(start, end)).getBytes(StandardCharsets.UTF_8)));
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(extra.subList(start, end)).getBytes(StandardCharsets.UTF_8)));
            objList.add(name);
            objList.add(ByteBuffer.wrap("[]".getBytes(StandardCharsets.UTF_8)));
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(x.subList(start, end)).getBytes(StandardCharsets.UTF_8)));
            objList.add("ms");
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(y.subList(start, end)).getBytes(StandardCharsets.UTF_8)));
            objList.add("m/s^2");
            BoundStatement bind = prepare.bind(objList.toArray());
            session.execute(bind);
            start = end;
        }
        session.close();
    }

    private static String getFileContent(String path) {
        StringBuilder content = new StringBuilder();
        int flag;
        try (FileInputStream fis = new FileInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[10240];
            while ((flag = bis.read(buffer)) != -1) {
                content.append(new String(buffer, 0, flag, StandardCharsets.UTF_8));
            }
        } catch (Exception ignored) {
        }
        return content.toString();
    }
}
