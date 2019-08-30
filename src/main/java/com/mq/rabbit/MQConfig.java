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
     * queue和Exchange进行绑定
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


//        直连交换价配置

//    /**
//     * DirectExchange:直连交换机
//     * 根据消息携带的路由键（routing key）将消息投递给对应队列的。直连交换机用来处理消息的单播路由（
//     */
//    @Bean
//    public DirectExchange exchange() {
//        DirectExchange directExchange = new DirectExchange(EXCHANGE, true, false);
//        return directExchange;
//    }
//
//    /**
//     * 直连交换机绑定队列需要指定一个路由键（routing key）
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


//        扇型交换机配置

//    /**
//     * Fanout exchange（扇型交换机）
//     * 将消息路由给绑定到它身上的所有队列，而不理会绑定的路由键。
//     * 如果N个队列绑定到某个扇型交换机上，当有消息发送给此扇型交换机时，交换机会将消息的拷贝分别发送给这所有的N个队列。
//     * 扇型用来交换机处理消息的广播路由（broadcast routing）。
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




//        主题交换机配置

    /**
     * Topic exchange（主题交换机）
     *主题交换机（topic exchanges）通过对消息的路由键和队列到交换机的绑定模式之间的匹配，将消息路由给一个或多个队列。
     * 主题交换机经常用来实现各种分发/订阅模式及其变种。主题交换机通常用来实现消息的多播路由（multicast routing）。
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
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());
        //template.setReturnCallback(msgSendReturnCallback());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        //  template.setMandatory(true);
        return template;
    }

    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack() {
        return new MsgSendConfirmCallBack();
    }


}

