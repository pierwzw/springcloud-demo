package com.pier.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 17:01
 */
public interface InputProcess {

    String INPUT = "input1"; // 输入通道名称

    @Input(InputProcess.INPUT)
    SubscribableChannel input();
}
