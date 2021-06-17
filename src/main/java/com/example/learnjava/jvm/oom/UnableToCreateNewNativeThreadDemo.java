package com.example.learnjava.jvm.oom;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: UnableToCreateNewNativeThreadDemo
 * @Author: lhb
 * @Date: 2021/6/16 下午10:39
 *
 * 报错：
 *      java.lang.OutOfMemoryError: unable to create new native thread
 * 原因：
 *      一般来说，操作系统不允许一个应用无限制的创建新线程，不同的操作系统，限制不同，这是由操作系统决定的，
 *      在Linux下，非root用户，一个进程最多创建1024个线程，可以用命令查看：ulimit -u
 */

@Slf4j
public class UnableToCreateNewNativeThreadDemo {

    public static void main(String[] args) {
        // 不停地创建新线程
        // 在本机macOS下，创建到第4070个线程，报错误
        // java.lang.OutOfMemoryError: unable to create new native thread
        for (int i = 0; ; i++) {
            log.info("创建第{}个线程", i);
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
