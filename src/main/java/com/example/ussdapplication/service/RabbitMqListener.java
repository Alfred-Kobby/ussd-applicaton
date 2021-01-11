package com.example.ussdapplication.service;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqListener implements MessageListener{
    public void onMessage(Message message) {
        System.out.println("Consuming Transaction - " + new String(message.getBody()));
    }
}
