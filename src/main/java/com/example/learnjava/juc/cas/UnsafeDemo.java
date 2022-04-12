package com.example.learnjava.juc.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description: Unsafe类示例
 * @Author: lhb
 * @Date: 2022/4/12 21:41
 */

@Slf4j
public class UnsafeDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        // 获取该字段偏移地址
        long offset = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
        Student student = new Student(1);
        log.info("CAS前，student：{}", student);
        // CAS
        unsafe.compareAndSwapInt(student, offset, 1, 2);
        log.info("CAS后，student：{}", student);
    }

    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Class<Unsafe> clazz = Unsafe.class;
        // 反射获取theUnsafe字段
        Field theUnsafe = clazz.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        // 获取该字段的值，本应该传一个对象，表示获取该对象下这个字段的值，但Unsafe类中theUnsafe是静态变量，所以传null
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        return unsafe;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Student {
        volatile int id;
    }
}
