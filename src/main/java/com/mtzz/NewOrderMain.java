package com.mtzz;


import com.mtzz.service.KafkaDispatcher;
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
        try ( var kafkaDispatcher = new KafkaDispatcher() ){
            for (int i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                var value = key + "123, 01426871, 2100";
                var email = "Welcome! We are proccessing your order!";
                kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
                kafkaDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }

}