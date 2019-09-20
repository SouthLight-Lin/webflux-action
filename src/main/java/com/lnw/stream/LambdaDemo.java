package com.lnw.stream;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 惰性求值的作用：如果没有调用终止函数，当前线程不会阻塞去执行耗时的操作
 *              我们可以把耗时操作交给另一个线程执行，这种特性在WebFlux中得到应用
 * Created by linnanwei on 2019/9/18
 *
 * @author linnanwei
 */
public class LambdaDemo {
    public static void main(String[] args) {
        // 惰性求值
        int[] nums = {1,2,3,4,5,6};
        // map就是中间操作（返回stream的操作）
        // sum就是终止操作
        // int sum = IntStream.of(nums).map(LambdaDemo::doubleNum).sum();
        // System.out.println("Done");
        // 由于没有调用终止操作sum，所以map中的函数不会被执行
        IntStream intStream =  IntStream.of(nums).map(LambdaDemo::doubleNum);
        // 将耗时操作交给两个线程执行
        new Thread(() ->{
            intStream.sum();
        }).start();
        System.out.println("Main Thread Done");

    }

    public static int doubleNum(int i){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行了乘以2");
        return i*i;
    }
}
