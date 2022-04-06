package com.example.learnjava.designpattern.singleton;

/**
 * @Description: 单例模式-饿汉式
 * @Author: lhb
 * @Date: 2022/4/6 23:20
 *
 * 要点：
 *      1.构造方法私有
 *      2.类初始化（类加载流程：加载 -> 连接 -> 初始化）时就创建对象
 */

public class SingletonDemo1 {

    private static final SingletonDemo1 INSTANCE = new SingletonDemo1();

    private SingletonDemo1() {

    }

    public static SingletonDemo1 getInstance() {
        return INSTANCE;
    }
}
