package com.pier.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 16:51
 */
@Component
@EnableBinding({InputProcess.class, Sink.class})  // 没自定义之前是Sink.class
@Slf4j
public class MessageListener {

    @StreamListener(InputProcess.INPUT)
    public void input(Message<String> message) {
        log.info("【*** 消息接收 ***】" + message.getPayload());
    }

    @StreamListener(InputProcess.INPUT)
    public void receive(Message<String> message,
                        @Header(AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {

        if (StringUtils.isBlank(message.getPayload())) {
            throw new Exception("数据有误");
        }
        try {
            channel.basicAck(deliveryTag, false);//手动确认
            log.info("【*** 消息接收 ***】" + message.getPayload());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @StreamListener(Sink.INPUT)
    public void kafkaInput(Message<String> message) {
        log.info("【*** 消息接收 ***】" + message.getPayload());
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }
}