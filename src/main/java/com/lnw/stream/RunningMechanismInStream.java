package com.lnw.stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Stream的运行机制
 * 1、所有操作是链式调用，一个元素只迭代一次
 * 2、每个中间操作返回一个新的流，流里面有一个属性——sourceStage指向头部链表，就是Head
 * 3、Head有个指针指向next，Head->nextStage->...->null
 * 4、有状态操作会把无状态操作截断，单独处理
 * 5、并行环境下，有状态的中间操作不一定能并行操作
 * 6、parallel / sequential 这两个操作也是中间操作，返回Stream，但是他们不创建流，
 *      只修改Head的并行标志
 * Created by linnanwei on 2019/9/23
 *
 * @author linnanwei
 */
public class RunningMechanismInStream {
    public static void main(String[] args) {
        Random random = new Random();
        // 从输出的日志可看出操作是链式操作
        final Stream<Integer> integerStream = Stream.generate(() -> random.nextInt())
                // 产生500个（无限流需要短路操作）
                .limit(500)
                // 第一个无状态操作
                .peek(s -> System.out.println(Thread.currentThread().getName()+" peek:" + s))
                // 第二个无状态操作
                .filter(s -> {
                    System.out.println(Thread.currentThread().getName()+" filter: " + s);
                    return s > 1000000;
                })
                // 有状态操作
                .sorted((i1, i2) -> {
                    System.out.println(Thread.currentThread().getName()+" 排序："+i1+","+i2);
                    return i1.compareTo(i2);
                })
                // 无状态操作
                .peek(s -> System.out.println(Thread.currentThread().getName()+" peek2:" + s))
                .parallel();
        // 终止操作
        integerStream.count();
    }
}

