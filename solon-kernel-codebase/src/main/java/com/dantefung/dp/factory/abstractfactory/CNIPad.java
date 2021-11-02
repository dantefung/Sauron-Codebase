package com.dantefung.dp.factory.abstractfactory;

/**
 * 具体产品： 实现  IPad 接口【针对中国市场】
 */
public class CNIPad implements IPad {

    public String price() {
        return "2999RMB";
    }

    public String policy() {
        return "1年保修";
    }

}