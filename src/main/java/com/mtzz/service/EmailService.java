package com.mtzz.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;


public class EmailService {

    public static void main(String[] args) throws InterruptedException {
        var emailService = new EmailService();
        var kafkaService = new KafkaService(EmailService.class.getSimpleName()
                                            ,"ECOMMERCE_SEND_EMAIL"
                                            , emailService::parse);

        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, String> record)  {
        System.out.println("--------------------------------------------------");
        System.out.println("SENDING EMAIL, CHECKING FOR FRAUD");
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
