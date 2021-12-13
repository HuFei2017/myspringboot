package com.learning.cassandra.controller;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CassanStressController
 * @Description TODO
 * @Author hufei
 * @Date 2021/4/15 15:04
 * @Version 1.0
 */
@RestController
public class CassandraStressController {

    private CqlSession session;
    private int counter;

    public CassandraStressController() {
        this.session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("192.168.1.14", 9042))
                .addContactPoint(new InetSocketAddress("192.168.1.15", 9042))
                .addContactPoint(new InetSocketAddress("192.168.1.16", 9042))
                .withKeyspace("hufei")
                .withLocalDatacenter("datacenter1")
                .build();
    }

    @GetMapping("/test")
    public List<Object> test() {
        List<Object> list = new ArrayList<>();
        ResultSet ret = session.execute(
                SimpleStatement.newInstance(
                        "SELECT data.t123 as value,wid from hufei.dat_indexdatas_test_frozen_noindex" +
                                " WHERE assetid = 3504c026-7744-4ffd-9134-062ab211f4d4" +
                                " and partid = d8b30c34-2828-40cc-af5b-bacdece4f3d8" +
                                " and measdate>='2020-01-01 00:00:00+0000' and measdate<='2021-01-01 00:00:00+0000'"
                ).setTimeout(Duration.ofMinutes(1L))
        );
        for (Row row : ret) {
            list.add(row.getObject("value"));
        }
        return list;
    }

}
