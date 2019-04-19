package com.pier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableCircuitBreaker
@EnableDiscoveryClient
@RemoteApplicationEventScan(basePackages = "com.pier.event")
public class CloudStreamProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamProviderApplication.class, args);
    }

}
