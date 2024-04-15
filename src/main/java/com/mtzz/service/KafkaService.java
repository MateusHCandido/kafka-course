package com.mtzz.service;

import com.mtzz.service.serializers.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;


    //Overload Constructor
    KafkaService(String groupIdConfig, String topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties){
        this(groupIdConfig, parse, type, properties);
        consumer.subscribe(Collections.singletonList(topic));
    }

    //Overload Constructor
    KafkaService(String groupId, Pattern topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties){
        this(groupId, parse, type, properties);
        this.consumer.subscribe(topic);
    }

    //Main Constructor
    private KafkaService(String groupIdConfig, ConsumerFunction parse, Class<T> type, Map<String, String> properties){
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getProperties(type, groupIdConfig, properties));
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
     * Generates properties for configuring a Kafka consumer.
     * Configures the following properties:
     * - BOOTSTRAP_SERVERS_CONFIG: Kafka bootstrap server address.
     * - KEY_DESERIALIZER_CLASS_CONFIG: Key deserializer class for consumed messages.
     * - VALUE_DESERIALIZER_CLASS_CONFIG: Value deserializer class for consumed messages.
     * - GROUP_ID_CONFIG: Consumer group ID.
     * - CLIENT_ID_CONFIG: Randomly generated client ID.
     * - TYPE_CONFIG: Type configuration for Gson deserializer.
     *
     * @param type              The type class for deserialization.
     * @param groupId           The consumer group ID.
     * @param overrideProperties Additional properties to override or extend the default configuration.
     * @return Properties object configured for Kafka consumer.
     */
    private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties){
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        properties.putAll(overrideProperties);
        return properties;
    }

    @Override
    public void close(){
        consumer.close();
    }
}
