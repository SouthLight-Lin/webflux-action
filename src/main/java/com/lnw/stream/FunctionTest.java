package com.lnw.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by linnanwei on 2019/9/18
 *
 * @author linnanwei
 */
public class FunctionTest {
    public static void main(String[] args) {
        // 断言函数接口
        Predicate<Integer> predicate = i -> i>0;
        System.out.println(predicate.test(1));

        // 消费函数接口                // 方法引用，这样写更加简洁
        Consumer<String> consumer = System.out::println;
        consumer.accept("输入数据：SouthLight");

        // 生产函数接口
        Supplier<String> supplier = () -> "lnw";
        System.out.println(supplier.get());

        // 静态方法引用
        Consumer<Student> consumer1 = Student::print;
        consumer1.accept(new Student());

        Student student = new Student();
        // 实例方法的引用
        Function<String, String> function = student::setName;
        System.out.println(function.apply("lnw"));

        // 构造函数的方法引用
        Supplier<Student> supplier1 = Student::new;
        System.out.println(supplier1.get());

        // 带参数的构造函数的方法引用(最多只能带一个）
        Function<String, Student> function1 = Student::new;
        System.out.println(function1.apply("LLLNNNWWW"));
    }

}


class Student{
    String name = "SouthLight";
    String school  = "GDUF";

    public Student() {
    }

    public Student(final String name) {
        this.name = name;
        this.school = school;
    }

    public static void print(Student student){
        System.out.println(student);
    }

    public String setName(String name){
        this.name = name;
        return this.name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}