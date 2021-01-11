package com.example.ussdapplication.service;
import com.example.ussdapplication.model.Transaction;
import com.example.ussdapplication.model.UssdResponse;
import com.example.ussdapplication.model.UssdSession;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMqSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${javainuse.rabbitmq.exchange}")
    private String exchange;

    @Value("${javainuse.rabbitmq.routingkey}")
    private String routingkey;
    public void send(UssdSession ussdSession) {
        rabbitTemplate.convertAndSend(exchange, routingkey, ussdSession);
        System.out.println("Transaction successful" );

    }
}
