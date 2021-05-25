package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 *      1.可重入锁示例
 *      2.公平锁和非公平锁说明
 * @Author: lhb
 * @Date: 2021/5/24 22:26
 *
 * 可重入锁
 *      1.说明：指一个线程获得外层同步方法的锁后，能自动获取内层同步方法的锁。
 *      2.示例：以下两种锁都是典型的可重入锁
 *          2.1：synchronized
 *          2.2：ReentrantLock
 *
 * 公平锁和非公平锁
 *      1.说明：
 *          公平锁：线程按照FIFO的顺序进入队列排队去获取锁
 *          非公平锁：线程进来就会直接去抢占锁，抢占失败再去队列排队等待
 *      2.示例：
 *          2.1：synchronized是非公平锁
 *          2.2：ReentrantLock默认是非公平锁
 *
 *              无参构造方法：
 *              public ReentrantLock() {
 *                  sync = new NonfairSync();
 *              }
 *              说明：创建一个非公平锁
 *
 *              含参构造方法：
 *              public ReentrantLock(boolean fair) {
 *                  sync = fair ? new FairSync() : new NonfairSync();
 *              }
 *              说明：传false：创建一个非公平锁；传true：创建一个公平锁
 *
 *
 */

@Slf4j
public class ReentrantLockDemo {

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();

        demo.testSynchronized();

        try {
            Thread.sleep(1000);
            log.info("----------分隔符----------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        demo.testReentrantLock();
    }

    /**
     * 测试1：验证synchronized是可重入锁
     */
    public void testSynchronized() {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendMessage();
        }, "t1").start();

        new Thread(() -> {
            phone.sendMessage();
        }, "t2").start();
    }

    /**
     * 测试2：验证ReentrantLock是可重入锁
     * 笔记：
     *      1.面试题：
     *          问：lock.lock()可以锁两次吗？
     *          答：可以锁两次（虽然是没意义的，实际开发并不会这样做），但是同时lock.unlock()也要解锁两次，
     *          否则锁将得不得释放，其他线程将一直等待下去。
     *
     */
    public void testReentrantLock() {
        Man man = new Man();

        new Thread(() -> {
            man.eat();
        }, "t3").start();

        new Thread(() -> {
            man.eat();
        }, "t4").start();

    }

    class Phone {
        public synchronized void sendMessage() {
            log.info(Thread.currentThread().getName() + "\t sendMessage");
            sendEmail();
        }

        public synchronized void sendEmail() {
            log.info(Thread.currentThread().getName() + "\t sendEmail");
        }
    }

    class Man {

        Lock lock = new ReentrantLock();

        public void eat() {
            lock.lock();
            try {
                log.info(Thread.currentThread().getName() + "\t eat");
                talk();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void talk() {
            lock.lock();
            try {
                log.info(Thread.currentThread().getName() + "\t talk");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}
