package com.example.learnjava.juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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

@Slf4j
public class ReadWriteLockDemo {

    /**
     * 测试多线程操作缓存，以验证读写锁
     * @param args
     */
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        // 5个线程同时写入
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(String.valueOf(finalI), finalI);
            }, "writeThread" + i).start();
        }

        // 5个线程同时读
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(String.valueOf(finalI));
            }, "readThread" + i).start();
        }

    }

    /**
     * 资源类，模拟缓存，get操作上读锁、put操作上写锁
     */
    public static class MyCache {

        private volatile Map<String, Object> map = new HashMap<>();

        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        /**
         * 写缓存
         * @param key
         * @param value
         */
        public void put(String key, Object value) {
            // 写锁
            ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
            writeLock.lock();
            try {
                log.info("{} 正在写入缓存，key = {}", Thread.currentThread().getName(), key);
                // 线程暂停0.3s，模拟真实业务场景
                Thread.sleep(300);
                map.put(key, value);
                log.info("{} 写入缓存完成", Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }

        /**
         * 读缓存
         * @param key
         */
        public void get(String key) {
            ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
            readLock.lock();
            try {
                log.info("{} 正在读取缓存，key = {}", Thread.currentThread().getName(), key);
                // 线程暂停0.3s，模拟真实业务场景
                Thread.sleep(300);
                Object value = map.get(key);
                log.info("{} 读取缓存完成，value = {}", Thread.currentThread().getName(), value);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
        }

        /**
         * 清空缓存
         */
        public void clear() {
            ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
            writeLock.lock();
            try {
                log.info("{} 正在清空缓存", Thread.currentThread().getName());
                // 线程暂停0.3s，模拟真实业务场景
                Thread.sleep(300);
                map.clear();
                log.info("{} 清空缓存完成", Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }

    }
}
