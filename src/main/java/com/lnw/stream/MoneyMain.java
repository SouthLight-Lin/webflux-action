package com.lnw.stream;

import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * Created by linnanwei on 2019/9/18
 *
 * @author linnanwei
 */
// @FunctionalInterface
// interface IMoneyFormat{
//     String format(int money);
// }

class MyMoney{
    private final int money;
    public MyMoney(int money){
        this.money = money;
    }

    /** 输入时Integer类型，输出是String类型 **/
    public void printMyMoney(Function<Integer, String> iMoneyFormat){
        System.out.println("My money:" + iMoneyFormat.apply(money));
    }
}

public class MoneyMain {
    public static void main(String[] args) {
        MyMoney myMoney = new MyMoney(99999);
        final Function<Integer, String> myFunction = i -> new DecimalFormat("#,###").format(i);
        // 链式操作
        myMoney.printMyMoney(myFunction.andThen(
                s -> "人民币："+s));
    }
}
