package com.dantefung.dp.observer;

import junit.framework.AssertionFailedError;

import org.junit.Test;

/**
 * 
 * @author DanteFung
 *
 */
public interface TestListener {
	
	/**
	 * An error occurred.
	 * @param test
	 * @param t
	 */
	public void addError(Test test, Throwable t);
	
	/**
	 * A failure occured.
	 * @param test
	 * @param t
	 */
	public void addFailure(Test test, AssertionFailedError t);
	
	/**
	 * A TEST end.
	 * @param test
	 */
	public void endTest(Test test);
	
	/**
	 * A test started.
	 * @param test
	 */
	public void startTest(Test test);
}
