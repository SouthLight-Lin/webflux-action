package com.lnw.singleton;

/**
 * Created by linnanwei on 2019/9/23
 *
 * @author linnanwei
 */
public class Singleton {
    /** 饿汉模式，不管有没有调用，都会生成 **/
    public static Singleton instance = new Singleton();

    public Singleton(){}

    public static Singleton getInstance(){
        return instance;
    }


    /** 懒汉模式 **/
    public volatile static Singleton instance2 = null;
    public static Singleton getInstance2(){
        if (instance2 == null){
            synchronized (Singleton.class){
                instance2 = new Singleton();
            }
        }
        return instance2;
    }

    /** 静态内部类 **/
    private static class SingletonHolder{
        private static final Singleton singleton = new Singleton();
    }
    public static Singleton getInstance3(){
        return SingletonHolder.singleton;
    }
}
