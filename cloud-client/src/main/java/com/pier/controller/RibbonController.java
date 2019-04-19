package com.pier.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * ribbon一个坑，不能接受List类型，要使用数组接收。
     * @param parm
     * @return
     */
    @GetMapping("/ribbon/{wd}")
    @HystrixCommand(fallbackMethod="fallbackMethod")
    public Mono<String> sayHelloWorld(@PathVariable("wd") String parm) {

        ServiceInstance serviceInstance = loadBalancerClient.choose("CLOUD-SERVER");

        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/test" + parm;

        log.info("url:" + url);

        String res = this.restTemplate.getForObject(/*"http://CLOUD-SERVER/test/" + parm*/url, String.class);
        log.info("get ribbon res:" + res);
        return Mono.just(res);
    }

    /**
     * 参数和返回值与回调他的方法保持一致
     * @param parm
     * @return
     */
    public Mono<String> fallbackMethod(@PathVariable("wd") String parm) {
        return Mono.just("fallback");
    }
}
