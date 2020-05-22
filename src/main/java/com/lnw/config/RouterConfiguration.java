package com.lnw.config;

import java.time.Duration;

import com.lnw.LoginRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by linnanwei on 2019/8/23
 *
 * @author linnanwei
 */
@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> webFlux() {
        System.out.println("----init-----");
        return RouterFunctions.route(RequestPredicates.POST("/webFlux"), request -> {
            System.out.println(request.bodyToMono(LoginRequest.class));
            Mono<String> str = Mono.just("Hello World").delayElement(Duration.ofMillis(100));
            return ServerResponse.ok().body(str, String.class);
        });
    }

}
