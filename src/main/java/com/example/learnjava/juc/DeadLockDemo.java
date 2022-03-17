package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 死锁示例
 * @Author: lhb
 * @Date: 2022/3/17 22:38
 */

@Slf4j
public class DeadLockDemo {

    public static void main(String[] args) {
        // 资源1
        Object resource1 = new Object();
        // 资源2
        Object resource2 = new Object();

        new Thread(() -> {
            synchronized (resource1) {
                log.info("【{}】获取到了【资源1】", Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("【{}】正在尝试获取【资源2】" , Thread.currentThread().getName());
                synchronized (resource2) {
                    log.info("【{}】获取到了【资源2】" , Thread.currentThread().getName());
                }
            }
        }, "线程a").start();

        new Thread(() -> {
            synchronized (resource2) {
                log.info("【{}】获取到了【资源2】", Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("【{}】正在尝试获取【资源1】" , Thread.currentThread().getName());
                synchronized (resource1) {
                    log.info("【{}】获取到了【资源1】" , Thread.currentThread().getName());
                }
            }
        }, "线程b").start();
    }
}
