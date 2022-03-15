package com.example.learnjava.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description: 线程创建示例
 * @Author: lhb
 * @Date: 2021/7/19 下午8:13
 */

public class ThreadDemo {

    public static void main(String[] args) {
        // 1.继承Thread类
        Thread thread = new MyThread();
        thread.start();

        // 2.1 实现Runnable接口
        Thread thread1 = new Thread(new MyThread1());
        thread1.start();

        // 2.2 实现Runnable接口的另一张写法
        new Thread(() -> {
            // 写逻辑
        }, "t1").start();

        // 3.实现Callable接口
        FutureTask futureTask = new FutureTask(new MyThread2());
        Thread thread2 = new Thread(futureTask);
        thread2.start();
        // 拿到run方法的返回结果
        try {
            Object o = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            // 写逻辑
        }
    }

    static class MyThread1 implements Runnable {

        @Override
        public void run() {
            // 写逻辑
        }
    }

    static class MyThread2 implements Callable {

        @Override
        public Object call() throws Exception {
            // 写逻辑
            return "success";
        }
    }
}
