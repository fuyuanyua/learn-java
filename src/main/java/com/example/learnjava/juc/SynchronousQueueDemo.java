package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @Description: 阻塞队列之SynchronousQueue示例
 * @Author: lhb
 * @Date: 2021/6/3 23:11
 *
 * SynchronousQueue
 *      生产一个消费一个，不消费不生产
 */

@Slf4j
public class SynchronousQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<Integer> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                // 生产
                log.info("{} put 1", Thread.currentThread().getName());
                blockingQueue.put(1);

                // 生产
                log.info("{} put 2", Thread.currentThread().getName());
                blockingQueue.put(2);

                // 生产
                log.info("{} put 3", Thread.currentThread().getName());
                blockingQueue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                // 5s后去消费
                Thread.sleep(5 * 1000);
                log.info("{} take", Thread.currentThread().getName());
                blockingQueue.take();

                // 5s后去消费
                Thread.sleep(5 * 1000);
                log.info("{} take", Thread.currentThread().getName());
                blockingQueue.take();

                // 5s后去消费
                Thread.sleep(5 * 1000);
                log.info("{} take", Thread.currentThread().getName());
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
