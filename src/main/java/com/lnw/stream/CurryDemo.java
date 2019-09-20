package com.lnw.stream;

import java.util.function.Function;

/**
 * 级联表达式和柯里化
 * 柯里化：把多个参数的函数转化为只有一个参数的函数
 * 高阶函数：返回函数的函数
 * Created by linnanwei on 2019/9/18
 *
 * @author linnanwei
 */
public class CurryDemo {
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> fun  =
                x -> y -> x+y;
        System.out.println(fun.apply(1).apply(2));
        System.out.println(fun.apply(1));
    }
}
