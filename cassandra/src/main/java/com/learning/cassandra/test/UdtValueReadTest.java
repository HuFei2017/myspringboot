package com.learning.cassandra.test;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DriverTimeoutException;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UdtValueReadTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/4/1 10:13
 * @Version 1.0
 */
public class UdtValueReadTest {

    public static void main(String[] args) {
        List<Long> times = new ArrayList<>();
        String[] filter = getFilterStr();
        ConsistencyLevel level = parseLevel(filter[4]);
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("192.168.1.14", 9042))
                .addContactPoint(new InetSocketAddress("192.168.1.15", 9042))
                .addContactPoint(new InetSocketAddress("192.168.1.16", 9042))
                .withKeyspace("hufei")
                .withLocalDatacenter("datacenter1")
                .build()) {
            for (int i = 0; i < 10; i++) {
                int counter = 1;
                long start = 0L;
                while (counter > 0) {
                    start = System.currentTimeMillis();
                    try {
                        //session.execute(String.format("SELECT assetid,partid,measdate,condition,%s,wid from hufei.dat_indexdatas_test WHERE assetid=ff535eab-bba1-4e2b-89ff-4332e79efdc2 and partid=ff535eab-bba1-4e2b-89ff-4332e79efdc2 and measdate='2020-01-01 18:18:59+0000'", getColumnStr(i + 1)));
                        //session.execute(String.format("SELECT assetid,partid,measdate,condition,%s,wid from hufei.dat_indexdatas_frozentest WHERE assetid=40005b43-618b-4334-b885-5eb993abf62a and partid=40005b43-618b-4334-b885-5eb993abf62a and measdate='2020-01-01 18:18:59+0000'", getColumnStr(i + 1)));
                        //session.execute(String.format("SELECT assetid,partid,measdate,condition,%s,wid from hufei.dat_indexdatas_test WHERE assetid=f0bd5d94-1a8e-4589-aeaf-021c637fffcd and partid=f0bd5d94-1a8e-4589-aeaf-021c637fffcd and measdate>='2020-01-01 00:00:00+0000' and measdate<='2021-01-01 00:00:00+0000'", getColumnStr(i + 1)));
                        //session.execute("SELECT assetid,partid,measdate,condition,data,wid from hufei.dat_indexdatas_test WHERE assetid=f0bd5d94-1a8e-4589-aeaf-021c637fffcd and partid=f0bd5d94-1a8e-4589-aeaf-021c637fffcd and measdate>='2020-01-01 00:00:00+0000' and measdate<='2020-01-13 00:00:00+0000'");
//                        session.execute(
//                                SimpleStatement.newInstance(
//                                        "SELECT data,wid from hufei.dat_indexdatas_test_frozen_noindex" +
//                                                " WHERE assetid = 07a1bc0b-c02d-4ad2-8cdd-1ff7ae50ef00" +
//                                                " and partid = a6da6dfa-a520-43f7-9837-b0778c74861a" +
//                                                " and measdate>='2020-01-01 00:00:00+0000' and measdate<='2021-01-01 00:00:00+0000'"
//                                ).setTimeout(Duration.ofMinutes(1L))
//                        );
                        ResultSet ret = session.execute(
                                SimpleStatement.newInstance(
                                        "SELECT data" + filter[3] + " as value,wid from hufei.dat_indexdatas_test_" + filter[2] +
                                                " WHERE assetid " + filter[0] +
                                                " and partid " + filter[1] +
                                                " and measdate>='2020-01-01 00:00:00+0000' and measdate<='2021-01-01 00:00:00+0000'"
                                ).setTimeout(Duration.ofMinutes(1L)).setConsistencyLevel(level)
                        );
//                        session.execute(
//                                SimpleStatement.newInstance(
//                                        "SELECT * from hufei.dat_indexdatas_test where assetid = 4c5ad5a2-00da-4d66-a331-60dd25f374f9 allow filtering"
//                                ).setTimeout(Duration.ofMinutes(1L))
//                        );
                        for(Row row: ret){
                            row.getObject("value");
                        }
                    } catch (DriverTimeoutException ignored) {
                        counter++;
                    }
                    counter--;
                }
                long end = System.currentTimeMillis();
                System.out.println("读取耗时" + (end - start) + "ms");
                times.add(end - start);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("读取耗时" + avg(times) + "ms");
    }

    private static float avg(List<Long> values) {

        int size = values.size();

        if (size <= 0)
            return 0.0f;

        long sum = 0L;

        for (Long value : values)
            sum += value;

        return 1.0f * sum / size;
    }

    private static String getYearMonth(int i) {

        if (i < 10)
            return "2020-0" + i + "-01 00:00:00+0000";
        else if (i < 13)
            return "2020-" + i + "-01 00:00:00+0000";
        else
            return "2021-01-01 00:00:00+0000";

    }

    private static String getColumnStr(int max) {
        String[] str = new String[max];
        for (int i = 1; i <= max; i++) {
            str[i - 1] = "data.t" + i;
        }
        return String.join(",", str);
    }

    private static String[] getFilterStr() {

        List<String> points = new ArrayList<>();
        List<String> parts = new ArrayList<>();
        String[] ret = new String[5];
        String part = "";
        String single = "";
        String mode = "";

        try {
            Reader in = new FileReader("/var/cassandra_test.json");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                String pointId = record.get("pointId");
                String partId = record.get("partId");
                if (!points.contains(pointId))
                    points.add(pointId);
                if (!parts.contains(partId))
                    parts.add(partId);
                if (part.isEmpty())
                    part = record.get("part");
                if (single.isEmpty())
                    single = record.get("single");
                if (mode.isEmpty())
                    mode = record.get("mode");
            }
        } catch (IOException ignored) {
        }

        ret[0] = getString(points.toArray());
        ret[1] = getString(parts.toArray());
        ret[2] = part;
        ret[3] = Boolean.parseBoolean(single) ? ".t135" : "";
        ret[4] = mode;

        return ret;
    }

    private static String getString(Object[] objs) {
        if (objs.length <= 1)
            return "= " + objs[0].toString();
        else
            return "in (" + StringUtils.join(objs, ",") + ")";
    }

    private static ConsistencyLevel parseLevel(String mode) {
        if (mode.equals("QUORUM"))
            return ConsistencyLevel.QUORUM;
        if (mode.equals("LOCAL_QUORUM"))
            return ConsistencyLevel.LOCAL_QUORUM;
        return ConsistencyLevel.LOCAL_ONE;
    }

}
