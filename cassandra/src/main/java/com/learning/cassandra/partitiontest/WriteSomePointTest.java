package com.learning.cassandra.partitiontest;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.learning.cassandra.util.TextUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName WriteTest
 * @Description TODO
 * @Author hufei
 * @Date 2022/1/6 11:28
 * @Version 1.0
 */
public class WriteSomePointTest {

    public static void main(String[] args) {
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress("172.16.0.78", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.79", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.80", 9042));
        CqlSessionBuilder cluster = builder.withKeyspace("samplesetdata").withLocalDatacenter("datacenter1");
        CqlSession session = cluster.build();
        PreparedStatement prepare = session.prepare("INSERT INTO samplesetdata.test_vibdata(id,datatype,seq,comment,extra,name,props,x,xunit,y,yunit) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        int size = 500000;
        int k = 1;
        UUID id = UUID.randomUUID();
        for (int i = 0; i < 1; i++) {
            List<Object> objList = new ArrayList<>(10);
            objList.add(id);
            objList.add(3);
            objList.add(k++);
            List<String> comment = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                comment.add("");
            }
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(comment).getBytes(StandardCharsets.UTF_8)));
            List<String> extra = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                extra.add("nosql:Cassandra:Case:samplesetdata:casedataset_wavedata:wid=0457d636-e4bb-f063-e018-5fb3e34d6af0");
            }
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(extra).getBytes(StandardCharsets.UTF_8)));
            objList.add("XX指标");
            objList.add(ByteBuffer.wrap("[]".getBytes(StandardCharsets.UTF_8)));
            List<Long> x = new ArrayList<>();
            long x0 = 1506276316000L;
            for (int j = 0; j < size; j++) {
                x.add(x0);
                x0 += 300000L;
            }
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(x).getBytes(StandardCharsets.UTF_8)));
            objList.add("ms");
            List<Float> y = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                y.add(5.045083522796631f);
            }
            objList.add(ByteBuffer.wrap(TextUtil.fromJson(y).getBytes(StandardCharsets.UTF_8)));
            objList.add("m/s^2");
            BoundStatement bind = prepare.bind(objList.toArray());
            session.executeAsync(bind);
        }
        session.close();
    }

}
