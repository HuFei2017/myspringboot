package com.learning.cassandra.partitiontest;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.DefaultBatchType;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;

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
public class WritePerPointTest {

    public static void main(String[] args) {
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress("172.16.0.78", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.79", 9042));
        builder.addContactPoint(new InetSocketAddress("172.16.0.80", 9042));
        CqlSessionBuilder cluster = builder.withKeyspace("samplesetdata").withLocalDatacenter("datacenter1");
        CqlSession session = cluster.build();
        PreparedStatement prepare = session.prepare("INSERT INTO samplesetdata.test_vibdata2(id,datatype,x,comment,extra,name,props,xunit,y,yunit) VALUES (?,?,?,?,?,?,?,?,?,?)");
        long x = 1506276316000L;
        UUID id = UUID.randomUUID();
        for (int i = 0; i < 1000; i++) {
            BoundStatement[] binds = new BoundStatement[500];
            for (int j = 0; j < 500; j++) {
                List<Object> objList = new ArrayList<>(10);
                objList.add(id);
                objList.add(1);
                objList.add(x);
                objList.add("");
                objList.add("nosql:Cassandra:Case:samplesetdata:casedataset_wavedata:wid=0457d636-e4bb-f063-e018-5fb3e34d6af0");
                objList.add("XX指标");
                objList.add(ByteBuffer.wrap("[]".getBytes(StandardCharsets.UTF_8)));
                objList.add("ms");
                objList.add(5.045083522796631f);
                objList.add("m/s^2");
                binds[j] = prepare.bind(objList.toArray());
                x += 300000L;
            }
            session.execute(BatchStatement.newInstance(
                    DefaultBatchType.LOGGED,
                    binds));
        }
        session.close();
    }

}
