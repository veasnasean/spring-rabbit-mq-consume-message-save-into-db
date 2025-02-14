package org.vsskh.rabbitmqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vsskh.rabbitmqdemo.model.Invoice;

import java.util.List;

@RestController
@RequestMapping("/publish")
public class BatchUploadController {
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;
    @Value("${rabbitmq.queue.exchange}")
    private String queueExchange;
    private final RabbitTemplate rabbitTemplate;
    public BatchUploadController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @PostMapping
    public String sendMessage(@RequestBody List<Invoice> invoices) {
        rabbitTemplate.convertAndSend(queueExchange, routingJsonKey, invoices);
        return "Message sent to RabbitMQ!";
    }
}
