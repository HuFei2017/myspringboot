package com.learning.cassandra.write;

import java.util.*;

/**
 * @ClassName Main
 * @Description TODO
 * @Author hufei
 * @Date 2021/1/7 13:59
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Map<UUID, List<UUID>> ret = new HashMap<>();
        for (int i = 0; i < 1; i++) {
            UUID assetId = UUID.randomUUID();
            for (int j = 0; j < 1; j++) {
                UUID partId = UUID.randomUUID();

                WriteThread thread1 = new WriteThread(assetId, partId, "2020-01-01 00:00:00", "2020-04-01 00:00:00");
                WriteThread thread2 = new WriteThread(assetId, partId, "2020-04-01 00:00:00", "2020-07-01 00:00:00");
                WriteThread thread3 = new WriteThread(assetId, partId, "2020-07-01 00:00:00", "2020-10-01 00:00:00");
                WriteThread thread4 = new WriteThread(assetId, partId, "2020-10-01 00:00:00", "2021-01-01 00:00:00");

                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();

                thread1.join();
                thread2.join();
                thread3.join();
                thread4.join();

                if (!ret.containsKey(assetId))
                    ret.put(assetId, new ArrayList<>());
                ret.get(assetId).add(partId);
            }
        }
        for (Map.Entry<UUID, List<UUID>> entry : ret.entrySet()) {
            for (UUID item : entry.getValue())
                System.out.println("测点ID:" + entry.getKey() + ";部件ID:" + item);
        }
        long end = System.currentTimeMillis();
        System.out.println("写入耗时" + (end - start) + "ms");
    }
}
