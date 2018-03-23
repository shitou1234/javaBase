package com.shitou.reflect.field;

/**
 * Created by Administrator on 2018/3/13.
 */
public class StudentDemo {
    public StudentDemo(){

    }
    //**********字段*************//
    public String name;
    protected int age;
    char sex;
    private String phoneNum;

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", sex=" + sex
                + ", phoneNum=" + phoneNum + "]";
    }
}
