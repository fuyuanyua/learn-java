package com.example.learnjava.jvm.oom;

/**
 * @Description: JavaHeapSpaceOomDemo
 * @Author: lhb
 * @Date: 2021/6/15 下午10:51
 *
 * 报错：
 *      java.lang.OutOfMemoryError: Java heap space
 * 原因：
 *      堆内存空间不足
 *
 */

public class JavaHeapSpaceDemo {

    public static void main(String[] args) {
        // JVM参数配置：-Xms10m -Xmx10m
        // 创建一个80MB的大对象，报错误
        // java.lang.OutOfMemoryError: Java heap space
        byte[] bytes = new byte[1024 * 1024 * 80];
    }
}
