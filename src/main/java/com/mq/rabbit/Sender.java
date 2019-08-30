package com.mq.rabbit;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


/**
 * @Author LX
 * @Date 2019-8-28 10:26
 * @Description TODO
 */
@Component
@Slf4j
public class Sender {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String send() {
        String context = "����Ϣ����";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("����Ϣ����ʱ�䣺" + new Date());
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTINGKEY1, context, correlationData);
        return "���ͳɹ�";
    }

    public String send2() {
        String context = "����Ϣ����66666666666666";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("����Ϣ����ʱ�䣺" + new Date());
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, "abcd.123888", context, correlationData);
        return "���ͳɹ�";
    }
}
