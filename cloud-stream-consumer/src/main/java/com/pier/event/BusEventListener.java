package com.pier.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.ConditionalOnBusEnabled;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.SpringCloudBusClient;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.bus.event.SentApplicationEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @auther zhongweiwu
 * @date 2019/4/19 16:52
 */
@Configuration
@ConditionalOnBusEnabled //bus启用的开关
@EnableBinding(SpringCloudBusClient.class) //绑定通道
@EnableConfigurationProperties(BusProperties.class)
@Slf4j
public class BusEventListener implements ApplicationEventPublisherAware {

    @Autowired
    private BusProperties bus;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ServiceMatcher serviceMatcher;

    //注入source接口，用于发送消息
    @Autowired
    @Output(SpringCloudBusClient.OUTPUT)
    private MessageChannel cloudBusOutboundChannel;

    //消息的消费，也是事件的发起
    @StreamListener(SpringCloudBusClient.INPUT)
    public void acceptRemote(RemoteApplicationEvent event) {
        if (event instanceof AckRemoteApplicationEvent) {
            //ack事件
            if (this.bus.getTrace().isEnabled() && !this.serviceMatcher.isFromSelf(event)
                    && this.applicationEventPublisher != null) {
                //当开启bus追踪且不是自己的ack事件，则通知所有的注册该事件的监听者，否则直接返回
                this.applicationEventPublisher.publishEvent(event);
                log.info("is not the msg myself, notice all event listeners");
            }
            log.info("Received RemoteApplicationEvent - message: " + event);
            return;
        }
        //消费消息，该消息属于自己
        if (this.serviceMatcher.isForSelf(event) && this.applicationEventPublisher != null) {
            //不是自己发布的事件，正常处理
            if (!this.serviceMatcher.isFromSelf(event)) {
                this.applicationEventPublisher.publishEvent(event);
            }
            //消费之后，需要发送ack确认事件
            if (this.bus.getAck().isEnabled()) {
                AckRemoteApplicationEvent ack = new AckRemoteApplicationEvent(this,
                        this.serviceMatcher.getServiceId(),
                        this.bus.getAck().getDestinationService(),
                        event.getDestinationService(), event.getId(), event.getClass());
                this.cloudBusOutboundChannel.send(MessageBuilder.withPayload(ack).build());
                this.applicationEventPublisher.publishEvent(ack);
            }
        }
        //事件追踪相关，若是开启追踪事件则执行
        if (this.bus.getTrace().isEnabled() && this.applicationEventPublisher != null) {
            // 不论其来源，准备发送事件，发布了之后供本地消费
            this.applicationEventPublisher.publishEvent(new SentApplicationEvent(this,
                    event.getOriginService(), event.getDestinationService(),
                    event.getId(), event.getClass()));
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }
}
