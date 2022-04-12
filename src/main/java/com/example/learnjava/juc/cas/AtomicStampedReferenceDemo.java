package com.example.learnjava.juc.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description: AtomicStampedReference示例：解决ABA问题
 * @Author: lhb
 * @Date: 2022/4/12 20:50
 */

@Slf4j
public class AtomicStampedReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

        String initValue = ref.getReference();
        int initStamp = ref.getStamp();
        log.info("【{}】获取到初始值【{}】，初始版本号【{}】", Thread.currentThread().getName(), initValue, initStamp);

        Thread.sleep(200);

        new Thread(() -> {
            while (true) {
                String preValue = ref.getReference();
                String nextValue = "B";
                int preStamp = ref.getStamp();
                int nextStamp = preStamp + 1;
                if (ref.compareAndSet(preValue, nextValue, preStamp, nextStamp)) {
                    log.info("【{}】将【{}】->【{}】，【{}】->【{}】", Thread.currentThread().getName(), preValue, nextValue, preStamp, nextStamp);
                    break;
                }
            }
        }, "t2").start();

        Thread.sleep(200);

        new Thread(() -> {
            while (true) {
                String preValue = ref.getReference();
                String nextValue = "A";
                int preStamp = ref.getStamp();
                int nextStamp = preStamp + 1;
                if (ref.compareAndSet(preValue, nextValue, preStamp, nextStamp)) {
                    log.info("【{}】将【{}】->【{}】，【{}】->【{}】", Thread.currentThread().getName(), preValue, nextValue, preStamp, nextStamp);
                    break;
                }
            }
        }, "t3").start();

        boolean compareAndSet = ref.compareAndSet(initValue, "B", initStamp, initStamp + 1);
        log.info("【{}】CAS【{}】", Thread.currentThread().getName(), compareAndSet ? "成功" : "失败");
    }

}
