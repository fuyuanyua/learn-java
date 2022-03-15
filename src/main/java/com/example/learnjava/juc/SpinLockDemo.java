package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: 自旋锁示例
 * @Author: lhb
 * @Date: 2021/5/26 22:09
 *
 * 自旋锁：
 *      1.说明：
 *          线程反复检查锁是否可用，直到获取到锁为止，
 *          线程获取到锁后会一直保持该锁，直到显式释放
 *      2.优点：自旋锁不会让线程状态发生改变，减少了不必要的上下文切换
 *      3.缺点：如果某个线程获取锁时间过长，会让其他待获取锁的线程一直循环等待，消耗CPU
 *      4.示例：通过CAS实现一个自旋锁
 */

@Slf4j
public class SpinLockDemo {

    /**
     * 原子引用线程，初始值null
     */
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 测试自定义的自旋锁
     * @param args
     */
    public static void main(String[] args) {
        SpinLockDemo spinLock = new SpinLockDemo();

        new Thread(() -> {
            spinLock.lock();
            // sleep5秒，模拟线程正在进行业务
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unlock();
        }, "t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLock.lock();
            // sleep5秒，模拟线程正在进行业务
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unlock();
        }, "t2").start();

    }

    /**
     * 加锁
     */
    public void lock() {
        // 当前线程
        Thread thread = Thread.currentThread();
        log.info("{} 正在不断尝试获取锁", thread.getName());
        // 循环尝试获取锁
        while (!atomicReference.compareAndSet(null, thread)) {
            log.info("{} 尝试获取锁失败", thread.getName());
        }
        log.info("{} 获取到了锁", thread.getName());
    }

    /**
     * 解锁
     */
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        log.info("{} 释放了锁", thread.getName());
    }
}
