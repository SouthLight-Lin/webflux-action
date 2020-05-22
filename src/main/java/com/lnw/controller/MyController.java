package com.lnw.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

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
        log.info(Thread.currentThread().getName()+":start");
        // 因为没有流的最终操作，所以不会阻塞Controller这个线程，惰性求值
        Mono<String> res = Mono.fromSupplier(() -> doSomething());
        log.info(Thread.currentThread().getName()+":end");
        return res;
    }

    @GetMapping("/findAll")
    Mono<Map<String, String>> findAll() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "LNW");
        map.put("school", "GDUF");
        map.put("home", "广东汕头");
        return Mono.just(map);
    }

    @GetMapping("/download")
    public Mono<Void> downloadByWriteWith(ServerHttpResponse response, String arg) throws IOException {
        if ("1".equals(arg)) {
            final ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
            response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parallel.png");

            final Resource resource = new FileUrlResource("C:\\Users\\Administrator\\Pictures\\Saved Pictures\\4.png");
            final File file = resource.getFile();
            return zeroCopyResponse.writeWith(file, 0, file.length());
        }
        return Mono.empty();
    }

    @GetMapping("/download2")
    public Mono<ResponseEntity<Object>> downloadByWriteWith2(ServerHttpResponse response, String arg) throws IOException {
        if ("1".equals(arg)) {
            final Resource resource = new FileUrlResource("C:\\Users\\Administrator\\Pictures\\Saved Pictures\\4.png");
            return Mono.justOrEmpty(ResponseEntity.ok().header("Content-Disposition", "attachment; filename=parallel.png")
                    .body(new InputStreamResource(resource.getInputStream())));
        }
        final Map<String, String> map = new HashMap<>();
        map.put("message", "fail to download file");
        return Mono.just(ResponseEntity.ok().body(map));
    }

    @GetMapping("/download3")
    public Mono<Resource> download3() {
        return Mono.justOrEmpty(new FileSystemResource("G:\\评语表.docx"));
    }

    public String doSomething() {
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("doSomething:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Do something";
    }
}
