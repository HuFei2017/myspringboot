package com.learning.cassandra.test;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.web.client.RestClientException;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CassandraMigrationAuto
 * @Description TODO
 * @Author hufei
 * @Date 2021/2/24 20:17
 * @Version 1.0
 */
public class CassandraMigrationManual {

    public static void main(String[] args) {

        //连接Cassandra
        CqlSessionBuilder cluster;
        CqlSession session = null;
        //连接备用Cassandra
        CqlSessionBuilder cluster_bakup;
        CqlSession session_bakup = null;
        Date date1 = new Date();

        try {
            //获取风电、非风电设备清单
            HttpResponse response_FD = HttpUtils.doGet("http://172.16.0.77:50001/api/Device/FD", null, HttpResponse.class);
            HttpResponse response_SH = HttpUtils.doGet("http://172.16.0.77:50001/api/Device/!FD", null, HttpResponse.class);

            List<String> deviceList = new ArrayList<>();
            deviceList.addAll(response_FD.getData());
            deviceList.addAll(response_SH.getData());
            int size = deviceList.size();

            //获取需要备份的工作流清单
            //509c605e-cd70-43cb-b2bc-010553a548a6		fd_alm_算法A版		    有       已迁移
            //935b89e9-1a15-41ad-9f4b-f669ca31d869		石化离线_指标报警		有       已迁移
            //bfbf3ab5-b558-4f27-9bd0-d4f36c3eea70		风电A版离线拷贝项目报警	有       已迁移
            //6080afd9-f939-4cfd-a3f8-efd50401f6a5		SH_AL算法A版		    有
            //59d670ba-d9f3-4c40-b023-1fa1b3e5d37f		石化功能测试-报警		有       <keyspace需要调整>
            //String[] workflows = {"509c605e-cd70-43cb-b2bc-010553a548a6", "935b89e9-1a15-41ad-9f4b-f669ca31d869",
            // "bfbf3ab5-b558-4f27-9bd0-d4f36c3eea70", "6080afd9-f939-4cfd-a3f8-efd50401f6a5", "59d670ba-d9f3-4c40-b023-1fa1b3e5d37f"};
            String[] workflows = {"509c605e-cd70-43cb-b2bc-010553a548a6"};

            String[] ips = {"172.16.3.1", "172.16.3.2", "172.16.3.3", "172.16.3.4"};
            String[] ips_bakup = {"172.16.3.101", "172.16.3.102", "172.16.3.103", "172.16.3.104"};
            //Cassandra建立连接
            cluster = getCluster(ips);
            session = cluster.build();
            //备用Cassandra建立连接
            cluster_bakup = getCluster(ips_bakup);
            session_bakup = cluster_bakup.build();
            //连接指向给定的keyspace
            String sql = "use rzbigdata";
            session.execute(sql);
            session_bakup.execute(sql);

            ResultSet resultSet;

            System.out.println("开始buffer迁移");
            int var = 1;
            for (String workflowid : workflows) {
                System.out.println("迁移第" + (var++) + "个工作流数据");
                System.out.println("设备数量：" + size + "个");
                int tmp = size;
                for (String deviceid : deviceList) {
                    sql = "select * from dat_algorithm_cache where workflowid=" + workflowid + " and assetid=" + deviceid;
                    resultSet = session.execute(sql);
                    for (Row row : resultSet) {
                        Object wkid = row.getObject("workflowid");
                        Object asset = row.getObject("assetid");
                        if (null != wkid && null != asset)
                            session_bakup.execute(String.format("insert into dat_algorithm_cache(workflowid,assetid,buffer,metadata) VALUES (%s,%s,'%s','%s')",
                                    wkid.toString(), asset.toString(), row.getString("buffer"), row.getString("metadata")));
                    }
                    System.out.println("剩余" + (--tmp) + "个设备");
                }
            }
            //关闭Cassandra集群连接
            session.close();
            session_bakup.close();
        } catch (RestClientException re) {
            System.out.println("获取设备列表接口出现错误");
            re.printStackTrace();
        } catch (Exception ex) {
            System.out.println("sql执行错误,请检查Cassandra连接");
            ex.printStackTrace();
            if (session != null)
                session.close();
            if (session_bakup != null)
                session_bakup.close();
        }

        Date date2 = new Date();
        System.out.println("共耗时：" + (date2.getTime() - date1.getTime()) + "ms");
    }

    private static CqlSessionBuilder getCluster(String[] ips) {

        CqlSessionBuilder builder = CqlSession.builder();

        for (String ip : ips) {
            builder.addContactPoint(new InetSocketAddress(ip, 9042));
        }

        return builder.withKeyspace("rzbigdata").withLocalDatacenter("datacenter1");
    }
}
