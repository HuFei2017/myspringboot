package com.learning.datasource.configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import org.springframework.stereotype.Service;

@Service
public class CassandraPool {
    private Cluster cluster=null;
    private static CassandraPool instance=new CassandraPool();
    private CassandraPool(){
        if(cluster == null)
            cluster = createCluster();
    }
    public static CassandraPool getInstance(){
        return instance;
    }
    private Cluster createCluster(){
        PoolingOptions poolingOptions = new PoolingOptions();
        // 每个连接的最大请求数 2.0的驱动好像没有这个方法
        poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, 32);
        // 表示和集群里的机器至少有2个连接 最多有4个连接
        poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 2).setMaxConnectionsPerHost(HostDistance.LOCAL, 4);
        return Cluster.builder()
                .addContactPoints("10.189.2.16")//node04.jinbo.com
//                .addContactPoint("172.16.4.201")//node00.ronds.com
//                .addContactPoint("10.74.3.166")//node00.hnfd.com
                .withPort(9042)
                .withPoolingOptions(poolingOptions)
                .build();
    }

    public Session getSession() {
        return cluster.connect();
    }
}
