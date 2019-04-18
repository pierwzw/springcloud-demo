package com.pier.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zhongweiwu
 * @date 2019/4/17 10:46
 */
@RestController
@RequestMapping("test")
@Slf4j
public class IndexController {

    //返回一个实体
    @GetMapping("{msg}")
    public Mono<String> sayHelloWorld(@PathVariable("msg") String msg) {
        log.info("come on " + msg);
        return Mono.just("cloud-provider receive : " +msg);
    }
    //返回一个列表
    @GetMapping("list")
    public Flux<Integer> list() {
        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(22);
        list.add(75);
        list.add(93);
        Flux<Integer> userFlux = Flux.fromIterable(list);
        return userFlux;
    }
}
