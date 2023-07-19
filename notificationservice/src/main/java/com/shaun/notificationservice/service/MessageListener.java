package com.shaun.notificationservice.service;

import com.shaun.notificationservice.config.MQConfig;
import com.shaun.notificationservice.dto.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(Notification notification)
    {
        System.out.println(notification);
    }

}
