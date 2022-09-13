package com.rocktech.orderservicekafka.controller;

import com.rocktech.basedomainkafka.model.Order;
import com.rocktech.basedomainkafka.model.OrderEvent;
import com.rocktech.orderservicekafka.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setMessage("Order now in progress...");
        orderEvent.setStatus("PENDING");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        return "Order Placed successfully...";
    }
}
