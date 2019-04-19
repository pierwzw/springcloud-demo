package com.pier.mq.rabbitmq;

import com.pier.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 16:29
 */
@EnableBinding({OutputProcess.class, Source.class}) // 可以理解为是一个消息的发送管道的定义 之前为Source.class
public class MessageProviderImpl implements IMessageProvider {

    /*@Bean(name = "routerChannel")
    public MessageChannel routerChannel() {
        return new DirectChannel();
    }*/

    @Resource/*(name="routerChannel")*/
    @Qualifier("output1") // 为了防止出现多个可选注入导致的错误
    private MessageChannel channel; // 消息的发送管道

    @Resource
    @Qualifier(Source.OUTPUT)
    private MessageChannel kafkaChannel;

    @Override
    public void send(User user) {
        channel.send(MessageBuilder.withPayload(user).build()); // 创建并发送消息
    }

    @Override
    public void send(String msg) {
        channel.send(MessageBuilder.withPayload(msg).build()); // 创建并发送消息
    }

    @Override
    public void sendKafka(String msg){
        kafkaChannel.send(MessageBuilder.withPayload(msg).build()); // 创建并发送消息
    }
}
