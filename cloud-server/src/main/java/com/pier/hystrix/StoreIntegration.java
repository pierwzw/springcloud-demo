package com.pier.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @auther zhongweiwu
 * @date 2019/4/4 15:46
 */
@Component
@Slf4j
public class StoreIntegration {

    @HystrixCommand(fallbackMethod = "defaultStores")
    public Object getStores(Map<String, Object> parameters) {
        log.error("hystrix discover error!");
        return null;
    }

    public Object defaultStores(Map<String, Object> parameters) {
        log.info("hystrix default Stores");
        return null;
    }
}
