package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Description: 信号量示例
 * @Author: lhb
 * @Date: 2021/6/1 22:24
 *
 * 信号量：
 *      1.说明：
 *          1.用于控制多个线程争抢多个资源
 *          2.用于并发线程数量的控制
 *      2.示例：
 *          以下用抢车位来演示信号量的使用
 */

@Slf4j
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 模拟3个停车位
        Semaphore semaphore = new Semaphore(3);

        // 一个线程代表一辆车，模拟6辆车抢车位
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    // 获得信号量
                    semaphore.acquire();
                    log.info("{} 抢到了车位", Thread.currentThread().getName());
                    Thread.sleep(1000 * finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log.info("{} 停车{}s后离开车位", Thread.currentThread().getName(), finalI);
                    // 释放信号量
                    semaphore.release();
                }
            }, "t" + i).start();
        }
    }
}
