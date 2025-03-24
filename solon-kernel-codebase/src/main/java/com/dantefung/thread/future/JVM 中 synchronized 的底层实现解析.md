# **JVM 中 `synchronized` 的底层实现解析**

## **1. `synchronized` 的底层机制**
在 Java 中，`synchronized` 依赖 JVM **C++ 实现的 `ObjectMonitor`**，通过 **对象头（Mark Word）** 及 **Monitor 机制** 进行线程同步。其核心包括：
- **对象头（Mark Word）：** 记录锁状态（偏向锁、轻量级锁、重量级锁）。
- **Monitor（监视器）：** 维护线程的等待、阻塞和唤醒，底层由 C++ 代码实现。

在 `FutureData` 代码中：
```java
public synchronized String getContent() {
    while (!isReady) {
        wait();  // (1) 进入 ObjectMonitor 等待队列
    }
    return realData.getContent();
}

public synchronized void setRealData(RealData realData) {
    this.realData = realData;
    this.isReady = true;
    notifyAll();  // (2) 唤醒等待队列中的线程
}
```
- `wait()` 让线程进入 **ObjectMonitor 的 `_WaitSet`**，并释放对象锁。
- `notifyAll()` **唤醒等待的线程**，它们需要重新竞争对象锁才能继续执行。

---

## **2. `wait()` 和 `notifyAll()` 的底层实现**
### **(1) `wait()` 发生了什么？**
- **进入 `ObjectMonitor` 的 `_WaitSet`（等待队列）。**
- 释放当前对象锁（`FutureData` 实例的锁）。
- 线程进入 **等待状态（WAITING）**，不占用 CPU 资源。

JVM 底层的 **C++ 代码实现**：
```cpp
void ObjectMonitor::wait(jlong millis, bool interruptible) {
    Thread *Self = Thread::current();
    _WaitSet.insert(Self);  // (1) 线程进入等待队列
    Self->park();           // (2) 挂起线程
}
```

### **(2) `notifyAll()` 发生了什么？**
- **唤醒 `ObjectMonitor` `_WaitSet` 中的所有线程**。
- 这些线程会进入 **锁竞争队列（EntryList）**，重新争夺对象锁。

JVM 底层 **C++ 代码**：
```cpp
void ObjectMonitor::notifyAll() {
    for (Thread *t : _WaitSet) {
        t->unpark();  // (3) 唤醒线程
    }
}
```

---

## **3. `synchronized` vs. AQS**
`wait()/notifyAll()` 依赖 **JVM 内部的 `ObjectMonitor`**，而 AQS（AbstractQueuedSynchronizer）用于 **`ReentrantLock` 等锁机制**，两者区别如下：

| 特性 | `synchronized` | AQS（ReentrantLock） |
|------|---------------|------------------|
| **底层实现** | JVM 内部 `ObjectMonitor`（C++） | Java 层面 `AbstractQueuedSynchronizer` |
| **等待队列** | `_WaitSet`（等待集合） | **CLH 队列**（基于 `volatile` + CAS） |
| **阻塞方式** | `park()`（JVM 级别） | `LockSupport.park()` |
| **唤醒方式** | `notify()` / `notifyAll()` | `unpark()` 唤醒 AQS 队列中的线程 |
| **公平性** | **非公平锁**（默认） | **支持公平 / 非公平** |

**总结：**
- `synchronized` **由 JVM 层面管理，C++ 代码直接控制 Monitor。**
- `AQS` **在 Java 层面管理线程同步，底层使用 `CLH 队列 + CAS`。**
- `ReentrantLock` **基于 AQS，提供更灵活的锁机制，如可重入、公平锁等。**

---

## **4. 结论**
✔ **`synchronized` 底层依赖 `ObjectMonitor`，是 JVM 层面的实现**，而 **AQS 用于 Java 层面实现同步工具（如 `ReentrantLock`）。**  
✔ **`wait()` 让线程进入 `_WaitSet` 并释放对象锁，`notifyAll()` 负责唤醒等待线程。**  
✔ **两者最终都依赖 `park()` / `unpark()` 进行线程阻塞和唤醒，体现了 “阻塞同步” 机制。**

🚀 **如果你用 `ReentrantLock` 替代 `synchronized`，底层就变成了 AQS 机制，采用 CLH 队列 + CAS 实现同步。**