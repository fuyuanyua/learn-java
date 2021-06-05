package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.*;

/**
 * @Description: 线程池示例
 * @Author: lhb
 * @Date: 2021/6/5 11:04
 *
 * 线程池：
 *      1.Java创建线程的四种方式：
 *          1.继承Thread类
 *          2.实现Runnable接口
 *          3.实现Callable接口
 *          4.通过线程池创建
 *      2.线程池创建线程的优点：
 *          1.线程可复用，减少反复创建线程对象、垃圾回收的消耗
 *          2.可以控制并发最大的线程数
 *          3.可以有效的管理线程
 *      3.创建线程池：
 *          1.借助Executors工具类：
 *              常用有以下几种：
 *              1.Executors.newFixedThreadPool(n); 创建固定n个线程的线程池
 *                  源码：
 *                  new ThreadPoolExecutor(nThreads, nThreads,
 *                                       0L, TimeUnit.MILLISECONDS,
 *                                       new LinkedBlockingQueue<Runnable>());
 *
 *              2.Executors.newSingleThreadExecutor(); 创建单线程的线程池
 *                  源码：
 *                  new ThreadPoolExecutor(1, 1,
 *                                     0L, TimeUnit.MILLISECONDS,
 *                                     new LinkedBlockingQueue<Runnable>())
 *
 *              3.Executors.newCachedThreadPool(); 创建可缓存的线程池
 *                  源码：
 *                  new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 *                                       60L, TimeUnit.SECONDS,
 *                                       new SynchronousQueue<Runnable>());
 *          2.手动创建线程池：
 *              new一个ThreadPoolExecutor对象
 *              1.ThreadPoolExecutor构造方法源码：
 *                  public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler) {
 *                               // 省略具体
 *                  }
 *              2.关注构造方法的七个参数：
 *                  1.corePoolSize：线程池中常驻的核心线程数
 *                  2.maximumPoolSize：线程池所能容纳的最大线程数
 *                  3.keepAliveTime：
 *                      多余空闲线程的存活时间，
 *                      当线程池的线程数超过corePoolSize时，多余的那些线程空闲时间超过keepAliveTime，
 *                      这些线程将被销毁，直至线程池的线程数降回到corePoolSize
 *                  4.unit：存活时间的时间单位
 *                  5.workQueue：阻塞队列，当核心线程满了之后，新的任务将进入阻塞队列
 *                  6.threadFactory：线程工厂，用于创建线程池中的线程对象，一般用默认即可
 *                  7.handler：拒绝策略
 *
 */

@Slf4j
public class ThreadPoolDemo {

    public static void main(String[] args) {
        // log.info("{}", Runtime.getRuntime().availableProcessors());

        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();

        // 测试用Executors创建线程池
        // threadPoolDemo.testExecutors();

        // 测试自定义线程池
        threadPoolDemo.testMyThreadPool();

    }

    /**
     * 测试用Executors创建线程池
     */
    public void testExecutors() {
        // 创建固定5个线程的线程池
        // ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 创建单线程的线程池
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();

        // 创建可缓存的线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            // 模拟10个任务进来
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    log.info("{} 处理任务", Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    /**
     * 测试自定义线程池，并验证各参数功能
     *
     * 验证：
     *      当阻塞队列满后，有新任务进来，将会开启新的线程，并立即执行新进来的任务，而不是阻塞队列内的任务
     */
    public void testMyThreadPool() {
        // 创建自定义线程池
        ExecutorService myThreadPool = new ThreadPoolExecutor(
                // 核心线程数
                2,
                // 最大线程数
                5,
                // 空闲线程最大存活时间
                100L,
                // 存活时间的时间单位
                TimeUnit.SECONDS,
                // 阻塞队列
                new LinkedBlockingDeque<>(3),
                // 线程工厂
                Executors.defaultThreadFactory(),
                // 拒绝策略
                new ThreadPoolExecutor.AbortPolicy());

        // 模拟8个任务进来
        try {
            for (int i = 0; i < 8; i++) {
                int finalI = i;
                myThreadPool.execute(() -> {
                    log.info("{} 处理任务{}", Thread.currentThread().getName(), finalI + 1);
                    try {
                        // 每个任务要处理4s
                        Thread.sleep(1000 * 4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myThreadPool.shutdown();
        }
    }
}
