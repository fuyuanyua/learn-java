package com.example.learnjava.designpattern.singleton;

/**
 * @Description: 单例模式-懒汉式
 * @Author: lhb
 * @Date: 2022/4/6 23:22
 *
 * 要点：
 *      1.构造方法私有
 *      2.第一次调用getInstance方法才创建对象
 *      3.非线程安全
 */

public class SingletonDemo3 {

    private static SingletonDemo3 INSTANCE = null;

    private SingletonDemo3() {

    }

    public static SingletonDemo3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonDemo3();
        }
        return INSTANCE;
    }
}
