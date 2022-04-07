package com.example.learnjava.designpattern.singleton;

/**
 * @Description: 单例模式-内部类实现
 * @Author: lhb
 * @Date: 2022/4/7 10:40
 *
 * 说明：
 *      1.构造方法私有
 *      2.内部类能访问外部类的构造方法
 *      3.内部类是懒加载的，在第一次访问内部类的（静态变量、静态方法）时候才初始化
 *      4.内部类的静态变量是饿汉式的，在内部类初始化时就完成创建，并由JVM保证了线程安全
 */

public class SingletonDemo5 {

    private SingletonDemo5() {

    }

    static class Holder {
        static SingletonDemo5 INSTANCE = new SingletonDemo5();
    }

    public static SingletonDemo5 getInstance() {
        return Holder.INSTANCE;
    }
}
