package com.mtzz;


import com.mtzz.entity.Order;
import com.mtzz.service.KafkaDispatcher;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try ( var orderDispatcher = new KafkaDispatcher<Order>() ){
            try (var emailDispatcher = new KafkaDispatcher<>()){
                for (int i = 0; i < 50; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, amount);

                    var email = "Welcome! We are proccessing your order!";
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
                }
            }
        }
    }

}