package com.dantefung.nio.reactor.mainsubmutilthread.server.state;

import com.dantefung.nio.reactor.mainsubmutilthread.server.Handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public interface HandlerState {

	void changeState(Handler h);

	void handle(Handler h, SelectionKey sk, SocketChannel sc) throws IOException;
}