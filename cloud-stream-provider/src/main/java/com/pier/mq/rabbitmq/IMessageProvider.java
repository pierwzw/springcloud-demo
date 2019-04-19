package com.pier.mq.rabbitmq;

import com.pier.bean.User;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 16:27
 */
public interface IMessageProvider {

    /**
     * 实现消息的发送，本次发送的消息是一个对象（自动变为json）
     * @param user VO对象，该对象不为null*/
    void send(User user);

    void send(String msg);

    void sendKafka(String msg);
}
