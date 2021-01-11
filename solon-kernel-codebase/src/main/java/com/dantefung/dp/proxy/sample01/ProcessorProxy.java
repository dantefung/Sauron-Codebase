package com.dantefung.dp.proxy.sample01;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ProcessorProxy
 * jdk动态代理动态拦截每一个消息处理器
 * 执行签名校验、AES加密校验、设备有效性校验、设备登录状态校验
 *
 * @author DANTE FUNG
 * @create 2019年12月25日 10:31:34
 **/
@Slf4j
public class ProcessorProxy<T> implements InvocationHandler {

	private Object target;

	public ProcessorProxy(Object target) {
		this.target = target;
	}

	/**
	 * 获取被代理接口实例对象
	 * @param <T>
	 * @return
	 */
	public <T> T getProxy() {
		Class<?> superclass = target.getClass().getSuperclass();
		Class<?>[] interfaces = null;
		if (!(superclass instanceof Object)) {
			interfaces = target.getClass().getSuperclass().getInterfaces();
		} else {
			interfaces = target.getClass().getInterfaces();
		}
		return (T) Proxy
				.newProxyInstance(target.getClass().getClassLoader(), interfaces,
						this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info("消息处理器代理[{}]-目标对象[{}]-START...", proxy.getClass().getSimpleName(), target.getClass().getSimpleName());
		if (log.isDebugEnabled()) {
			log.debug("target:{} method:{} args:{}", target.getClass().getSimpleName(), method, args);
		}
		Object result = null;
		try {
			result = method.invoke(target, args);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
		log.info("消息处理器代理[{}]-目标对象[{}]-END...", proxy.getClass().getSimpleName(), target.getClass().getSimpleName());
		return result;
	}

}