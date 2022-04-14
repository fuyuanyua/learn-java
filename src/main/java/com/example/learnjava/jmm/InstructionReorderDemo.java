package com.example.learnjava.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 指令重排示例
 * @Author: lhb
 * @Date: 2022/4/13 15:58
 */

@Slf4j
public class InstructionReorderDemo {

    private static boolean flag = false;

    private static int num = 0;

    // 线程1调用此方法
    public static int get() {
        if (flag) {
            return num * num;
        }
        return 1;
    }

    // 线程2调用此方法
    public static void set() {
        num = 2;
        flag = true;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            set();
        }, "t2").start();

        new Thread(() -> {
            int result = get();
            log.info("result = {}", result);
        }, "t1").start();
    }
}
