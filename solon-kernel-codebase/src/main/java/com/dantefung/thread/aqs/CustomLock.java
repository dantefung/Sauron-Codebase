package com.dantefung.thread.aqs;

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
			Thread.sleep(2000);
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

		//lock.unlock();
	}
}