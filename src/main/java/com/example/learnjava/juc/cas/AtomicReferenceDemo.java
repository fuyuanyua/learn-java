package com.example.learnjava.juc.cas;

import com.example.learnjava.juc.cas.account.BigDecimalAccount;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: AtomicReferenceDemo
 * @Author: lhb
 * @Date: 2022/4/12 18:24
 */

@Slf4j
public class AtomicReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        // 测试，多个线程操作此账户，不会产生线程安全问题
        BigDecimalAccount bigDecimalCasAccount = new BigDecimalCasAccount(new BigDecimal("10000"));
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                bigDecimalCasAccount.withdraw(new BigDecimal("10"));
            }, "xxx").start();
        }
        Thread.sleep(1000);
        log.info("bigDecimalCasAccount余额：{}", bigDecimalCasAccount.getBalance());
    }

    /**
     * 线程安全账户
     */
    static class BigDecimalCasAccount implements BigDecimalAccount {

        /**
         * balance变量为BigDecimal类型，且被AtomicReference保护起来
         */
        private AtomicReference<BigDecimal> balance;

        public BigDecimalCasAccount(BigDecimal balance) {
            this.balance = new AtomicReference<>(balance);
        }

        @Override
        public BigDecimal getBalance() {
            return balance.get();
        }

        @Override
        public void withdraw(BigDecimal amount) {
            while (true) {
                BigDecimal pre = balance.get();
                BigDecimal next = pre.subtract(pre);
                // CAS
                if (balance.compareAndSet(pre, next)) {
                    break;
                }
            }
        }
    }
}
