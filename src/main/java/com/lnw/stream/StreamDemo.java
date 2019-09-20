package com.lnw.stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * https://www.bilibili.com/video/av63120055/?p=19
 *
 * Created by linnanwei on 2019/9/20
 *
 * @author linnanwei
 */
public class StreamDemo {

    public static void main(String[] args) {
        String str = "my name is lnw";
        Stream.of(str.split(" "))
                .filter(s -> s.length()>2)
                .mapToInt( s -> s.length())
                .forEach(System.out::println);
        // flatMap A -> B属性（是个集合），最终得到所有A元素里面所有B属性集合
        // IntSteam或者LongStream并不是Stream的子类，所有要进行装箱box
        Stream.of(str.split(" "))
                .flatMap(s -> s.chars().boxed()).forEach(i -> System.out.println((char)i.intValue()));
        System.out.println();
        // peek用于debug，是个中间操作，但是foreach是终止操作
        Stream.of(str.split(" ")).peek(System.out::println)
                .forEach(System.out::println);
        System.out.println();

        // limit使用，主要用于无限流
        new Random().ints().limit(10).forEach(System.out::println);
    }
}
