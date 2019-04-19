package com.pier.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @auther zhongweiwu
 * @date 2019/4/19 14:55
 */
//自定义事件侦听
@Component
@Slf4j
public class MyCustomRemoteEventListener implements ApplicationListener<MyCustomRemoteEvent> {

    //处理自定义事件
    @Override
    public void onApplicationEvent(MyCustomRemoteEvent event) {
        log.info("Received MyCustomRemoteEvent - message: " + event.getMessage());
    }
}
