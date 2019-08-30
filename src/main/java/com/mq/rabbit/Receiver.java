package com.mq.rabbit;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author LX
 * @Date 2019-8-28 10:35
 * @Description TODO
 */
@Component

@Slf4j
public class Receiver {

    @RabbitListener(queues ="message1")
    public void process(String Str) {

        log.info("=================================");
        log.info("接收消息message1：" + Str);

        log.info("接收消息时间message1：" + new Date());
    }

    @RabbitListener(queues ="message2")
    public void process2(String Str) {

        log.info("=================================");
        log.info("接收消息message2：" + Str);

        log.info("接收消息时间message2：" + new Date());
    }
}
