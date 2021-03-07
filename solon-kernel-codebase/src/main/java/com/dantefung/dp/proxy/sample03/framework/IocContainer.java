/*
 * Copyright (C), 2015-2020
 * FileName: IocContainer
 * Author:   DANTE FUNG
 * Date:     2021/3/7 14:42
 * Description: 极简版ioc容器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 14:42   V1.0.0
 */
package com.dantefung.dp.proxy.sample03.framework;


import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: IocContainer
 * @Description: 极简版ioc容器
 * @author DANTE FUNG
 * @date 2021/03/07 14/42
 * @since JDK1.8
 */
public class IocContainer {

	public static final Map<String, BeanDefinition> BEAN_DEFINITION_MAP = new ConcurrentHashMap<>();

	// 一般会有多个切面, 这里简化为一个.
	public Aspect aspect;

	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		BEAN_DEFINITION_MAP.put(beanName, beanDefinition);
	}

	public Object getBean(String beanName) throws IllegalAccessException, InstantiationException {
		// 需要能得到用户给定的bean的定义
		Object bean = doCreateBean(beanName);
		// 执行Bean初始化操作: xxxAware、InitializingBean、BeanPostProcessor、init-method。aop就是用后置处理器实现的。
		// 这里简化一下实现代理.
		bean = proxyEnhance(bean);
		return bean;
	}

	private Object proxyEnhance(Object bean) {
		if (bean.getClass().getName().matches(aspect.getPointcut().getClassPattern())) {
			return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
					new AopInvocationHandler(bean, aspect));
		}

		return bean;
	}

	private Object doCreateBean(String beanName) throws IllegalAccessException, InstantiationException {
		return BEAN_DEFINITION_MAP.get(beanName).getBeanClass().newInstance();
	}

	public void setAspect(Aspect aspect) {
		this.aspect = aspect;
	}
}
