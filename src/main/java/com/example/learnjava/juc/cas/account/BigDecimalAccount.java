package com.example.learnjava.juc.cas.account;

import java.math.BigDecimal;

/**
 * @Description: BigDecimalAccount
 * @Author: lhb
 * @Date: 2022/4/12 18:28
 */

public interface BigDecimalAccount {

    /**
     * 获取账户余额
     *
     * @return
     */
    BigDecimal getBalance();

    /**
     * 取款
     *
     * @param amount 取款金额
     */
    void withdraw(BigDecimal amount);
}
