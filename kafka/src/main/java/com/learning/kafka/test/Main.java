package com.learning.kafka.test;

/**
 * @ClassName Main
 * @Description TODO
 * @Author hufei
 * @Date 2020/11/4 18:23
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args){
//        String server = "node01.xuyang.com:6667,node02.xuyang.com:6667,node03.xuyang.com:6667";
//        String topic = "tests";
//        KafkaTest.consume(server,topic);
        String server = "node01.rz.com:6667,node02.rz.com:6667,node03.rz.com:6667";
        String topic = "test_speed";
        KafkaTest.produce(server,topic);
    }
}
