package com.example.learnjava.juc.cas.account;

/**
 * @Description: Account
 * @Author: lhb
 * @Date: 2022/4/12 14:53
 */

public interface Account {

    /**
     * 获取账户余额
     *
     * @return
     */
    int getBalance();

    /**
     * 取款
     *
     * @param amount 取款金额
     */
    void withdraw(int amount);
}
