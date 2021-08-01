package com.dantefung.nio.reactor.mainsubmutilthread.server.state;

import com.dantefung.nio.reactor.mainsubmutilthread.server.Handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class WorkState implements HandlerState {

	public WorkState() {
	}

	@Override
	public void changeState(Handler h) {
		// TODO Auto-generated method stub
		h.setState(new WriteState());
	}

	@Override
	public void handle(Handler h, SelectionKey sk, SocketChannel sc) throws IOException {
		// TODO Auto-generated method stub

	}

}