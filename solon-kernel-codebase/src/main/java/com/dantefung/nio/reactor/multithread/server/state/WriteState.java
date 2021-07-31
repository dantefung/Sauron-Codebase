package com.dantefung.nio.reactor.multithread.server.state;

import com.dantefung.nio.reactor.multithread.server.Handler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class WriteState implements HandlerState {

	public WriteState() {
	}

	@Override
	public void changeState(Handler h) {
		// TODO Auto-generated method stub
		h.setState(new ReadState());
	}

	@Override
	public void handle(Handler h, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool)
			throws IOException { // send()
		// get message from message queue
		if (sk.isValid()) {
			String str = "Your message has sent to " + sc.socket().getLocalSocketAddress().toString() + "\r\n";
			ByteBuffer buf = ByteBuffer.wrap(str.getBytes()); // wrap自動把buf的position設為0，所以不需要再flip()

			log.info("[Handler->WriteState] 准备写出内容: {}", str);
			int count = 0;
			while (buf.hasRemaining()) {
				sc.write(buf); // 回傳給client回應字符串，發送buf的position位置 到limit位置為止之間的內容
				count ++;
			}

			if (count == 0) {
				log.warn("[Warning!] A client has been closed.");
				sk.cancel();
				sc.close();
			}

			h.setState(new ReadState()); // 改變狀態(SENDING->READING)
			// 没断开连接，则再次切换到读.
			sk.interestOps(SelectionKey.OP_READ); // 通過key改變通道註冊的事件
			sk.selector().wakeup(); // 使一個阻塞住的selector操作立即返回
		}
	}

}