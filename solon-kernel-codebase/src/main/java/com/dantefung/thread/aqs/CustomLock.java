package com.dantefung.thread.aqs;

/**
 * 独占式同步工具类
 *
 * 在独占模式下，我们可以把state的初始值设置成0，
 * 每当某个线程要进行某项独占操作前，都需要判断state的值是不是0，
 * 如果不是0的话意味着别的线程已经进入该操作，则本线程需要阻塞等待；
 * 如果是0的话就把state的值设置成1，自己进入该操作。
 * 这个先判断再设置的过程我们可以通过CAS操作保证原子性，
 * 我们把这个过程称为尝试获取同步状态。如果一个线程获取同步状态成功了，
 * 那么在另一个线程尝试获取同步状态的时候发现state的值已经是1了就一直阻塞等待，
 * 直到获取同步状态成功的线程执行完了需要同步的操作后释放同步状态
 * ，也就是把state的值设置为0，并通知后续等待的线程。
 */
public class CustomLock {

    private static class Sync extends CustomAQS {

        @Override
        protected boolean tryAcquire(int arg) {
			boolean flag = compareAndSetState(0, 1);
			if (flag) {
				System.out.println(Thread.currentThread().getName()+"获取锁成功!");
			}
			return flag;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
			System.out.println(Thread.currentThread().getName()+"释放锁成功!");
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private Sync sync = new Sync();


    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

	public void print() {
    	sync.printSyncronizedQueue();
	}

	public static void main(String[] args) throws InterruptedException {
		CustomLock lock = new CustomLock();
		lock.lock();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Thread t1 = new Thread(()->{
			lock.lock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}, "t1");
		t1.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread t2 = new Thread(()->{
			lock.lock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}, "t2");
		t2.start();

		try {
			Thread.sleep(5000);
			lock.print();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

/*		Thread t3 =	new Thread(()->{
			lock.lock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}, "t3");
		t3.start();
*/

		lock.unlock();
	}
}