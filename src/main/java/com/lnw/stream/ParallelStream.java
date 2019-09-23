package com.lnw.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 并行流
 *
 * Created by linnanwei on 2019/9/23
 *
 * @author linnanwei
 */
public class ParallelStream {
    public static void main(String[] args) {
        // 1、调用parallel产生一个并行流
        // IntStream.range(1,100).parallel().peek(ParallelStream::debug).count();

        // 第一步先并行，第二步再串行
        // 2、多次调用串行和并行函数，以最后一次为准，因为调用时经过一个调用链的
        // IntStream.range(1,100)
        //         // 调用并行函数
        //         .parallel().peek(ParallelStream::debug)
        //         // 调用串行函数
        //         .sequential().peek(ParallelStream::syncDebug)
        //         .count();

        // 3、并行流使用的线程池：ForkJoinPool.commonPool
        // 默认的线程数是CPU的核心数，也就是逻辑处理器的个数
        // 使用下面这段代码即可修改线程池的大小
        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        // IntStream.range(1,100).parallel().peek(ParallelStream::debug).count();

        // 4、所有的并行流一般都是使用同一个相同的，我们可以设定指定自己的线程池
        // 使用自己创建的线程池，线程名：ForkJoinPool-1
        ForkJoinPool pool = new ForkJoinPool(15);
        pool.submit(() -> IntStream.range(1,100).parallel().peek(ParallelStream::debug).count());
        pool.shutdown();

        synchronized (pool){
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug(int i)  {
        System.out.println(Thread.currentThread().getName()+" debug:" + i);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void syncDebug(int i){
        System.err.println("syncDebug:" + i);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
