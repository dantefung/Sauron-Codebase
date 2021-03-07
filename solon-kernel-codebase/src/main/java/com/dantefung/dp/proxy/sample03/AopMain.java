/*
 * Copyright (C), 2015-2020
 * FileName: AopMain
 * Author:   DANTE FUNG
 * Date:     2021/3/7 14:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 14:26   V1.0.0
 */
package com.dantefung.dp.proxy.sample03;

import com.dantefung.dp.proxy.sample03.client.TargetObject;
import com.dantefung.dp.proxy.sample03.client.TargetObjectImpl;
import com.dantefung.dp.proxy.sample03.client.TimeCsAdvice;
import com.dantefung.dp.proxy.sample03.framework.*;

/**
 * @Title: AopMain
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/07 14/26
 * @since JDK1.8
 */
public class AopMain {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// 使用者提供的增强逻辑
		Advice timeCsAdvice = new TimeCsAdvice();
		String classPattern = "com\\.dantefung\\.dp\\.proxy\\.sample03\\.client\\..*";
		String methodPattern = ".*Message";
		// 使用者提供的切入点
		Pointcut pointcut = new Pointcut(classPattern, methodPattern);
		Aspect aspect = new Aspect(timeCsAdvice, pointcut);
		// 到这里客户要干得事情就完成了， 现在我们完善框架的功能了

//		TargetObjectImpl targetObject = new TargetObjectImpl();
//		targetObject.showMessage();

		// 如果要AOP， 就不能让用户去new对象.
		// 这个时候就需要一个工厂来负责提供对象，就是需要一个IOC
		// 写个简化版的IOC容器
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClassName("com.dantefung.dp.proxy.sample03.client.TargetObjectImpl");
		IocContainer iocContainer = new IocContainer();
		iocContainer.registerBeanDefinition("targetObject", beanDefinition);
		iocContainer.setAspect(aspect);
		TargetObject targetObject = (TargetObject)iocContainer.getBean("targetObject");
		targetObject.showMessage();

	}
}
