package com.mtzz.service;


import com.mtzz.entity.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;



public class FraudDetectorService {

    public static void main(String[] args) throws InterruptedException {
        var fraudService = new FraudDetectorService();

        try(var kafkaService = new KafkaService<>(FraudDetectorService.class.getSimpleName()
                , "ECOMMERCE_NEW_ORDER"
                , fraudService::parse
                , Order.class)) {

            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("--------------------------------------------------");
        System.out.println("PROCESSING NEW ORDER, CHECKING FOR FRAUD");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ORDER PROCESSED");
    }

}
