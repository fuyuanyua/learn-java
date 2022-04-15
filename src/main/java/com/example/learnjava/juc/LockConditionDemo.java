package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: LockConditionDemo
 * @Author: lhb
 * @Date: 2022/4/15 14:31
 *
 * 说明：
 *      1.Condition就类似于synchronized锁的monitor中的WaitSet
 *        不过一个monitor只有一个WaitSet，而一个lock可以有多个condition
 *      2.condition.await类似于obj.wait
 *      3.condition.signal、signalAll类似于obj.notify、notifyAll
 *      4.obj.await、notify、notifyAll都需要获取到synchronized对象锁才能调用
 *        condition.await、signal、signalAll同样也需要获取到lock锁才能调用
 */

@Slf4j
public class LockConditionDemo {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                log.info("t1获取到锁");
                // do something
                log.info("t1释放锁，进入condition1等待");
                condition1.await();
                log.info("t1被唤醒");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.info("t2获取到锁");
                // do something
                condition1.signal();
                log.info("t2唤醒condition1中的一个线程");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
