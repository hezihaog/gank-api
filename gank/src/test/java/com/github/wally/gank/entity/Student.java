package com.github.wally.gank.entity;

/**
 * Package: com.github.wally.rxjava_training.entity
 * FileName: Student
 * Date: on 2018/6/13  下午2:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}