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
        System.out.println("MsgSendConfirmCallBack  , �ص�id:" + correlationData);
        if (b) {
            System.out.println("��Ϣ�����ɹ�");
        } else {
            System.out.println("��Ϣ����ʧ��:" + s+"\n���·���");
        }

    }
}
