package com.github.wally.mvp.entity;

import java.util.List;

/**
 * Package: com.github.wally.rxjava_training.entity
 * FileName: Teacher
 * Date: on 2018/6/13  下午2:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class Teacher {
    private List<Student> students;

    public Teacher(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}