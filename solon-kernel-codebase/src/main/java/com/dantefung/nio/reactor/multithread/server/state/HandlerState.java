package com.dantefung.nio.reactor.multithread.server.state;

import com.dantefung.nio.reactor.multithread.server.Handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public interface HandlerState {
      
        public void changeState(Handler h);
      
        public void handle(Handler h, SelectionKey sk, SocketChannel sc,
                ThreadPoolExecutor pool) throws IOException;
    }