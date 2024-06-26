package com.mtzz.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;


public class LogService {

    public static void main(String[] args) throws InterruptedException {
        var logService = new LogService();
        try(var kafkaService = new KafkaService(LogService.class.getSimpleName(),Pattern.compile("ECOMMERCE.*")
                , logService::parse
                , String.class
                , Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record)  {
        System.out.println("--------------------------------------------------");
        System.out.println("LOG: " + record.topic());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Email sent");
    }

}
