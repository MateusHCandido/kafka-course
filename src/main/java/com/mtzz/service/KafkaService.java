package com.mtzz.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaService implements Closeable {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;


    //Overload Constructor
    KafkaService(String groupIdConfig, String topic, ConsumerFunction parse){
        this(groupIdConfig, parse);
        consumer.subscribe(Collections.singletonList(topic));
    }

    //Overload Constructor
    KafkaService(String groupId, Pattern topic, ConsumerFunction parse){
        this(groupId, parse);
        this.consumer.subscribe(topic);
    }

    //Main Constructor
    private KafkaService(String groupIdConfig, ConsumerFunction parse){
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(groupIdConfig));
    }

    void run() throws InterruptedException {
        while (true) {
            var records = this.consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("RECORDS FOUNDED: " + records.count());
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    /**
     * Method that configures Kafka consumer properties
     * Configure the following parameters
     *
     * - BOOTSTRAP_SERVERS_CONFIG: Kafka bootstrap server address.
     * - KEY_DESERIALIZER_CLASS_CONFIG:Deserialization class for the key of consumed messages.
     * - VALUE_DESERIALIZER_CLASS_CONFIG: Deserialization class for the value of consumed messages.
     * - GROUP_ID_CONFIG: Consumer group id.
     * - CLIENT_ID_CONFIG: Randomly generated customer id.
     *
     * @param groupId Consumer group id.
     * @return Properties object configured with Kafka consumer properties
     * */
    private static Properties properties(String groupId){
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        return properties;
    }

    @Override
    public void close(){
        consumer.close();
    }
}
