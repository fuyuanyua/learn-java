package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description: 阻塞队列示例
 * @Author: lhb
 * @Date: 2021/6/2 22:11
 *
 * 阻塞队列：
 *      1.特性：
 *          1.阻塞队列也是队列，自然符合FIFO（先进先出）的特性
 *          2.当阻塞队列为空（不是null，指无元素）时，take（获取）元素的操作将被阻塞:
 *              试图从一个空的阻塞队列获取元素的线程将被阻塞，直至其他线程往这个阻塞队列中添加元素
 *          3.当阻塞队列为满时，put（添加）元素的操作将被阻塞：
 *              试图向一个满的阻塞队列添加元素的线程将被阻塞，直至其他线程获取了这个阻塞队列的一个或多个元素或清空了这个阻塞队列
 *
 *      2.优点：可以自动帮我们阻塞或者唤醒线程，而不是让我们手动的去控制
 *
 *      2.接口：BlockingQueue<E> extends Queue<E>
 *
 *      3.实现类：
 *          1.ArrayBlockingQueue：由数组实现的有界阻塞队列（需手动指定容量大小，且默认是非公平访问策略）
 *              public ArrayBlockingQueue(int capacity) {
 *                  this(capacity, false);
 *              }
 *          2.LinkedBlockingQueue：由链表实现的有界阻塞队列（默认大小为Integer.MAX_VALUE）
 *              public LinkedBlockingQueue() {
 *                  this(Integer.MAX_VALUE);
 *              }
 *          3.SynchronousQueue：同步队列，只容纳一个元素的阻塞队列（且默认是非公平访问策略）
 *              // Creates a {@code SynchronousQueue} with nonfair access policy.
 *              public SynchronousQueue() {
 *                  this(false);
 *              }
 */

@Slf4j
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueueDemo blockingQueueDemo = new BlockingQueueDemo();

        blockingQueueDemo.testArrayBlockingQueue1();
        blockingQueueDemo.testArrayBlockingQueue2();
        blockingQueueDemo.testArrayBlockingQueue3();
    }

    /**
     * 测试BlockingQueue的API：
     *      1.add：往队列尾添加元素（若队列满，报异常）
     *      2.remove：从队列头移除元素（若队列空，报异常）
     *      2.element：获取队列头元素，但不移除（若队列空，报异常）
     */
    public void testArrayBlockingQueue1() {
        log.info("----------测试add、remove、element----------");
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);

        log.info("add 1 -> {}", blockingQueue.add(1));
        log.info("add 2 -> {}", blockingQueue.add(2));
        log.info("add 3 -> {}", blockingQueue.add(3));
        // 队列满了调用add方法，报异常：java.lang.IllegalStateException: Queue full
        // blockingQueue.add(4);

        log.info("element -> {}", blockingQueue.element());

        log.info("remove -> {}", blockingQueue.remove());
        log.info("remove -> {}", blockingQueue.remove());
        log.info("remove -> {}", blockingQueue.remove());
        // 队列空了调用remove方法，报异常：java.util.NoSuchElementException
        // blockingQueue.remove();

        // 队列空了调用element方法，报异常：java.util.NoSuchElementException
        // blockingQueue.element();
    }

    /**
     * 测试BlockingQueue的API：
     *      1.offer：往队列尾添加元素（若队列满，返回false）
     *      2.poll：从队列头移除元素（若队列空，返回null）
     *      2.peek：获取队列头元素，但不移除（若队列空，返回null）
     */
    public void testArrayBlockingQueue2() {
        log.info("----------测试offer、poll、peek----------");
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);

        log.info("offer 1 -> {}", blockingQueue.offer(1));
        log.info("offer 2 -> {}", blockingQueue.offer(2));
        log.info("offer 3 -> {}", blockingQueue.offer(3));
        // 队列满了调用offer方法，返回false
        log.info("offer 4 -> {}", blockingQueue.offer(4));

        log.info("peek -> {}", blockingQueue.peek());

        log.info("poll -> {}", blockingQueue.poll());
        log.info("poll -> {}", blockingQueue.poll());
        log.info("poll -> {}", blockingQueue.poll());
        // 队列空了调用poll方法，返回null
        log.info("poll -> {}", blockingQueue.poll());

        // 队列空了调用peek方法，返回null
        log.info("peek -> {}", blockingQueue.peek());
    }

    /**
     * 测试BlockingQueue的API：
     *      1.put：往队列尾添加元素（若队列满，则阻塞）
     *      2.take：从队列头移除元素（若队列空，则阻塞）
     */
    public void testArrayBlockingQueue3() throws InterruptedException {
        log.info("----------测试put、take----------");
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(3);
        blockingQueue.put(1);
        blockingQueue.put(2);
        blockingQueue.put(3);

        // log.info("队列满，阻塞");
        // blockingQueue.put(4);

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        log.info("队列空，阻塞");
        blockingQueue.take();
    }
}
