package com.pier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
//@RibbonClient("roundrobbin")
@EnableFeignClients
@EnableCircuitBreaker
//@EnableHystrix
public class CloudClient2Application {

    /*// ribbon需要配置，负载均衡
    @Autowired
    private RestTemplateBuilder builder;
    // ribbon需要配置，负载均衡
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return builder.build();
    }*/

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudClient2Application.class, args);
    }

}
