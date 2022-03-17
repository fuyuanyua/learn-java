package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

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
 *
 *      2.线程池创建线程的优点：
 *          1.线程可复用，减少反复创建线程对象、垃圾回收的消耗
 *          2.可以控制并发最大的线程数
 *          3.可以有效的管理线程
 *
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
 *      4.线程池工作流程：
 *          1.线程池被创建，创建了corePoolSize个核心线程等待执行任务
 *          2.核心线程执行任务满了，后续进来的任务就进入阻塞队列
 *          3.阻塞队列也满了，就创建新的非核心线程并立即执行后续进来的任务，直到线程总数达到maximumPoolSize
 *          4.当线程总数达到maximumPoolSize且都在执行任务，线程池就会采取拒绝策略来处理后续进来的任务
 *          5.其他说明：
 *              1.当一个线程执行完任务后，它会从阻塞队列取下一个任务来执行
 *              2.任务都执行完了，当线程的空闲时间超过keepAliveTime，线程会被销毁，直到线程池的线程总数降回到corePoolSize
 *
 *      5.拒绝策略：
 *          1.AbortPolicy：直接抛异常
 *          2.CallerRunsPolicy：不抛异常也不丢弃任务，而是把任务回退给调用者线程
 *          3.DiscardOldestPolicy：丢弃队列中等待最久的任务，然后把新任务加入到队列中
 *          4.DiscardPolicy：直接丢弃新任务
 *
 *      6.实际生产中使用什么方式创建线程池？
 *          不允许使用Executors创建，而是手动自定义创建线程池
 *          参考阿里巴巴Java开发手册：https://github.com/alibaba/p3c/blob/master/Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E5%B5%A9%E5%B1%B1%E7%89%88%EF%BC%89.pdf
 *          1.Executors.newFixedThreadPool(n)和Executors.newSingleThreadExecutor()
 *              源码中他们的阻塞队列采用了new LinkedBlockingQueue<Runnable>()，表示队列长度为Integer.MAX_VALUE
 *              几乎可以认为是无界队列，那么就会堆积大量任务，导致OOM
 *          2.Executors.newCachedThreadPool()
 *              源码中maximumPoolSize参数设置为了Integer.MAX_VALUE，几乎可以认为会无限制地创建非核心线程，导致OOM
 *
 *      7.线程池参数如何合理配置？
 *          corePoolSize和maximumPoolSize需要设置成一样
 *          主要关注maximumPoolSize这个参数，根据cpu的逻辑核心数设置，比如我的设备cpu为四核八线程，那么逻辑核心数为8
 *          1.如果是cpu密集型业务：
 *              cpu密集型表示该任务需要大量运算，没有阻塞，cpu一直在全负荷的跑
 *              那么maximumPoolSize设为（cpu逻辑核心数 + 1），那么在我的设备上就设置为9
 *          2.如果是io密集型业务：
 *              io密集型例如经常的从数据库或缓存读数据、写数据，存在大量阻塞，那么有以下两种配置：
 *              1.maximumPoolSize设置为（cpu逻辑核心数 * 2），那么在我的设备上就设置为16
 *              2.maximumPoolSize设置为（cpu逻辑核心数 / (1 - 阻塞系数)），阻塞系数在0.8～0.9之间，
 *              假设阻塞系数为0.9，那么在我的设备上就设置为80
 *          注意：以上公式都是理论情况，但实际生产还是应该根据实际的具体情况来调整参数，以达到最优状态
 *
 */

@Slf4j
public class ThreadPoolDemo {

    public static void main(String[] args) {
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
     *      当阻塞队列满后，如果再有新任务进来：将会开启新的线程，并立即执行新进来的任务，而不是执行阻塞队列内的任务
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
            for (int i = 0; i < 9; i++) {
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
