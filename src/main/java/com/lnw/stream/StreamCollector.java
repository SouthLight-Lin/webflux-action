package com.lnw.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 流的收集器
 * 作用：把流处理后的数据收集起来，把处理的数据收集成list、map等集合，
 * 或者求和，将多个数据整理成一个数据，
 * 字符串的拼接
 *
 * Created by linnanwei on 2019/9/23
 *
 * @author linnanwei
 */
public class StreamCollector {
    public static void main(String[] args) {
        List<Stu> stus = Arrays.asList(
                new Stu(10, "Ming", Gender.MALE, Grade.ONE),
                new Stu(12, "Marry", Gender.FEMALE, Grade.FIVE),
                new Stu(13, "Jian", Gender.FEMALE, Grade.TWO),
                new Stu(15, "Kai", Gender.MALE, Grade.FIVE),
                new Stu(11, "Men", Gender.MALE, Grade.THREE),
                new Stu(12, "Mei", Gender.MALE, Grade.FIVE),
                new Stu(16, "Qi", Gender.FEMALE, Grade.FOUR),
                new Stu(18, "Ji", Gender.FEMALE, Grade.FIVE),
                new Stu(12, "Huo", Gender.MALE, Grade.TWO),
                new Stu(10, "Tao", Gender.MALE, Grade.FIVE),
                new Stu(9, "Fang", Gender.MALE, Grade.FIVE)
        );

        // 得到所有学生的年龄列表
        System.out.println(stus.stream().map(Stu::getAge).collect(Collectors.toSet()).toString());
        // 统计汇总信息
        System.out.println(stus.stream().collect(Collectors.summarizingInt(Stu::getAge)));
        // 分块
        System.out.println(stus.stream().collect(Collectors.partitioningBy(s -> s.getGender() == Gender.MALE)));
        // 分组
        System.out.println(stus.stream().collect(Collectors.groupingBy(Stu::getGrade)));
        // 统计分组后每组的人数
        System.out.println(stus.stream().collect(Collectors.groupingBy(Stu::getGrade, Collectors.counting())));
    }
}

@Data
@AllArgsConstructor
class Stu{
    int age;
    String name;
    Gender gender;
    Grade grade;
}

enum Gender{
    MALE, FEMALE;
}

enum Grade{
    ONE,TWO,THREE,FOUR,FIVE,SIX;
}