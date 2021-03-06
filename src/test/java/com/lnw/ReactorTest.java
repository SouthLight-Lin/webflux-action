package com.lnw;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * @author linnanwei
 * @version 1.0.0
 * @since 1.0.0
 * Date： 2019/10/10
 */
public class ReactorTest {

    @Test
    public void testBackPressure() throws InterruptedException {
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<Integer>() {

                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(final Subscription s) {
                        this.s = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(final Integer integer) {
                        System.out.println(integer);
                        onNextAmount++;
                        if (onNextAmount % 2 == 0){
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(final Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println(i);
        }
        Thread.sleep(10*1000);
    }


    @Test
    public void testFlux(){
        Flux.just(1,2,3,4).subscribe(System.out::print);
        System.out.println();
        Mono.just(1).subscribe(System.out::print);
        System.out.println();
        // 订阅并定义对正常数据、错误信号、和完成信号的处理
        Flux.just(1,2,3,4,5,6)
                .subscribe(System.out::println,
                        System.out::println,
                        () -> System.out.println("Completed"));
        // 错误信号
        Flux.error(new Exception("error"))
                .subscribe(System.out::println,
                        System.out::println,
                        () -> System.out.println("Completed"));
    }


    private Flux<Integer> generateFlux(){
        return Flux.just(1,2,3,4,5,6);
    }

    private Mono<Integer> generateMonoWithError(){
        return Mono.error(new Exception("error"));
    }

    @Test
    public void testViaStepVerifier(){
        StepVerifier.create(generateFlux())
                .expectNext(1,2,3,4,5,6)
                .expectComplete()
                .verify();

        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("error")
                .verify();
    }

    @Test
    public void testFlatMap() {
        StepVerifier.create(Flux.just("flux", "mono")
                .flatMap(s -> Flux.fromArray(s.split("\\s*"))
                        .delayElements(Duration.ofMillis(100)))
                .doOnNext(System.out::print))
                .expectNextCount(8)
                .verifyComplete();
    }


    @Test
    public void testSchedulers() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(() -> getStringSync())  // 1
                .subscribeOn(Schedulers.elastic()) // 2
                .subscribe(s -> {
                    System.out.println(Thread.currentThread().getName() + " s ");
                }, null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);

        // 2 将任务调度到Schedulers内置的弹性线程池执行，弹性线程池会为Callable的执行任务分配一个单独的线程

    }

    private String getStringSync() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, Reactor!";
    }
}
