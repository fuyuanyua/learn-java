package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: OptimisticLockDemo
 * @Author: lhb
 * @Date: 2021/7/15 下午8:52
 */

@Slf4j
public class OptimisticLockDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        // 五个线程分别同时对atomicInteger执行100次getAndIncrement方法，
        // 因为getAndIncrement使用了CAS算法，所以最终结果一定是500
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    atomicInteger.getAndIncrement();
                }
            }, "t" + i).start();
        }

        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("最终计算结果 = {}", atomicInteger.intValue());
    }
}
