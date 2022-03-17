package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 闭锁示例
 * @Author: lhb
 * @Date: 2021/10/21 上午10:04
 */

@Slf4j
public class CountDownLatchDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newScheduledThreadPool(3);
        Random random = new Random();
        while (true) {
            // 初始计数为3
            CountDownLatch latch = new CountDownLatch(3);
            for (int i = 0; i < 3; i++) {
                threadPool.execute(() -> {
                    // do something
                    try {
                        Thread.sleep(random.nextInt(5) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("线程 {} 执行完业务", Thread.currentThread().getName());
                    // 计数-1
                    latch.countDown();
                });
            }
            try {
                // 线程阻塞直到计数为0
                latch.await();
                log.info("所有子线程都已执行完任务，主线程开始执行任务");
                // do something
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
