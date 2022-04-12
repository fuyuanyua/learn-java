package com.example.learnjava.juc.cas;

import com.example.learnjava.juc.cas.account.Account;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: CasDemo
 * @Author: lhb
 * @Date: 2022/4/12 14:59
 */

@Slf4j
public class CasDemo {

    public static void main(String[] args) throws InterruptedException {
        // 多个线程操作此账户，不会产生线程安全问题
        Account casAccount = new CasAccount(10000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                casAccount.withdraw(10);
            }, String.valueOf(i)).start();
        }
        Thread.sleep(1000);
        log.info("casAccount余额：{}", casAccount.getBalance());

        // 多个线程操作此账户，会产生线程安全问题
        Account normalAccount = new NormalAccount(10000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                normalAccount.withdraw(10);
            }, String.valueOf(i)).start();
        }
        Thread.sleep(1000);
        log.info("normalAccount余额：{}", normalAccount.getBalance());
    }


    /**
     * 线程安全账户：采用cas
     */
    static class CasAccount implements Account {

        /**
         * 账户余额
         */
        private AtomicInteger balance;

        public CasAccount(int balance) {
            this.balance = new AtomicInteger(balance);
        }

        @Override
        public int getBalance() {
            return balance.get();
        }

        @Override
        public void withdraw(int amount) {
            while (true) {
                // 获取最新余额
                int pre = balance.get();
                // 期望更新的值
                int next = pre - amount;
                // cas，若成功，退出循环；若失败，进行下一次cas
                if (balance.compareAndSet(pre, next)) {
                    break;
                }
            }
        }
    }

    /**
     * 线程非安全账户：未加任何锁
     */
    static class NormalAccount implements Account {

        /**
         * 账户余额
         */
        private Integer balance;

        public NormalAccount(int balance) {
            this.balance = balance;
        }

        @Override
        public int getBalance() {
            return balance;
        }

        @Override
        public void withdraw(int amount) {
            balance -= amount;
        }
    }
}
