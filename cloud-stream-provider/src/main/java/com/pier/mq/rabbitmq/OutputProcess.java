package com.pier.mq.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 17:01
 */
public interface OutputProcess {

    String OUTPUT = "output1"; // 输出通道名称
    //String INPUT = "input1"; // 输入通道名称

    //@Input(DefaultProcess.INPUT)
    //SubscribableChannel input();

    @Output(OutputProcess.OUTPUT)
    MessageChannel output();
}
