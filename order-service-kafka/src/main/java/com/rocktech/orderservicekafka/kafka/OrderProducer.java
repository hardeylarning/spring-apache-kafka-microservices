package com.rocktech.orderservicekafka.kafka;

import com.rocktech.basedomainkafka.model.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {

    @Autowired
    private NewTopic topic;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendMessage(OrderEvent event) {
        log.info(String.format("OrderEvent => %s", event.toString()));

        Message<OrderEvent> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
