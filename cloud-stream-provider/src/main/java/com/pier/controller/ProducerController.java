package com.pier.controller;

import com.pier.mq.rabbitmq.IMessageProvider;
import com.pier.mq.rabbitmq.MessageProviderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zhongweiwu
 * @date 2019/4/18 16:37
 */
@Slf4j
@RestController
public class ProducerController {

    @Autowired
    private MessageProviderImpl producer;


    @RequestMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg) throws InterruptedException {
        int i=0;
        while(true){
            log.info("send msg:" + msg + "-----" + i++);
            producer.send(msg + "-----" + i);
            Thread.sleep(1000);
        }
        //return "send msg finished:" + msg;
    }

    @RequestMapping("/sendkafka/{msg}")
    public void sendKafka(@PathVariable("msg") String msg) throws InterruptedException {
        int i=0;
        while(true){
            log.info("send kafka msg:" + msg + "-----" + i++);
            producer.sendKafka(msg + "-----" + i);
            Thread.sleep(1000);
        }
        //return "send msg finished:" + msg;
    }
}
