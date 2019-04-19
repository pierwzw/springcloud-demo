package com.pier.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 17:01
 */
public interface OutputProcess {

    String OUTPUT = "output1"; // 输出通道名称

    @Output(OutputProcess.OUTPUT)
    MessageChannel output();
}
