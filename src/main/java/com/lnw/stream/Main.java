package com.lnw.stream;

import java.util.stream.IntStream;

/**
 * Created by linnanwei on 2019/9/18
 *
 * @author linnanwei
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {33,55,-9,500,-33,1};
        int min = IntStream.of(nums).min().getAsInt();
        System.out.println(min);

        Interface1 interface1 = i -> i*i;
        System.out.println(interface1.sum(1, 2));
        System.out.println(interface1.doublex(3));
    }
}
@FunctionalInterface
interface Interface1{
    int doublex(int x);

    default int sum(int x, int y){
        return x + y;
    }
}

@FunctionalInterface
interface Interface2{
    int doublex(int x);

    default int sum(int x, int y){
        return x + y;
    }
}

/**
 * 必须重写sum方法，因为两个父类都有此方法
 */
@FunctionalInterface
interface Interface3 extends Interface1, Interface2{
    @Override
    default int sum(int x, int y) {
        return Interface1.super.sum(x, y);
    }
}