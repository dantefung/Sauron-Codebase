package com.dantefung.io.nio.nioserver.impl;


import com.dantefung.io.nio.nioserver.InputHandler;

/**
 * Created by IntelliJ IDEA.
 * User: ron
 * Date: Mar 18, 2007
 * Time: 5:47:51 PM
 */
public interface InputHandlerFactory
{
	InputHandler newHandler() throws IllegalAccessException, InstantiationException;
}
