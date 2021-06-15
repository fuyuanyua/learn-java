package com.example.learnjava.jvm;

/**
 * @Description: JavaHeapSpaceOomDemo
 * @Author: lhb
 * @Date: 2021/6/15 下午10:51
 */

public class JavaHeapSpaceOomDemo {

    public static void main(String[] args) {
        // java.lang.OutOfMemoryError: Java heap space
        byte[] bytes = new byte[1024 * 1024 * 80];
    }
}
