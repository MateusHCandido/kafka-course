package com.mtzz.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;



public class FraudDetectorService {

    public static void main(String[] args) throws InterruptedException {
        var fraudService = new FraudDetectorService();

        var kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName()
                                                , "ECOMMERCE_NEW_ORDER"
                                                , fraudService::parse);

        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
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
