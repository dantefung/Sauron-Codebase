package com.dantefung.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class )
public class TestDemoParamter {
    private String target;
    private String expected;
 
    @Parameterized.Parameters
    public static Collection setParam() {
       return Arrays.asList(new Object[][] { { "emplee_info", "empleeInfo" }, // 测试正常情况
              { null, null }, // 测试null时处理情况
              { "", "" }, // 测试空字符串的情况
              { "employee_info", "EmployeeInfo" }, // 测试当首字母大写时的情况
              { "employee_info_a", "employeeInfoA" }, // 测试当尾字母为大写时的情况
              { "employee_a_info", "employeeAInfo" } // 测试多个相连字母大写时的情况
              });
    }
 
    /**
     * 参数化测试必须的构造函数
     * 
     * @param expected 期望的测试结果 ，对应参数集中的第一个参数
     * @param target 测试数据，对应结果集中的第二个参数
     */
    public TestDemoParamter(String target, String expected) {
       this.expected = expected;
       this.target = target;
    }
    
    @Test
    public void testParam(){
       Assert.assertEquals(expected, target);
    }
}