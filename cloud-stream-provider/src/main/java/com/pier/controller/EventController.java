package com.pier.controller;

import com.pier.event.MyCustomRemoteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zhongweiwu
 * @date 2019/4/19 14:52
 */
@RefreshScope //允许动态刷新配置
@RestController
@Slf4j
public class EventController {

    //注入ApplicationContext,通过ApplicationContext来publish remote event
    private ApplicationContext context;

    @Autowired
    public EventController(ApplicationContext context) {
        this.context = context;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public String publish(@RequestParam(value = "destination", required = false, defaultValue = "**") String destination) {
        // each service instance must have a unique context ID

        final String myUniqueId = context.getId();

        final MyCustomRemoteEvent event;
        event = new MyCustomRemoteEvent(this, myUniqueId, destination, "hello world");
        //Since we extended RemoteApplicationEvent and we've configured the scanning of remote events using
        // @RemoteApplicationEventScan, it will be treated as a bus event rather than just a regular ApplicationEvent published in the context.
        //因为我们在启动类上设置了@RemoteApplicationEventScan注解，所以通过context发送的时间将变成一个bus event总线事件，而不是在自身context中发布的一个ApplicationEvent
        log.info("publish event:" + event);
        context.publishEvent(event);

        return "event published";
    }
}
