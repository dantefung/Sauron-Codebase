
package com.dantefung.io.nio.books.channels;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.SelectableChannel;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 *
 * 这种框架提供了很多灵活性。通常的做法是在选择器上调用一次 select 操作(这将更新已选择的
 *  键的集合)，然后遍历 selectKeys( )方法返回的键的集合。在按顺序进行检查每个键的过程中，相关
 *  的通道也根据键的就绪集合进行处理。然后键将从已选择的键的集合中被移除（通过在 Iterator
 *  对象上调用 remove( )方法），然后检查下一个键。完成后，通过再次调用 select( )方法重复这个循
 *  环。
 *
 * Simple echo-back server which listens for incoming stream connections
 * and echoes back whatever it reads.  A single Selector object is used to
 * listen to the server socket (to accept new connections) and all the
 * active socket channels.
 *
 * @author Ron Hitchens (ron@ronsoft.com)
 * @version $Id: SelectSockets.java,v 1.5 2002/05/20 07:24:29 ron Exp $
 */
public class SelectSockets
{
	public static int PORT_NUMBER = 1234;

	public static void main (String [] argv)
		throws Exception
	{
		new SelectSockets().go (argv);
	}

	public void go (String [] argv)
		throws Exception
	{
		int port = PORT_NUMBER;

		if (argv.length > 0) {	// override default listen port
			port = Integer.parseInt (argv [0]);
		}

		System.out.println ("Listening on port " + port);

		// allocate an unbound server socket channel
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// Get the associated ServerSocket to bind it with
		ServerSocket serverSocket = serverChannel.socket();
		// create a new Selector for use below
		Selector selector = Selector.open();

		// set the port the server channel will listen to
		serverSocket.bind (new InetSocketAddress (port));

		// set non-blocking mode for the listening socket
		serverChannel.configureBlocking (false);

		// register the ServerSocketChannel with the Selector
		serverChannel.register (selector, SelectionKey.OP_ACCEPT);

		while (true) {
			// this may block for a long time, upon return the
			// selected set contains keys of the ready channels
			int n = selector.select();

			if (n == 0) {
				continue;	// nothing to do
			}

			// get an iterator over the set of selected keys
			Iterator it = selector.selectedKeys().iterator();

			// look at each key in the selected set
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();

				// Is a new connection coming in?
				if (key.isAcceptable()) {
					ServerSocketChannel server =
						(ServerSocketChannel) key.channel();
					// 返回一个客户端通道
					SocketChannel channel = server.accept();
					// 注册客户端可读事件到多路复用器
					registerChannel (selector, channel,
						SelectionKey.OP_READ);

					sayHello (channel);
				}

				// is there data to read on this channel?
				if (key.isReadable()) {
					readDataFromSocket (key);
				}

				// remove key from selected set, it's been handled
				it.remove();
			}
		}
	}

	// ----------------------------------------------------------

	/**
	 * Register the given channel with the given selector for
	 * the given operations of interest
	 */
	protected void registerChannel (Selector selector,
		SelectableChannel channel, int ops)
		throws Exception
	{
		if (channel == null) {
			return;		// could happen
		}

		// set the new channel non-blocking
		channel.configureBlocking (false);

		// register it with the selector
		channel.register (selector, ops);
	}

	// ----------------------------------------------------------

	// Use the same byte buffer for all channels.  A single thread is
	// servicing all the channels, so no danger of concurrent acccess.
	private ByteBuffer buffer = ByteBuffer.allocateDirect (1024);

	/**
	 * Sample data handler method for a channel with data ready to read.
	 * @param key A SelectionKey object associated with a channel
	 *  determined by the selector to be ready for reading.  If the
	 *  channel returns an EOF condition, it is closed here, which
	 *  automatically invalidates the associated key.  The selector
	 *  will then de-register the channel on the next select call.
	 */
	protected void readDataFromSocket (SelectionKey key)
		throws Exception
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;

		buffer.clear();			// make buffer empty

		// loop while data available, channel is non-blocking
		while ((count = socketChannel.read (buffer)) > 0) {
			buffer.flip();		// make buffer readable

			// send the data, don't assume it goes all at once
			while (buffer.hasRemaining()) {
				//制定解码集utf-8，对读buffer解码打印
				Charset charset = Charset.forName("utf-8");
				String recMsg = String.valueOf(charset.decode(buffer).array());
				System.out.println("收到来自客户端消息:" + recMsg);
				StringJoiner stringJoiner = new StringJoiner("");
				stringJoiner.add("您好，客户端我已收到你的消息:");
				stringJoiner.add(recMsg);
				ByteBuffer respBuffer = ByteBuffer.allocate(1024);
				respBuffer.put(stringJoiner.toString().getBytes("UTF-8"));
				respBuffer.flip();
				// 回写收到客户端的消息
				socketChannel.write (respBuffer);
			}
			// WARNING: the above loop is evil.  Because
			// it's writing back to the same non-blocking
			// channel it read the data from, this code can
			// potentially spin in a busy loop.  In real life
			// you'd do something more useful than this.

			buffer.clear();		// make buffer empty
		}

		if (count < 0) {
			// close channel on EOF, invalidates the key
			socketChannel.close();
		}
	}

	// ----------------------------------------------------------

	/**
	 * Spew a greeting to the incoming client connection.
	 * @param channel The newly connected SocketChannel to say hello to.
	 */
	private void sayHello (SocketChannel channel)
		throws Exception
	{
		buffer.clear();
		buffer.put ("Hi there!\r\n".getBytes());
		buffer.flip();

		channel.write (buffer);
	}

}
