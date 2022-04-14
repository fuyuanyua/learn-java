package com.example.learnjava.designpattern.singleton;

/**
 * @Description: 单例模式-双重校验懒汉式
 * @Author: lhb
 * @Date: 2022/4/6 23:29
 *
 * 说明：
 *      1.构造方法私有
 *      2.第一次调用getInstance方法才创建对象
 *      3.线程安全
 *          1.volatile修饰？
 *              INSTANCE = new SingletonDemo4()分为三步骤：
 *                  1.开辟内存空间
 *                  2.调用构造方法赋值
 *                  3.引用指向内存地址
 *              因为指令重排，2和3这两步骤顺序不确定，那么多个线程并发创建单例对象时，一个线程可能获取到另一个线程未完全创建好的实例
 *              volatile禁止了指令重排，保证了创建对象的有序性
 *          2.为什么需要两次判断？
 *              1.第一次判断：
 *                  为了优化性能，调用getInstance方法无需每次都上锁，只有INSTANCE对象还未被创建时才会上锁
 *              2.第二次判断：
 *                  假设线程a和线程b，INSTANCE对象还未被创建
 *                  此时a获取到锁（b阻塞），创建了对象，锁释放
 *                  后续b获取到锁，如果未进行第二次判断，那么b仍然认为INSTANCE对象还未被创建，又重新创建一个对象
 *                  所以如果第二次不判断的话会导致线程安全问题
 *
 */

public class SingletonDemo4 {

    /**
     * volatile保证可见性、有序性
     */
    private static volatile SingletonDemo4 INSTANCE = null;

    private SingletonDemo4() {

    }

    public static SingletonDemo4 getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonDemo4.class) {
                if (INSTANCE == null) {
                    // 底层工作原理
                    // 1.创建对象，分配内存空间
                    // 2.init方法：即调用构造方法
                    // 3.给INSTANCE赋值
                    INSTANCE = new SingletonDemo4();
                }
            }
        }
        return INSTANCE;
    }
}
