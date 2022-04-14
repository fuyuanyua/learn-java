package com.example.learnjava.jmm;

/**
 * @Description: 可见性示例
 * @Author: lhb
 * @Date: 2022/4/13 12:28
 */

public class VisibleDemo {

    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
                // do something
            }
        }, "t1").start();

        Thread.sleep(100);
        flag = false;
    }
}
