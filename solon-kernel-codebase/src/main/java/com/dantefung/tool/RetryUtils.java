package com.dantefung.tool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class RetryUtils {
    
    /**
     * @param time
     * @param timeUnit
     * @throws IllegalStateException
     */
	public final static void sleep(long time, TimeUnit timeUnit) {
		final long ms = timeUnit.toMillis(time);
		if (ms < 0) {
			return;
		}

		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
    
    /**
     * @param ms
     * @throws IllegalStateException
     */
    public final static void sleep(long ms) {
		sleep(ms, TimeUnit.MILLISECONDS);
    }
    
    /**
     * <pre>
     * 对可重试的接口, 进行重试调用场景, 在接口有异常情况下, 才会重试
     * 
     * 注意:
     * 1.在重试n次后, 有一次成功, 则返回成功, 仍然失败, 则报异常, 告知失败
     * </pre>
     * @param retryable
     * @param retryCount  >= 1
     * @param retrySleepMs <  0 not sleep
     * @return RuntimeException || V
     */
    public final static <V> V retry(Retryable<V> retryable, int retryCount, long retrySleepMs) throws RuntimeException{
        Throwable ex = null;
        short iCount = 1;
        do {
            try {
                return retryable.retry(iCount);
            } catch (Throwable e) {
                ex = e;
                sleep(retrySleepMs);
            }
            
        }while(++ iCount <= retryCount);
        
        throw new RuntimeException("Retry error, retry:"+(iCount-1)+", total count:"+retryCount+", sleep:"+retrySleepMs +"ms", ex);
    }
    
    /**
     * <pre>
     * 对可重试的接口, 进行重试调用场景, 在接口有异常情况下, 才会重试
     * 
     * 注意:
     * 1.在重试n次后, 有一次成功, 则返回成功, 仍然失败, 则报异常, 告知失败
     * </pre>
     * @param retryable
     * @param retryCount  >= 1
     * @param retrySleepMs <  0 not sleep
     * @return RuntimeException || V
     */
    public final static <V> V retry(Callable<V> retryable, int retryCount, long retrySleepMs) throws RuntimeException{
        Throwable ex = null;
        short iCount = 1;
        do {
            try {
                return retryable.call();
            } catch (Throwable e) {
                ex = e;
                sleep(retrySleepMs);
            }
        }while(++ iCount <= retryCount);
        
        throw new RuntimeException("Retry error, retry "+(iCount-1)+", total count:"+retryCount+", sleep:"+retrySleepMs + "ms", ex);
    }

    /**
     * 安全环绕方法,用于解决调用方法有异常的情况
     * @param call
     * @param <V>
     * @return
     */
    public final static <V> V safeAround(Callable<V> call){
        return safeAround(call, null);
    }

    /**
     * 安全环绕方法,用于解决调用方法有异常的情况
     * @param call
     * @param defaultValue 返回的默认值
     * @param <V>
     * @return
     */
    public final static <V> V safeAround(Callable<V> call, V defaultValue){

        try {
            return call.call();
        } catch (Throwable e) {
            log.error("safeAround ignore error:", e);
        }

        return defaultValue;
    }

    public static interface Retryable<RS> {
        /**
         * 需要重试, 抛出异常
         * 方法体内有异常情况下, 才会重试
         * @param iCount
         * @return
         * @throws Exception
         */
        RS retry(int iCount) throws Exception;
    }

    /**
     * RetryException
     */
    public static class RetryException extends RuntimeException{

        public RetryException() {
            super();
        }

        public RetryException(String message) {
            super(message);
        }

        public RetryException(String message, Throwable cause) {
            super(message, cause);
        }

        public RetryException(Throwable cause) {
            super(cause);
        }

    }
}