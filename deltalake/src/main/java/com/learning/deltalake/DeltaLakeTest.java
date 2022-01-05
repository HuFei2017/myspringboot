package com.learning.deltalake;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @ClassName DeltaLakeTest
 * @Description TODO
 * @Author hufei
 * @Date 2022/1/5 15:24
 * @Version 1.0
 */
public class DeltaLakeTest {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\Files\\winutils\\hadoop-3.3.1\\bin");
        SparkConf sparkConf = new SparkConf();
        sparkConf.set("spark.hadoop.fs.s3a.access.key", "minio");
        sparkConf.set("spark.hadoop.fs.s3a.secret.key", "minio123");
        sparkConf.set("spark.hadoop.fs.s3a.endpoint", "http://192.168.1.179:9006");
        sparkConf.set("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension");
        sparkConf.set("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog");
        sparkConf.set("spark.network.timeout", "120s");
        sparkConf.set("spark.executor.heartbeatInterval", "10s");
        SparkSession sparkSession = SparkSession.builder().appName("test").master("local")
                .config(sparkConf).getOrCreate();
        //create table
//        sparkSession.range(0, 5).write().format("delta").save("s3a://tmp/firstdemo112");
        //read table
        Dataset<Row> df = sparkSession.read().format("delta").load("s3a://tmp/firstdemo4");
        df.show();
    }
}
