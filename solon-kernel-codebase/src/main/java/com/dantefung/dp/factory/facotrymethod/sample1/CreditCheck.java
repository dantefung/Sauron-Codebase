package com.dantefung.dp.factory.facotrymethod.sample1;

public interface CreditCheck {

    /* 赊购限额审查： 根据客户id号返回其赊购限额 */

    double creditLimit(int id);

}