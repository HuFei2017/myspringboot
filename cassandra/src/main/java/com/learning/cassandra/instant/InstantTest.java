package com.learning.cassandra.instant;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.time.Instant;

/**
 * @ClassName InstantTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/4/15 11:14
 * @Version 1.0
 */
public class InstantTest {

    public static void main(String[] args) throws Exception {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("172.16.3.1", 9042))
                .addContactPoint(new InetSocketAddress("172.16.3.2", 9042))
                .addContactPoint(new InetSocketAddress("172.16.3.3", 9042))
                .addContactPoint(new InetSocketAddress("172.16.3.4", 9042))
                .withKeyspace("rzbigdata")
                .withLocalDatacenter("datacenter1")
                .build()) {

            ResultSet ret = session.execute("SELECT * FROM dat_indexdatas where assetid=9b82057f-6d7b-7b5c-db92-0b75f7408211 and datatype=202 and measdate='2020-10-30 10:15:00+0000'");

            Row row = ret.one();

            Instant m = row.getInstant("measdate");

            System.out.println(m.toEpochMilli());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
