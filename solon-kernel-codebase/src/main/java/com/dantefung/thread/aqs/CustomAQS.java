/*
 * Copyright (C), 2015-2020
 * FileName: CustomAQS
 * Author:   DANTE FUNG
 * Date:     2021/6/1 16:54
 * Description: 自己实现一个AQS
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/6/1 16:54   V1.0.0
 */
package com.dantefung.thread.aqs;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @Title: CustomAQS
 * @Description: 自己实现一个AQS
 * @author DANTE FUNG
 * @date 2021/06/01 16/54
 * @since JDK1.8
 */
@Slf4j
public abstract class CustomAQS implements Serializable {

	/**
	 * 等待队列的头节点
	 * Head of the wait queue, lazily initialized.  Except for
	 * initialization, it is modified only via method setHead.  Note:
	 * If head exists, its waitStatus is guaranteed not to be
	 * CANCELLED.
	 */
	private transient volatile Node head;

	/**
	 * 等待队列的尾节点
	 * Tail of the wait queue, lazily initialized.  Modified only via
	 * method enq to add new wait node.
	 */
	private transient volatile Node tail;

	/**
	 * 同步状态
	 * The synchronization state.
	 */
	@Getter
	@Setter
	private volatile int state;

	protected CustomAQS() {

	}

	/**
	 * 独占模式: 尝试获取同步状态，由子类实现
	 * 可能简单设计为同步状态state = 1, 则获取锁成功
	 * @param arg
	 * @return
	 */
	protected boolean tryAcquire(int arg) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 独占模式: 尝试释放同步状态，由子类实现
	 * 可能简单设计为同步状态 state = 1 ---> state = 0 , 则获取释放锁成功
	 * @param arg
	 * @return
	 */
	protected boolean tryRelease(int arg) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 共享模式: 尝试获取同步状态，由子类实现
	 * @param arg
	 * @return
	 */
	protected int tryAcquireShared(int arg) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 共享模式: 尝试释放同步状态，由子类实现
	 * @param arg
	 * @return
	 */
	protected boolean tryReleaseShared(int arg) {
		throw new UnsupportedOperationException();
	}

	protected boolean isHeldExclusively() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Releases in exclusive mode.  Implemented by unblocking one or
	 * more threads if {@link #tryRelease} returns true.
	 * This method can be used to implement method {@link Lock#unlock}.
	 *
	 * @param arg the release argument.  This value is conveyed to
	 *        {@link #tryRelease} but is otherwise uninterpreted and
	 *        can represent anything you like.
	 * @return the value returned from {@link #tryRelease}
	 */
	public final boolean release(int arg) {
		//实际操作就是cas把AQS的state状态arg
		if (tryRelease(arg)) {
			Node h = head;
			if (h != null && h.waitStatus != 0)
				//核心方法，见后面详解
				unparkSuccessor(h);
			return true;
		}
		return false;
	}

	/**
	 * 子类锁，加锁时调用此方法
	 * @param arg
	 */
	public final void acquire(int arg) {
		// tryAcquire尝试获取锁(同步状态)，Semaphore、coutDownLatch等各个工具类实现不一致
		/*
			尝试获取锁成功，返回true，本方法结束
			尝试获取锁失败，返回false, !tryAcquire = true, 继续执行addWaiter、acquireQueued。即入列操作。
		 */
		if (!tryAcquire(arg) &&
				acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
			// 中断当前线程
			selfInterrupt();
	}

	/**
	 * Acquires in exclusive uninterruptible mode for thread already in
	 * queue. Used by condition wait methods as well as acquire.
	 *
	 * @param node the node
	 * @param arg the acquire argument
	 * @return {@code true} if interrupted while waiting
	 */
	final boolean acquireQueued(final Node node, int arg) {
		boolean failed = true;
		try {
			boolean interrupted = false;
			for (;;) {
				// 当前节点的上一个节点
				final Node p = node.predecessor();
				// 如果上一个节点是 head ，就尝试获取锁
				// 如果 获取成功，就将当前节点设置为 head，注意 head 节点是永远不会唤醒的。
				if (p == head && tryAcquire(arg)) {
					log.info(Thread.currentThread().getName()+"被唤醒尝试获取锁成功!");
					setHead(node);
					p.next = null; // help GC
					failed = false;
					printSyncronizedQueue();
					return interrupted;
				}
				// 获取锁失败就应该阻塞了.
				// shouldParkAfterFailedAcquire ---> 检查上一个节点的状态，如果是 SIGNAL 就阻塞，否则就改成 SIGNAL。
				if (shouldParkAfterFailedAcquire(p, node) &&
						parkAndCheckInterrupt())
					interrupted = true;
			}
		} finally {
			if (failed)
				cancelAcquire(node);
		}
	}

	/**
	 * Sets head of queue to be node, thus dequeuing. Called only by
	 * acquire methods.  Also nulls out unused fields for sake of GC
	 * and to suppress unnecessary signals and traversals.
	 *
	 * @param node the node
	 */
	private void setHead(Node node) {
		head = node;
		node.thread = null;// 当前节点的持有线程置为null
		node.prev = null;
	}
	/**
	 * Convenience method to park and then check if interrupted
	 * 阻塞并判断当前线程是否已经阻塞成功
	 * @return {@code true} if interrupted
	 */
	private final boolean parkAndCheckInterrupt() {
		System.out.println(System.currentTimeMillis() + "::" + Thread.currentThread().getName() + "即将被阻塞!");
		LockSupport.park(this);
		System.out.println(System.currentTimeMillis() + "::" + Thread.currentThread().getName() + "被唤醒!");
		return Thread.interrupted();
	}

	/**
	 * Checks and updates status for a node that failed to acquire.
	 * Returns true if thread should block. This is the main signal
	 * control in all acquire loops.  Requires that pred == node.prev.
	 *
	 * @param pred node's predecessor holding status
	 * @param node the node
	 * @return {@code true} if thread should block
	 */
	private static boolean shouldParkAfterFailedAcquire(
			Node pred, Node node) {
		int ws = pred.waitStatus;
		if (ws == Node.SIGNAL)
			/*
			 * This node has already set status asking a release
			 * to signal it, so it can safely park.
			 */
			return true;
		if (ws > 0) {
			/*
			 * Predecessor was cancelled. Skip over predecessors and
			 * indicate retry.
			 */
			do {
				node.prev = pred = pred.prev;
			} while (pred.waitStatus > 0);
			pred.next = node;
		} else {
			/*
			 * waitStatus must be 0 or PROPAGATE.  Indicate that we
			 * need a signal, but don't park yet.  Caller will need to
			 * retry to make sure it cannot acquire before parking.
			 */
			compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
		}
		return false;
	}

	/**
	 * Cancels an ongoing attempt to acquire.
	 *
	 * @param node the node
	 */
	private void cancelAcquire(Node node) {
		// Ignore if node doesn't exist
		if (node == null)
			return;

		node.thread = null;

		// Skip cancelled predecessors
		Node pred = node.prev;
		while (pred.waitStatus > 0)
			node.prev = pred = pred.prev;

		// predNext is the apparent node to unsplice. CASes below will
		// fail if not, in which case, we lost race vs another cancel
		// or signal, so no further action is necessary.
		Node predNext = pred.next;

		// Can use unconditional write instead of CAS here.
		// After this atomic step, other Nodes can skip past us.
		// Before, we are free of interference from other threads.
		node.waitStatus = Node.CANCELLED;

		// If we are the tail, remove ourselves.
		if (node == tail && compareAndSetTail(node, pred)) {
			compareAndSetNext(pred, predNext, null);
		} else {
			// If successor needs signal, try to set pred's next-link
			// so it will get one. Otherwise wake it up to propagate.
			int ws;
			if (pred != head &&
					((ws = pred.waitStatus) == Node.SIGNAL ||
							(ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
					pred.thread != null) {
				Node next = node.next;
				if (next != null && next.waitStatus <= 0)
					compareAndSetNext(pred, predNext, next);
			} else {
				unparkSuccessor(node);
			}

			node.next = node; // help GC
		}
	}

	/**
	 * Wakes up node's successor, if one exists.
	 * 释放锁核心方法
	 * @param node the node
	 */
	private void unparkSuccessor(Node node) {
		/*
		 * If status is negative (i.e., possibly needing signal) try
		 * to clear in anticipation of signalling.  It is OK if this
		 * fails or if status is changed by waiting thread.
		 */
		int ws = node.waitStatus;
		if (ws < 0)
			// 将 head 节点的 ws 改成 0，清除信号。表示，他已经释放过了。不能重复释放。
			compareAndSetWaitStatus(node, ws, 0);

		/*
		 * Thread to unpark is held in successor, which is normally
		 * just the next node.  But if cancelled or apparently null,
		 * traverse backwards from tail to find the actual
		 * non-cancelled successor.
		 */
		// 如果 next 是 null，或者 next 被取消了。就从 tail 开始向上找节点。
		Node s = node.next;
		if (s == null || s.waitStatus > 0) {
			s = null;
			// 从尾部开始，向前寻找未被取消的节点，直到这个节点是 null，或者是 head。
			// 也就是说，如果 head 的 next 是 null，那么就从尾部开始寻找，直到不是 null 为止，找到这个 head 就不管了。
			// 如果是 head 的 next 不是 null，但是被取消了，那这个节点也会被略过。
			for (Node t = tail; t != null && t != node; t = t.prev)
				if (t.waitStatus <= 0)
					s = t;
		}
		// 唤醒 head.next 这个节点。
		// 通常这个节点是 head 的 next。
		// 但如果 head.next 被取消了，就会从尾部开始找。
		if (s != null)
			LockSupport.unpark(s.thread);
	}

	/**
	 * Convenience method to interrupt current thread.
	 */
	static void selfInterrupt() {
		Thread.currentThread().interrupt();
	}

	/**
	 * Creates and enqueues node for current thread and given mode.
	 *
	 * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
	 * @return the new node
	 */
	private Node addWaiter(Node mode) {
		// 新建一个等待节点
		Node node = new Node(Thread.currentThread(), mode);
		// Try the fast path of enq; backup to full enq on failure
		// 非首次插入，可直接setTail
		// 设置老的tail为当前tail的pre节点
		Node pred = tail;
		if (pred != null) {
			node.prev = pred;
			if (compareAndSetTail(pred, node)) {
				// 设置当前节点的pre节点的next指向当前节点
				pred.next = node;
				return node;
			}
		}
		// 入列
		enq(node);
		return node;
	}

	/**
	 * Inserts node into queue, initializing if necessary. See picture above.
	 * 插入节点到队列里边，必要时进行初始化，见上图。
	 * @param node the node to insert
	 * @return node's predecessor 返回当前节点的前置节点
	 */
	private Node enq(final Node node) {
		for (;;) {
			Node t = tail;
			if (t == null) { // Must initialize
				if (compareAndSetHead(new Node()))
					tail = head;
			} else {
				// 把当前节点插入队尾
				node.prev = t;// 将老的tail设置为当前节点的prev
				if (compareAndSetTail(t, node)) {// 将当前节点设置为tail节点
					t.next = node;// 将前置节点的next指向当前节点
					return t;
				}
			}
		}
	}

	protected void printSyncronizedQueue() {
		String INDENT = "  ";
		String BOUNDARY = "|";
		String[] tplArr = {  "  ------------------"
							,"  |==>%s<==        |"
							,"  | prev: %s       "
							,"  | waitStatus: %s "
							,"  | thread: %s     "
							,"  | nextWaiter: %s "
							,"  | next: %s       "
							,"  |-------------------"};
		int colLength = 0;
		for (Node t = head; t != null; t = t.next) {
			colLength++;
		}
		String[][] printArr = new String[8][colLength];
		String tmp = "";
		for (int rowIdx = 0; rowIdx < 8; rowIdx++) {
			int colIdx = 0;
			for (Node t = head; t != null; t = t.next) {
				String tpl = tplArr[rowIdx];
				String msg = "";
				if (rowIdx > 1) {
					msg = StringUtils.contains(tpl, "%s") ?
							StringUtils.rightPad(String.format(tpl, getVal(rowIdx, t)),tmp.length(), " ").concat(BOUNDARY) :
							StringUtils.rightPad(tpl,tmp.length()," ").concat(BOUNDARY);
				} else {
					msg = StringUtils.contains(tpl, "%s") ?
							String.format(tpl, getVal(rowIdx, t)):
							tpl;
					tmp = msg;
				}
				printArr[rowIdx][colIdx] = msg;
				++colIdx;
			}
		}
		for (String[] strings : printArr) {
			for (String string : strings) {
				System.out.print(string);
			}
			System.out.println();
		}
	}

	private Object getVal(int rowIdx, Node node) {
		Object obj = null;
		switch (rowIdx) {
			case 0:
				obj = "";
				break;
			case 1:
				obj = node;
				break;
			case 2:
				obj = node.prev;
				break;
			case 3:
				obj = node.waitStatus;
				break;
			case 4:
				obj = node.thread;
				break;
			case 5:
				obj = node.nextWaiter;
				break;
			case 6:
				obj = node.next;
				break;
			default:
				obj = "";
				break;
		}
		return obj;
	}


	static final class Node {
		/** Marker to indicate a node is waiting in shared mode */
		static final Node SHARED = new Node();
		/** Marker to indicate a node is waiting in exclusive mode */
		static final Node EXCLUSIVE = null;

		/** waitStatus value to indicate thread has cancelled */
		static final int CANCELLED =  1;
		/** waitStatus value to indicate successor's thread needs unparking */
		static final int SIGNAL    = -1;
		/** waitStatus value to indicate thread is waiting on condition */
		static final int CONDITION = -2;
		/**
		 * waitStatus value to indicate the next acquireShared should
		 * unconditionally propagate
		 */
		static final int PROPAGATE = -3;

		volatile int waitStatus;

		volatile Node prev;

		volatile Node next;

		/**
		 * The thread that enqueued this node.  Initialized on
		 * construction and nulled out after use.
		 */
		volatile Thread thread;

		Node nextWaiter;

		/**
		 * Returns true if node is waiting in shared mode.
		 */
		final boolean isShared() {
			return nextWaiter == SHARED;
		}

		/**
		 * Returns previous node, or throws NullPointerException if null.
		 * Use when predecessor cannot be null.  The null check could
		 * be elided, but is present to help the VM.
		 *
		 * @return the predecessor of this node
		 */
		final Node predecessor() throws NullPointerException {
			Node p = prev;
			if (p == null)
				throw new NullPointerException();
			else
				return p;
		}

		Node() {    // Used to establish initial head or SHARED marker
		}

		Node(Thread thread, Node mode) {     // Used by addWaiter
			this.nextWaiter = mode;
			this.thread = thread;
		}

		Node(Thread thread, int waitStatus) { // Used by Condition
			this.waitStatus = waitStatus;
			this.thread = thread;
		}
	}


	@CallerSensitive
	private static sun.misc.Unsafe getUnsafe() {
		try {
			return sun.misc.Unsafe.getUnsafe();
		} catch (SecurityException tryReflectionInstead) {
		}
		try {
			return java.security.AccessController.doPrivileged(
					(PrivilegedExceptionAction<Unsafe>) () -> {
						Class<Unsafe> k = Unsafe.class;
						for (Field f : k.getDeclaredFields()) {
							f.setAccessible(true);
							Object x = f.get(null);
							if (k.isInstance(x)) return k.cast(x);
						}
						throw new NoSuchFieldError("the Unsafe");
					});
		} catch (java.security.PrivilegedActionException e) {
			throw new RuntimeException("Could not initialize intrinsics", e.getCause());
		}
	}

	/**
	 * Setup to support compareAndSet. We need to natively implement
	 * this here: For the sake of permitting future enhancements, we
	 * cannot explicitly subclass AtomicInteger, which would be
	 * efficient and useful otherwise. So, as the lesser of evils, we
	 * natively implement using hotspot intrinsics API. And while we
	 * are at it, we do the same for other CASable fields (which could
	 * otherwise be done with atomic field updaters).
	 */
	private static final Unsafe unsafe = getUnsafe();
	private static final long stateOffset;
	private static final long headOffset;
	private static final long tailOffset;
	private static final long waitStatusOffset;
	private static final long nextOffset;

	static {
		try {
			stateOffset = unsafe.objectFieldOffset
					(CustomAQS.class.getDeclaredField("state"));
			headOffset = unsafe.objectFieldOffset
					(CustomAQS.class.getDeclaredField("head"));
			tailOffset = unsafe.objectFieldOffset
					(CustomAQS.class.getDeclaredField("tail"));
			waitStatusOffset = unsafe.objectFieldOffset
					(CustomAQS.Node.class.getDeclaredField("waitStatus"));
			nextOffset = unsafe.objectFieldOffset
					(CustomAQS.Node.class.getDeclaredField("next"));

		} catch (Exception ex) { throw new Error(ex); }
	}

	/**
	 * Atomically sets synchronization state to the given updated
	 * value if the current state value equals the expected value.
	 * This operation has memory semantics of a {@code volatile} read
	 * and write.
	 *
	 * @param expect the expected value
	 * @param update the new value
	 * @return {@code true} if successful. False return indicates that the actual
	 *         value was not equal to the expected value.
	 */
	protected final boolean compareAndSetState(int expect, int update) {
		// See below for intrinsics setup to support this
		return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
	}

	/**
	 * CAS head field. Used only by enq.
	 */
	private final boolean compareAndSetHead(CustomAQS.Node update) {
		return unsafe.compareAndSwapObject(this, headOffset, null, update);
	}

	/**
	 * CAS tail field. Used only by enq.
	 */
	private final boolean compareAndSetTail(
			CustomAQS.Node expect, CustomAQS.Node update) {
		return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
	}

	/**
	 * CAS waitStatus field of a node.
	 */
	private static final boolean compareAndSetWaitStatus(CustomAQS.Node node,
			int expect,
			int update) {
		return unsafe.compareAndSwapInt(node, waitStatusOffset,
				expect, update);
	}

	/**
	 * CAS next field of a node.
	 */
	private static final boolean compareAndSetNext(CustomAQS.Node node,
			CustomAQS.Node expect,
			CustomAQS.Node update) {
		return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
	}

}
