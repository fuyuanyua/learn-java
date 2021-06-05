package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 线程池示例
 * @Author: lhb
 * @Date: 2021/6/5 11:04
 *
 * 线程池：
 *      1.Java创建线程的四种方式：
 *          1.继承Thread类
 *          2.实现Runnable接口
 *          3.实现Callable接口
 *          4.通过线程池创建
 *      2.线程池创建线程的优点：
 *          1.线程可复用，减少反复创建线程对象、垃圾回收的消耗
 *          2.可以控制并发最大的线程数
 *          3.可以有效的管理线程
 *      3.创建线程池：
 *          1.借助Executors工具类：
 *              常用有以下几种：
 *              1.Executors.newFixedThreadPool(n); 创建固定n个线程的线程池
 *              2.Executors.newSingleThreadExecutor(); 创建单线程的线程池
 *              3.Executors.newCachedThreadPool(); 创建可缓存的线程池
 *          2.手动创建：
 *
 *
 */

@Slf4j
public class ThreadPoolDemo {

    public static void main(String[] args) {
        // log.info("{}", Runtime.getRuntime().availableProcessors());

        // 创建固定5个线程的线程池
        // ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 创建单线程的线程池
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();

        // 创建可缓存的线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            // 模拟10个用户请求
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    log.info("{} 处理请求", Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
