package com.mq.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LX
 * @Date 2019-8-28 10:44
 * @Description TODO
 */
@Configuration
public class MQConfig {

    /**
     * EXchange
     */
    public static final String EXCHANGE = "exchangeTest";
    /**
     * Queue
     */
    public static final String QUEUE1 = "message1";
    public static final String QUEUE2 = "message2";
    /**
     * queue��Exchange���а�
     */
    public static final String ROUTINGKEY1 = "queuekey1";
    public static final String ROUTINGKEY2 = "queuekey2";


    @Bean
    public Queue queue1() {
        return new Queue(QUEUE1, true);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE2, true);
    }


//        ֱ������������

//    /**
//     * DirectExchange:ֱ��������
//     * ������ϢЯ����·�ɼ���routing key������ϢͶ�ݸ���Ӧ���еġ�ֱ������������������Ϣ�ĵ���·�ɣ�
//     */
//    @Bean
//    public DirectExchange exchange() {
//        DirectExchange directExchange = new DirectExchange(EXCHANGE, true, false);
//        return directExchange;
//    }
//
//    /**
//     * ֱ���������󶨶�����Ҫָ��һ��·�ɼ���routing key��
//     *
//     * @return
//     */
//    @Bean
//    public Binding binding_one() {
//        return BindingBuilder.bind(queue1()).to(exchange()).with(ROUTINGKEY1);
//    }
//
//    @Bean
//    public Binding binding_two() {
//        return BindingBuilder.bind(queue2()).to(exchange()).with(ROUTINGKEY2);
//    }


//        ���ͽ���������

//    /**
//     * Fanout exchange�����ͽ�������
//     * ����Ϣ·�ɸ��󶨵������ϵ����ж��У��������󶨵�·�ɼ���
//     * ���N�����а󶨵�ĳ�����ͽ������ϣ�������Ϣ���͸������ͽ�����ʱ���������Ὣ��Ϣ�Ŀ����ֱ��͸������е�N�����С�
//     * ��������������������Ϣ�Ĺ㲥·�ɣ�broadcast routing����
//     *
//     * @return
//     */
//    @Bean
//    public FanoutExchange exchange() {
//        FanoutExchange fanoutExchange = new FanoutExchange(EXCHANGE, true, false);
//        return fanoutExchange;
//    }
//
//    @Bean
//    public Binding binding_one() {
//        return BindingBuilder.bind(queue1()).to(exchange());
//    }
//
//    @Bean
//    public Binding binding_two() {
//        return BindingBuilder.bind(queue2()).to(exchange());
//    }




//        ���⽻��������

    /**
     * Topic exchange�����⽻������
     *���⽻������topic exchanges��ͨ������Ϣ��·�ɼ��Ͷ��е��������İ�ģʽ֮���ƥ�䣬����Ϣ·�ɸ�һ���������С�
     * ���⽻������������ʵ�ָ��ַַ�/����ģʽ������֡����⽻����ͨ������ʵ����Ϣ�Ķಥ·�ɣ�multicast routing����
     * @return
     */
    @Bean
    public TopicExchange exchange() {
        TopicExchange topicExchange = new TopicExchange(EXCHANGE, true, false);
        return topicExchange;
    }

    @Bean
    public Binding binding_one() {
        return BindingBuilder.bind(queue1()).to(exchange()).with("abcd.#");
    }

    @Bean
    public Binding binding_two() {
        return BindingBuilder.bind(queue2()).to(exchange()).with("abcd.*");
    }

    /**
     * ���ӹ���
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /**��ʹ��confirm-callback��return-callback��
         * ����Ҫ����publisherConfirms��publisherReturnsΪtrue
         * ÿ��rabbitTemplateֻ����һ��confirm-callback��return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());
        //template.setReturnCallback(msgSendReturnCallback());
        /**
         * ʹ��return-callbackʱ��������mandatoryΪtrue������������������mandatory-expression��ֵΪtrue��
         * �����ÿ���������Ϣȥȷ����mandatory����booleanֵ��
         * ֻ�����ṩ��return -callback��ʱʹ�ã���mandatory����
         */
        //  template.setMandatory(true);
        return template;
    }

    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack() {
        return new MsgSendConfirmCallBack();
    }


}

