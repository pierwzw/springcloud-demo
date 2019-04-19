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
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @auther zhongweiwu
 * @date 2019/4/19 16:41
 */
@Configuration
@ConditionalOnBusEnabled //bus启用的开关
@EnableBinding(SpringCloudBusClient.class) //绑定通道
@EnableConfigurationProperties(BusProperties.class)
@Slf4j
public class BusEventPublisher implements ApplicationEventPublisherAware {

    //注入source接口，用于发送消息
    @Autowired
    @Output(SpringCloudBusClient.OUTPUT)
    private MessageChannel cloudBusOutboundChannel;

    @Autowired
    private ServiceMatcher serviceMatcher;

    // 监听RemoteApplicationEvent事件
    @EventListener(classes = RemoteApplicationEvent.class)
    public void acceptLocal(ApplicationEvent event) { {
            if (this.serviceMatcher.isFromSelf((RemoteApplicationEvent) event)
                    && !(event instanceof AckRemoteApplicationEvent)) {
                log.info("received Remote ApplicationEvent:" + event);
                //当事件是来自自己的并且不是ack事件，则发送消息
                this.cloudBusOutboundChannel.send(MessageBuilder.withPayload(event).build());
            }else if (event instanceof MyCustomRemoteEvent){
                log.info("received MyCustomRemoteEvent:" + event);
                this.cloudBusOutboundChannel.send(MessageBuilder.withPayload(event).build());
            }
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }
}
