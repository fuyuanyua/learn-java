package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description: ArrayList非线程安全示例
 * @Author: lhb
 * @Date: 2021/5/23 21:30
 *
 * 1.场景：java.util.ConcurrentModificationException：并发修改异常
 * 2.原因：ArrayList是线程不安全的
 * 3.解决方案：
 *      3.1 使用Vector类：List<String> list = new Vector<>();
 *      3.2 使用Collections工具类：List<String> list = Collections.synchronizedList(new ArrayList<>());
 *      3.3 使用CopyOnWriteArrayList类：List<String> list = new CopyOnWriteArrayList<>();
 * 4.笔记：
 *      4.1 CopyOnWriteArrayList的add方法源码：
 *          public boolean add(E e) {
 *             final ReentrantLock lock = this.lock;
 *             lock.lock();
 *             try {
 *                 Object[] elements = getArray();
 *                 int len = elements.length;
 *                 Object[] newElements = Arrays.copyOf(elements, len + 1);
 *                 newElements[len] = e;
 *                 setArray(newElements);
 *                 return true;
 *             } finally {
 *                 lock.unlock();
 *             }
 *         }
 *      4.2 源码解读：
 *          add时加锁，并且不在原集合上直接添加元素，而是copy出一个新的集合，在新集合上添加元素，
 *          添加完后将原容器引用指向新容器，最后释放锁。
 */

@Slf4j
public class ArrayListUnsafeDemo {

    public static void main(String[] args) {
        ArrayListUnsafeDemo demo = new ArrayListUnsafeDemo();
        demo.unsafe();
    }

    /**
     * 线程不安全示例：使用ArrayList
     */
    public void unsafe() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                log.info("list = {}", list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 线程安全示例1：使用Vector
     */
    public void safe1() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                log.info("list = {}", list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 线程安全示例2：使用使用Collections工具类
     */
    public void safe2() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                log.info("list = {}", list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 线程安全示例3：使用CopyOnWriteArrayList
     */
    public void safe3() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                log.info("list = {}", list);
            }, String.valueOf(i)).start();
        }
    }
}
