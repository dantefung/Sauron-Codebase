package com.dantefung.nio.reactor.mainsubmutilthread.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * 从Reactor
 */
@Slf4j
public class SubReactor implements Runnable {

	private Selector selector;

	private volatile boolean stop;

	public SubReactor() {
		try {
			selector = SelectorProvider.provider().openSelector();
			stop = false;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			System.exit(1);
		}
	}

	/**
	 * 将主Reactor中的Channel注册到从Reactor中的selector
	 * @param sc
	 */
	public void register(SocketChannel sc) {
		try {
			log.info("[SubReactor] 初始化Handler, 并注册读事件 ...");
			new Handler(selector, sc);
		} catch (ClosedChannelException e) {
			log.error(e.getMessage(), e);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void run() {
		// 每来一个客户端就提交到线程池中执行.
        ThreadPool.getPool().submit(new Runnable() {
            @Override
            public void run() {
                log.info("[SubReactor]从reactor开始启动 ...");
                while (!stop) {
                    try {
						if (selector.select(1000)==0) {
							continue;
						}
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();// 多个客户端的读写事件都维护在SubReactor的Selector上.
                        Iterator<SelectionKey> it = selectedKeys.iterator();
                        SelectionKey key = null;
                        while (it.hasNext()) {
                            key = it.next();
//                            it.remove();// 这样写有并发问题. 会有多条线程修改SelectedKey集合内的数据.
                            try {
                                disptach(key);
                            } catch (Exception e) {
                                if (key != null) {
                                    key.cancel();
                                    if (key.channel() != null)
                                        key.channel().close();
                                }
                            }
                        }
                        selectedKeys.clear();
                    } catch (Throwable t) {
                        log.error(t.getMessage(), t);
                    }
                }
            }
        });

	}

	private void disptach(SelectionKey key) {
		/**
		 * 从Reactor只关心读和写事件
		 */
		if (key.isValid()) {
			Runnable handler = (Runnable)key.attachment();
			if (handler != null) {
				log.info("[SubRector] 读/写 事件派发给{}处理 ...", handler);
				handler.run();
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"@"+Integer.toHexString(hashCode());
	}
}