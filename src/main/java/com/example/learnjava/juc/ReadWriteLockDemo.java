package com.example.learnjava.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 读写锁示例
 * @Author: lhb
 * @Date: 2021/5/30 23:11
 *
 * 读写锁
 *      1.说明：
 *          读-读能共存
 *          读-写不能共存
 *          写-写不能共存
 */

public class ReadWriteLockDemo {

    /**
     * 资源类，模拟缓存，get操作上读锁、put操作上写锁
     */
    public static class MyCache {

        private volatile Map<String, Object> cache = new HashMap<>();

        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void put(String key, Object value) {
            // 写锁
            ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
            writeLock.lock();
            try {

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }


    }
}
