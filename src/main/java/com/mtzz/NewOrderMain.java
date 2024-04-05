package com.mtzz;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>( properties() );

        for (int i = 0; i < 10; i++) {
            var key = UUID.randomUUID().toString();
            var value = key + "123, 01426871, 2100";
            var email = "Welcome! We are proccessing your order!";

            var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, email);


            producer.send(record, getCallback()).get();
            producer.send(emailRecord, getCallback()).get();
        }
    }

    protected static Callback getCallback() {
        return (data, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }

            System.out.println("SENT SUCCESS"
                    + data.topic()
                    + " | partition: "
                    + data.partition()
                    + " |offset: "
                    + data.offset()
                    + " |timestamp: " +
                    data.timestamp());
        };
    }

    private static Properties properties(){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}