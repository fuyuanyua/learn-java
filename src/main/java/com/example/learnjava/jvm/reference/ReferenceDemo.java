package com.example.learnjava.jvm.reference;

import com.example.learnjava.str.Car;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @Description: ReferenceDemo
 * @Author: lhb
 * @Date: 2022/3/30 22:32
 */

public class ReferenceDemo {

    public static void main(String[] args) {
        // 强引用
        // 只有所有GC Roots都没有通过引用链找到该对象，该对象【堆中的car】才会被垃圾回收
        Car car = new Car();

        // 软引用，在GC后内存空间仍然不足，会回收软引用对象引用的对象【堆中的car】
        // 可以配合引用队列来回收软引用对象本身【堆中的softReference】
        SoftReference<Car> softReference = new SoftReference<>(car);

        // 弱引用，在GC时时就会回收弱引用对象引用的对象【堆中的car】
        // 可以配合引用队列来回收弱引用对象本身【堆中的weakReference】
        WeakReference<Car> weakReference = new WeakReference<>(car);

        // 虚引用，当虚引用引用的对象【堆中的car】被垃圾回收，就把虚引用对象本身【堆中的phantomReference】放入引用队列
        // 必须配合引用队列来使用
        // NIO中，就有一个Cleaner虚引用对象引用了ByteBuffer对象，管理了堆外的内存（ByteBuffer是使用的系统直接内存）
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Car> phantomReference = new PhantomReference<>(car, referenceQueue);
    }
}
