package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: PessimisticLockDemo
 * @Author: lhb
 * @Date: 2021/7/15 下午8:14
 */

@Slf4j
public class PessimisticLockDemo {

    /**
     * 测试悲观锁，两个线程分别同时对资源类执行100000次add操作，最终结果一定是200000
     * @param args
     */
    public static void main(String[] args) {
        Resource resource = new Resource();

        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                resource.add();
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                resource.add();
            }
        }, "t2").start();

        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("最终计算结果结果 = {}", resource.get());
    }

    /**
     * 资源类
     */
    static class Resource {

        int a = 0;

        Lock lock = new ReentrantLock();

        /**
         * add方法加上悲观锁，同一时间只能有一个线程执行方法
         */
        public void add() {
            lock.lock();
            try {
                a++;
                log.info("{} add", Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public int get() {
            return a;
        }
    }
}
