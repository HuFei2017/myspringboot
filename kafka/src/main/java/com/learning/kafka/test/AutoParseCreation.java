package com.learning.kafka.test;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.ConfigResource;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @ClassName AutoParseCreation
 * @Description TODO
 * @Author hufei
 * @Date 2021/5/13 14:11
 * @Version 1.0
 */
public class AutoParseCreation {
    public static void main(String[] args) {
        List<String> creation = new ArrayList<>();
        List<String> alter = new ArrayList<>();
        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.4.221:6667,172.16.4.222:6667,172.16.4.223:6667,172.16.4.224:6667,172.16.4.225:6667,172.16.4.226:6667");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092,kafka02:9092,kafka03:9092,kafka04:9092,kafka05:9092,kafka06:9092,kafka07:9092,kafka08:9092,kafka09:9092");
        try (AdminClient client = KafkaAdminClient.create(props)) {
            Set<String> topicsSet = client.listTopics().names().get();
            List<String> topics = topicsSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            for (String topic : topics) {
                String retention = "";
                ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, topic);
                Collection<ConfigEntry> list = client.describeConfigs(Collections.singleton(configResource)).all().get().get(configResource).entries();
                TopicDescription description = client.describeTopics(Collections.singletonList(topic)).all().get().get(topic);
                for (ConfigEntry item : list) {
                    String name = item.name();
                    if (name.equals("retention.ms"))
                        retention = item.value();
                }
                creation.add("./kafka-topics.sh --zookeeper localhost:2181 --create --topic " + topic + " --partitions " + description.partitions().size() + " --replication-factor 3 --if-not-exists");
                if (!retention.isEmpty()) {
                    alter.add("./kafka-configs.sh --zookeeper localhost:2181 --entity-type topics --entity-name " + topic + " --alter --add-config retention.ms=" + retention);
                }
            }
            System.out.println("#####################################################################");
            for(String m : creation){
                System.out.println(m);
            }
            System.out.println("#####################################################################");
            for(String m : alter){
                System.out.println(m);
            }
            System.out.println("#####################################################################");
        } catch (ExecutionException | InterruptedException exe) {
            System.out.println("get topic configurations failed");
        }
    }
}
