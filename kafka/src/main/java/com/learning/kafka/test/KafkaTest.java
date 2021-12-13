package com.learning.kafka.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.stream.Collectors;

public class KafkaTest {
    private static KafkaConsumer<String, String> getConsumer(String server) {
        Properties props = new Properties();
        props.put("bootstrap.servers", server);
        props.put("group.id", "kafka_test");
        props.put("enable.auto.commit", "true");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<>(props);
    }

    private static KafkaProducer<String, String> getProducer(String server) {
        Properties props = new Properties();
        props.put("bootstrap.servers", server);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    public static void consume(String server, String topic) {
        KafkaConsumer<String, String> consumer = getConsumer(server);
        List<PartitionInfo> partitions = consumer.partitionsFor(topic);
        List<TopicPartition> tps = partitions.stream().map(x -> new TopicPartition(x.topic(), x.partition())).collect(Collectors.toList());
        Map<TopicPartition, Long> beginoffsets = consumer.beginningOffsets(tps);
        Map<TopicPartition, Long> endoffsets = consumer.endOffsets(tps);
        for (TopicPartition tp : tps) {
            consumer.assign(new ArrayList<>(Collections.singletonList(tp)));
            consumer.seek(tp, beginoffsets.get(tp));
            consumer.commitSync();
            boolean iswhile = true;
            while (iswhile) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
                if (records.isEmpty())
                    iswhile = false;
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("key:" + record.key() + ";value:" + record.value());
                    if (record.offset() >= endoffsets.get(tp) - 1) {
                        iswhile = false;
                        break;
                    }
                }
            }
        }
        consumer.close();
    }

    public static void produce(String server, String topic) {
        KafkaProducer<String, String> producer = getProducer(server);
        int i = 0;
        while (i < 10000) {
            producer.send(new ProducerRecord<>(topic, String.valueOf(i), String.valueOf(i)));
            i++;
        }
    }
}
