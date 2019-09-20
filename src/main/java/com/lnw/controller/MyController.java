package com.lnw.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by linnanwei on 2019/8/23
 *
 * @author linnanwei
 */
@RestController
@Slf4j
public class MyController {

    @GetMapping("/index")
    public String simpleFind(){
        log.info("start");
        String res = doSomething();
        log.info("end");
        return res;
    }

    @GetMapping("/index/flux")
    Mono<String> find(){
        log.info("start");
        Mono<String> res = Mono.fromSupplier(() -> doSomething());
        log.info("end");
        return res;
    }

    @GetMapping("/findAll")
    Mono<Map<String, String>> findAll(){
        Map<String, String> map  = new HashMap<>();
        map.put("name", "LNW");
        map.put("school", "GDUF");
        map.put("home", "广东汕头");
        return Mono.just(map);
    }

    public String doSomething(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Do something";
    }
}
