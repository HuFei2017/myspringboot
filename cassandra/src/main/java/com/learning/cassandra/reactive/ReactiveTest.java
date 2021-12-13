package com.learning.cassandra.reactive;

import com.datastax.dse.driver.api.core.cql.reactive.ReactiveResultSet;
import com.datastax.dse.driver.api.core.cql.reactive.ReactiveRow;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.DriverException;
import com.datastax.oss.driver.api.core.cql.ColumnDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ReactiveTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/17 15:59
 * @Version 1.0
 */
public class ReactiveTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        try (CqlSession session = getSession()) {

            ReactiveResultSet ret = session.executeReactive("SELECT * FROM dat_indexdatas limit 10");
            Flux<ReactiveRow> rows = Flux.from(ret).cache();
            //Flux.from(ret).blockLast();
//            List<String> columns = Mono.from(ret.getColumnDefinitions()).map(definitions -> {
//                List<String> list = new ArrayList<>();
//                for (ColumnDefinition definition : definitions) {
//                    list.add(definition.getName().toString());
//                }
//                return list;
//            }).block();

            List<String> columns = getColumns(rows.blockFirst());

            List<Map<String, Object>> list = rows.map(row -> {
                Map<String, Object> data = new HashMap<>();
                //遍历每一列
                for (String column : columns) {
                    Object obj = row.getObject(column);
                    if (null == obj) {
                        data.put(column, null);
                    } else {
                        if (obj instanceof Instant) {
                            Instant time = row.getInstant(column);
                            if (null != time)
                                //data.put(column, time.atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime().toString().replace("T", " "));
                                data.put(column, time.toString().replace("T", " ").replace("Z", ""));
                        } else if (obj instanceof ByteBuffer) {
                            ByteBuffer bytes = row.getByteBuffer(column);
                            if (null != bytes)
                                data.put(column, bytes.array());
                        } else {
                            data.put(column, obj);
                        }
                    }
                }
                return data;
            }).collectList().block();

            System.out.println(mapper.writeValueAsString(list));
            System.out.println();
        } catch (DriverException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getColumns(ReactiveRow row) {

        List<String> list = new ArrayList<>();

        if (null == row)
            return list;

        for (ColumnDefinition definition : row.getColumnDefinitions()) {
            list.add(definition.getName().toString());
        }

        return list;
    }

    private static CqlSession getSession() {
        String[] ips = {"172.16.3.1", "172.16.3.2", "172.16.3.3", "172.16.3.4"};
        CqlSessionBuilder builder = CqlSession.builder();
        for (String ip : ips) {
            if (ip.length() > 0)
                builder.addContactPoint(new InetSocketAddress(ip, 9042));
        }
        builder.withKeyspace("rzbigdata");
        builder.withLocalDatacenter("datacenter1");

        return builder.build();
    }

}
