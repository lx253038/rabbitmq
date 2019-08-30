package com.mq.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Author LX
 * @Date 2019-8-28 14:09
 * @Description TODO
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("MsgSendConfirmCallBack  , 回调id:" + correlationData);
        if (b) {
            System.out.println("消息生产成功");
        } else {
            System.out.println("消息生产失败:" + s+"\n重新发送");
        }

    }
}
