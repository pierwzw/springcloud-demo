package com.pier.mq.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 17:01
 */
public interface InputProcess {

    //String OUTPUT = "output1"; // 输出通道名称
    String INPUT = "input1"; // 输入通道名称

    @Input(InputProcess.INPUT)
    SubscribableChannel input();

    //@Output(InputProcess.OUTPUT)
    //MessageChannel output();
}
