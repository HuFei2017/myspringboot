package com.learning.kafka.test;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName TopicPartitionMain
 * @Description TODO
 * @Author hufei
 * @Date 2021/3/16 12:43
 * @Version 1.0
 */
public class TopicPartitionMain {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092,kafka02:9092,kafka03:9092,kafka04:9092,kafka05:9092,kafka06:9092,kafka07:9092,kafka08:9092,kafka09:9092");
        try (AdminClient client = KafkaAdminClient.create(props)) {
            Set<String> topics = client.listTopics().names().get();
            Map<String, TopicDescription> descriptions = client.describeTopics(topics).all().get();
            for (Map.Entry<String, TopicDescription> entry : descriptions.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue().partitions().size());
            }
        } catch (ExecutionException | InterruptedException exe) {
            System.out.println("get topics failed");
        }
    }
}
