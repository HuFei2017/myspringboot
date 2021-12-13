package com.learning.cassandra.write;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DriverTimeoutException;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.DefaultBatchType;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.servererrors.WriteTimeoutException;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.internal.core.type.UserDefinedTypeBuilder;
import com.learning.cassandra.util.TimeHelper;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName WriteThread
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/22 11:21
 * @Version 1.0
 */
public class WriteThread extends Thread {

    private UUID assetId;
    private UUID partId;
    private String startTime;
    private String endTime;
    private TimeHelper helper = new TimeHelper();
    private final UserDefinedType indexTypeUdt = getIndexTypeUdt();
    private final UserDefinedType widTypeUdt = new UserDefinedTypeBuilder("hufei", "widtype")
            .withField("vel", DataTypes.UUID)
            .withField("la", DataTypes.UUID)
            .withField("ha", DataTypes.UUID)
            .build();

    public WriteThread(UUID assetId, UUID partId, String startTime, String endTime) {
        this.assetId = assetId;
        this.partId = partId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void run() {
        int batch = 72;
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("192.168.1.14", 9042))
                .addContactPoint(new InetSocketAddress("192.168.1.15", 9042))
                .addContactPoint(new InetSocketAddress("192.168.1.16", 9042))
                .withKeyspace("hufei")
                .withLocalDatacenter("datacenter1")
                .build()) {

            PreparedStatement preparedStat = session.prepare(
                    "INSERT INTO hufei.dat_indexdatas_test_map_frozen_noindex(assetid,partid,measdate,condition,data,wid) VALUES (?,?,?,?,?,?)"
            );

            while (helper.compare(startTime, endTime) <= 0) {
                BoundStatement[] binds = new BoundStatement[batch];

                for (int i = 0; i < batch; i++) {
                    binds[i] = preparedStat.bind(
                            assetId,
                            partId,
                            Instant.parse(startTime.replace(" ", "T") + "Z"),
                            -1,
                            generateRandomMapData(),
                            generateRandomMapWid()
                    );
                    startTime = helper.addTime(startTime, 5);
                }

                boolean tag = true;
                while (tag) {
                    try {
                        session.execute(BatchStatement.newInstance(
                                DefaultBatchType.LOGGED,
                                binds).setTimeout(Duration.ofMinutes(1L)));
                        tag = false;
                    } catch (DriverTimeoutException | WriteTimeoutException ignored) {
                        Thread.sleep(1);
                    }
                }

                Thread.sleep(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Map<Integer, Float> generateRandomMapData() {
        Map<Integer, Float> map = new HashMap<>();
        for (int i = 0; i < 400; i++) {
            map.put(i + 1, (float) (Math.random() * 10.0f));
        }
        return map;
    }

    private Map<String, UUID> generateRandomMapWid() {
        Map<String, UUID> map = new HashMap<>();
        map.put("vel", UUID.randomUUID());
        map.put("la", UUID.randomUUID());
        map.put("ha", UUID.randomUUID());
        return map;
    }

    private UdtValue generateRandomData() {
        Object[] values = new Object[400];
        for (int i = 0; i < 400; i++) {
            values[i] = (float) (Math.random() * 10.0f);
        }
        return indexTypeUdt.newValue(values);
    }

    private UdtValue generateRandomWid() {
        Object[] values = new Object[3];
        for (int i = 0; i < 3; i++) {
            values[i] = UUID.randomUUID();
        }
        return widTypeUdt.newValue(values);
    }

    private UserDefinedType getIndexTypeUdt() {
        UserDefinedTypeBuilder udtBuilder = new UserDefinedTypeBuilder("hufei", "indextype");
        for (int i = 1; i <= 400; i++)
            udtBuilder.withField("t" + i, DataTypes.FLOAT);
        return udtBuilder.build();
    }
}
