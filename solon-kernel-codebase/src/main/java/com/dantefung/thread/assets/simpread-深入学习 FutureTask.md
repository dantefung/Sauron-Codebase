> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [beautyboss.farbox.com](https://beautyboss.farbox.com/post/study/shen-ru-xue-xi-futuretask)

> 欲说还休，却道天凉好个秋。

### 第一部分:`What`

在`Java`中一般通过继承`Thread`类或者实现`Runnable`接口这两种方式来创建多线程，但是这两种方式都有个缺陷，就是不能在执行完成后获取执行的结果，因此`Java 1.5`之后提供了`Callable`和`Future`接口，通过它们就可以在任务执行完毕之后得到任务的执行结果。本文会简要的介绍使用方法，然后会从源代码角度分析下具体的实现原理。  
本文以`Java 1.7`的代码进行分析。

### 第二部分:`How`

> `Callable`接口

对于需要执行的任务需要实现`Callable`接口，`Callable`接口定义如下:

```
public interface Callable<V> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    V call() throws Exception;
}
```

可以看到`Callable`是个泛型接口，泛型`V`就是要`call()`方法返回的类型。`Callable`接口和`Runnable`接口很像，都可以被另外一个线程执行，但是正如前面所说的，`Runnable`不会返回数据也不能抛出异常。

> `Future`接口

`Future`接口代表异步计算的结果，通过`Future`接口提供的方法可以查看异步计算是否执行完成，或者等待执行结果并获取执行结果，同时还可以取消执行。`Future`接口的定义如下:

```
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

*   `cancel()`:`cancel()`方法用来取消异步任务的执行。如果异步任务已经完成或者已经被取消，或者由于某些原因不能取消，则会返回`false`。如果任务还没有被执行，则会返回`true`并且异步任务不会被执行。如果任务已经开始执行了但是还没有执行完成，若`mayInterruptIfRunning`为`true`，则会立即中断执行任务的线程并返回`true`，若`mayInterruptIfRunning`为`false`，则会返回`true`且不会中断任务执行线程。
*   `isCanceled()`: 判断任务是否被取消，如果任务在结束 (正常执行结束或者执行异常结束) 前被取消则返回`true`，否则返回`false`。
*   `isDone()`: 判断任务是否已经完成，如果完成则返回`true`，否则返回`false`。**_需要注意的是：任务执行过程中发生异常、任务被取消也属于任务已完成，也会返回`true`_**。
*   `get()`: 获取任务执行结果，如果任务还没完成则会阻塞等待直到任务执行完成。如果任务被取消则会抛出`CancellationException`异常，如果任务执行过程发生异常则会抛出`ExecutionException`异常，如果阻塞等待过程中被中断则会抛出`InterruptedException`异常。
*   `get(long timeout,Timeunit unit)`: 带超时时间的`get()`版本，如果阻塞等待过程中超时则会抛出`TimeoutException`异常。

> `FutureTask`

`Future`只是一个接口，不能直接用来创建对象，`FutureTask`是`Future`的实现类，  
`FutureTask`的继承图如下:  
![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAACbklEQVRoQ+2aMU4dMRCGZw6RC1CSSyQdLZJtKQ2REgoiRIpQkCYClCYpkgIESQFIpIlkW+IIcIC0gUNwiEFGz+hlmbG9b1nesvGW++zxfP7H4/H6IYzkwZFwQAUZmpJVkSeniFJKA8ASIi7MyfkrRPxjrT1JjZ8MLaXUDiJuzwngn2GJaNd7vyP5IoIYY94Q0fEQIKIPRGS8947zSQTRWh8CwLuBgZx479+2BTkHgBdDAgGAC+fcywoyIFWqInWN9BSONbTmFVp/AeA5o+rjKRJ2XwBYRsRXM4ZXgAg2LAPzOCDTJYQx5pSIVlrC3EI45y611osMTHuQUPUiYpiVooerg7TWRwDAlhSM0TuI+BsD0x4kGCuFSRVzSqkfiLiWmY17EALMbCAlMCmI6IwxZo+INgQYEYKBuW5da00PKikjhNNiiPGm01rrbwDwofGehQjjNcv1SZgddALhlJEgwgJFxDNr7acmjFLqCyJuTd6LEGFttpmkYC91Hrk3s1GZFERMmUT01Xv/sQljjPlMRMsxO6WULwnb2D8FEs4j680wScjO5f3vzrlNJszESWq2LYXJgTzjZm56MCHf3zVBxH1r7ftU1splxxKYHEgoUUpTo+grEf303rPH5hxENJqDKQEJtko2q9zGeeycWy3JhpKhWT8+NM/sufIhBwKI+Mta+7pkfxKMtd8Qtdbcx4dUQZcFCQ2I6DcAnLUpf6YMPxhIDDOuxC4C6djoQUE6+tKpewWZ1wlRkq0qUhXptKTlzv93aI3jWmE0Fz2TeujpX73F9TaKy9CeMk8vZusfBnqZ1g5GqyIdJq+XrqNR5AahKr9CCcxGSwAAAABJRU5ErkJggg==)  
可以看到,`FutureTask`实现了`RunnableFuture`接口，则`RunnableFuture`接口继承了`Runnable`接口和`Future`接口，所以`FutureTask`既能当做一个`Runnable`直接被`Thread`执行，也能作为`Future`用来得到`Callable`的计算结果。

> 使用

`FutureTask`一般配合`ExecutorService`来使用，也可以直接通过`Thread`来使用。

```
package com.beautyboss.slogen.callback;

import java.util.concurrent.*;

/**
 * Author  : Slogen
 * AddTime : 17/6/4
 * Email   : huangjian13@meituan.com
 */
public class CallDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * 第一种方式:Future + ExecutorService
         * Task task = new Task();
         * ExecutorService service = Executors.newCachedThreadPool();
         * Future<Integer> future = service.submit(task1);
         * service.shutdown();
         */


        /**
         * 第二种方式: FutureTask + ExecutorService
         * ExecutorService executor = Executors.newCachedThreadPool();
         * Task task = new Task();
         * FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
         * executor.submit(futureTask);
         * executor.shutdown();
         */

        /**
         * 第三种方式:FutureTask + Thread
         */

        // 2. 新建FutureTask,需要一个实现了Callable接口的类的实例作为构造函数参数
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task());
        // 3. 新建Thread对象并启动
        Thread thread = new Thread(futureTask);
        thread.setName("Task thread");
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

        // 4. 调用isDone()判断任务是否结束
        if(!futureTask.isDone()) {
            System.out.println("Task is not done");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int result = 0;
        try {
            // 5. 调用get()方法获取任务结果,如果任务没有执行完成则阻塞等待
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("result is " + result);

    }

    // 1. 继承Callable接口,实现call()方法,泛型参数为要返回的类型
    static class Task  implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
            int result = 0;
            for(int i = 0; i < 100;++i) {
                result += i;
            }

            Thread.sleep(3000);
            return result;
        }
    }
}
```

### 第三部分:`Why`

> 构造函数

先从`FutureTask`的构造函数看起，`FutureTask`有两个构造函数，其中一个如下：

```
public FutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;       // ensure visibility of callable
}
```

这个构造函数会把传入的`Callable`变量保存在`this.callable`字段中，该字段定义为`private Callable<V> callable;`用来保存底层的调用，在被执行完成以后会指向`null`, 接着会初始化`state`字段为`NEW`。`state`字段用来保存`FutureTask`内部的任务执行状态，一共有 7 中状态，每种状态及其对应的值如下：

```
private volatile int state;
private static final int NEW          = 0;
private static final int COMPLETING   = 1;
private static final int NORMAL       = 2;
private static final int EXCEPTIONAL  = 3;
private static final int CANCELLED    = 4;
private static final int INTERRUPTING = 5;
private static final int INTERRUPTED  = 6;
```

其中需要注意的是`state`是`volatile`类型的，也就是说只要有任何一个线程修改了这个变量，那么其他所有的线程都会知道最新的值。  
为了后面更好的分析`FutureTask`的实现，这里有必要解释下各个状态。

*   `NEW`: 表示是个新的任务或者还没被执行完的任务。这是初始状态。
*   `COMPLETING`: 任务已经执行完成或者执行任务的时候发生异常，但是任务执行结果或者异常原因还没有保存到`outcome`字段 (`outcome`字段用来保存任务执行结果，如果发生异常，则用来保存异常原因) 的时候，状态会从`NEW`变更到`COMPLETING`。但是这个状态会时间会比较短，属于中间状态。
*   `NORMAL`: 任务已经执行完成并且任务执行结果已经保存到`outcome`字段，状态会从`COMPLETING`转换到`NORMAL`。这是一个最终态。
*   `EXCEPTIONAL`: 任务执行发生异常并且异常原因已经保存到`outcome`字段中后，状态会从`COMPLETING`转换到`EXCEPTIONAL`。这是一个最终态。
*   `CANCELLED`: 任务还没开始执行或者已经开始执行但是还没有执行完成的时候，用户调用了`cancel(false)`方法取消任务且不中断任务执行线程，这个时候状态会从`NEW`转化为`CANCELLED`状态。这是一个最终态。
*   `INTERRUPTING`: 任务还没开始执行或者已经执行但是还没有执行完成的时候，用户调用了`cancel(true)`方法取消任务并且**_要中断任务执行线程_**但是还没有中断任务执行线程之前，状态会从`NEW`转化为`INTERRUPTING`。这是一个中间状态。
*   `INTERRUPTED`: 调用`interrupt()`中断任务执行线程之后状态会从`INTERRUPTING`转换到`INTERRUPTED`。这是一个最终态。

有一点需要注意的是，所有值大于`COMPLETING`的状态都表示任务已经执行完成 (任务正常执行完成，任务执行异常或者任务被取消)。

各个状态之间的可能转换关系如下图所示:  
![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAACbklEQVRoQ+2aMU4dMRCGZw6RC1CSSyQdLZJtKQ2REgoiRIpQkCYClCYpkgIESQFIpIlkW+IIcIC0gUNwiEFGz+hlmbG9b1nesvGW++zxfP7H4/H6IYzkwZFwQAUZmpJVkSeniFJKA8ASIi7MyfkrRPxjrT1JjZ8MLaXUDiJuzwngn2GJaNd7vyP5IoIYY94Q0fEQIKIPRGS8947zSQTRWh8CwLuBgZx479+2BTkHgBdDAgGAC+fcywoyIFWqInWN9BSONbTmFVp/AeA5o+rjKRJ2XwBYRsRXM4ZXgAg2LAPzOCDTJYQx5pSIVlrC3EI45y611osMTHuQUPUiYpiVooerg7TWRwDAlhSM0TuI+BsD0x4kGCuFSRVzSqkfiLiWmY17EALMbCAlMCmI6IwxZo+INgQYEYKBuW5da00PKikjhNNiiPGm01rrbwDwofGehQjjNcv1SZgddALhlJEgwgJFxDNr7acmjFLqCyJuTd6LEGFttpmkYC91Hrk3s1GZFERMmUT01Xv/sQljjPlMRMsxO6WULwnb2D8FEs4j680wScjO5f3vzrlNJszESWq2LYXJgTzjZm56MCHf3zVBxH1r7ftU1splxxKYHEgoUUpTo+grEf303rPH5hxENJqDKQEJtko2q9zGeeycWy3JhpKhWT8+NM/sufIhBwKI+Mta+7pkfxKMtd8Qtdbcx4dUQZcFCQ2I6DcAnLUpf6YMPxhIDDOuxC4C6djoQUE6+tKpewWZ1wlRkq0qUhXptKTlzv93aI3jWmE0Fz2TeujpX73F9TaKy9CeMk8vZusfBnqZ1g5GqyIdJq+XrqNR5AahKr9CCcxGSwAAAABJRU5ErkJggg==)  
另外一个构造函数如下，

```
public FutureTask(Runnable runnable, V result) {
        this.callable = Executors.callable(runnable, result);
        this.state = NEW;       // ensure visibility of callable
}
```

这个构造函数会把传入的`Runnable`封装成一个`Callable`对象保存在`callable`字段中，同时如果任务执行成功的话就会返回传入的`result`。这种情况下如果不需要返回值的话可以传入一个`null`。  
顺带看下`Executors.callable()`这个方法，这个方法的功能是把`Runnable`转换成`Callable`，代码如下:

```
public static <T> Callable<T> callable(Runnable task, T result) {
    if (task == null)
        throw new NullPointerException();
    return new RunnableAdapter<T>(task, result);
}
```

可以看到这里采用的是适配器模式，调用`RunnableAdapter<T>(task, result)`方法来适配，实现如下:

```
static final class RunnableAdapter<T> implements Callable<T> {
    final Runnable task;
    final T result;
    RunnableAdapter(Runnable task, T result) {
        this.task = task;
        this.result = result;
    }
    public T call() {
        task.run();
        return result;
    }
}
```

这个适配器很简单，就是简单的实现了`Callable`接口，在`call()`实现中调用`Runnable.run()`方法，然后把传入的`result`作为任务的结果返回。  
在`new`了一个`FutureTask`对象之后，接下来就是在另一个线程中执行这个`Task`, 无论是通过直接`new`一个`Thread`还是通过线程池，执行的都是`run()`方法，接下来就看看`run()`方法的实现。

> `run()`

`run()`方法实现如下:

```
public void run() {
    // 1. 状态如果不是NEW，说明任务或者已经执行过，或者已经被取消，直接返回
    // 2. 状态如果是NEW，则尝试把当前执行线程保存在runner字段中
    // 如果赋值失败则直接返回
    if (state != NEW ||
        !UNSAFE.compareAndSwapObject(this, runnerOffset,
                                     null, Thread.currentThread()))
        return;
    try {
        Callable<V> c = callable;
        if (c != null && state == NEW) {
            V result;
            boolean ran;
            try {
                // 3. 执行任务
                result = c.call();
                ran = true;
            } catch (Throwable ex) {
                result = null;
                ran = false;
                // 4. 任务异常
                setException(ex);
            }
            if (ran)
                // 4. 任务正常执行完毕
                set(result);
        }
    } finally {
        // runner must be non-null until state is settled to
        // prevent concurrent calls to run()
        runner = null;
        // state must be re-read after nulling runner to prevent
        // leaked interrupts
        int s = state;
        // 5. 如果任务被中断，执行中断处理
        if (s >= INTERRUPTING)
            handlePossibleCancellationInterrupt(s);
    }
}
```

`run()`方法首先会

1.  判断当前任务的`state`是否等于`NEW`, 如果不为`NEW`则说明任务或者已经执行过，或者已经被取消，直接返回。
2.  如果状态为`NEW`则接着会通过`unsafe`类把任务执行线程引用`CAS`的保存在`runner`字段中，如果保存失败，则直接返回。
3.  执行任务。
4.  如果任务执行发生异常，则调用`setException()`方法保存异常信息。`setException()`方法如下：
    
    ```
    protected void setException(Throwable t) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = t;
            UNSAFE.putOrderedInt(this, stateOffset, EXCEPTIONAL); // final state
            finishCompletion();
        }
    }
    ```
    
    在`setException()`方法中
    
    1.  首先会`CAS`的把当前的状态从`NEW`变更为`COMPLETING`状态。
    2.  把异常原因保存在`outcome`字段中，`outcome`字段用来保存任务执行结果或者异常原因。
    3.  `CAS`的把当前任务状态从`COMPLETING`变更为`EXCEPTIONAL`。这个状态转换对应着上图中的二。
    4.  调用`finishCompletion()`。关于这个方法后面在分析。
5.  如果任务成功执行则调用`set()`方法设置执行结果，该方法实现如下:
    
    ```
    protected void set(V v) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = v;
            UNSAFE.putOrderedInt(this, stateOffset, NORMAL); // final state
            finishCompletion();
        }
    }
    ```
    
    这个方法跟上面分析的`setException()`差不多，
    
    1.  首先会`CAS`的把当前的状态从`NEW`变更为`COMPLETING`状态。
    2.  把任务执行结果保存在`outcome`字段中。
    3.  `CAS`的把当前任务状态从`COMPLETING`变更为`NORMAL`。这个状态转换对应着上图中的一。
    4.  调用`finishCompletion()`。

发起任务线程跟执行任务线程通常情况下都不会是同一个线程，在任务执行线程执行任务的时候，任务发起线程可以查看任务执行状态、获取任务执行结果、取消任务等等操作，接下来分析下这些操作。

> get()

任务发起线程可以调用`get()`方法来获取任务执行结果，如果此时任务已经执行完毕则会直接返回任务结果，如果任务还没执行完毕，则调用方会阻塞直到任务执行结束返回结果为止。`get()`方法实现如下:

```
public V get() throws InterruptedException, ExecutionException {
    int s = state;
    if (s <= COMPLETING)
        s = awaitDone(false, 0L);
    return report(s);
}
```

`get()`方法实现比较简单，会

1.  判断任务当前的`state <= COMPLETING`是否成立。前面分析过，`COMPLETING`状态是任务是否执行完成的临界状态。
2.  如果成立，表明任务还没有结束 (这里的结束包括任务正常执行完毕，任务执行异常，任务被取消)，则会调用`awaitDone()`进行阻塞等待。
3.  如果不成立表明任务已经结束，调用`report()`返回结果。

> `awaitDone()`

当调用`get()`获取任务结果但是任务还没执行完成的时候，调用线程会调用`awaitDone()`方法进行阻塞等待，该方法定义如下:

```
private int awaitDone(boolean timed, long nanos)
        throws InterruptedException {
    // 计算等待截止时间
    final long deadline = timed ? System.nanoTime() + nanos : 0L;
    WaitNode q = null;
    boolean queued = false;
    for (;;) {
        // 1. 判断阻塞线程是否被中断,如果被中断则在等待队
        // 列中删除该节点并抛出InterruptedException异常
        if (Thread.interrupted()) {
            removeWaiter(q);
            throw new InterruptedException();
        }

        // 2. 获取当前状态，如果状态大于COMPLETING
        // 说明任务已经结束(要么正常结束，要么异常结束，要么被取消)
        // 则把thread显示置空，并返回结果
        int s = state;
        if (s > COMPLETING) {
            if (q != null)
                q.thread = null;
            return s;
        }
        // 3. 如果状态处于中间状态COMPLETING
        // 表示任务已经结束但是任务执行线程还没来得及给outcome赋值
        // 这个时候让出执行权让其他线程优先执行
        else if (s == COMPLETING) // cannot time out yet
            Thread.yield();
        // 4. 如果等待节点为空，则构造一个等待节点
        else if (q == null)
            q = new WaitNode();
        // 5. 如果还没有入队列，则把当前节点加入waiters首节点并替换原来waiters
        else if (!queued)
            queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
                    q.next = waiters, q);
        else if (timed) {
            // 如果需要等待特定时间，则先计算要等待的时间
            // 如果已经超时，则删除对应节点并返回对应的状态
            nanos = deadline - System.nanoTime();
            if (nanos <= 0L) {
                removeWaiter(q);
                return state;
            }
            // 6. 阻塞等待特定时间
            LockSupport.parkNanos(this, nanos);
        }
        else
            // 6. 阻塞等待直到被其他线程唤醒
            LockSupport.park(this);
    }
}
```

`awaitDone()`中有个死循环，每一次循环都会

1.  判断调用`get()`的线程是否被其他线程中断，如果是的话则在等待队列中删除对应节点然后抛出`InterruptedException`异常。
2.  获取任务当前状态，如果当前任务状态大于`COMPLETING`则表示任务执行完成，则把`thread`字段置`null`并返回结果。
3.  如果任务处于`COMPLETING`状态，则表示任务已经处理完成 (正常执行完成或者执行出现异常)，但是执行结果或者异常原因还没有保存到`outcome`字段中。这个时候调用线程让出执行权让其他线程优先执行。
4.  如果等待节点为空，则构造一个等待节点`WaitNode`。
5.  如果第四步中新建的节点还没如队列，则`CAS`的把该节点加入`waiters`队列的首节点。
6.  阻塞等待。

假设当前`state=NEW`且`waiters`为`NULL`, 也就是说还没有任何一个线程调用`get()`获取执行结果，这个时候有两个线程`threadA`和`threadB`先后调用`get()`来获取执行结果。再假设这两个线程在加入阻塞队列进行阻塞等待之前任务都没有执行完成且`threadA`和`threadB`都没有被中断的情况下 (因为如果`threadA`和`threadB`在进行阻塞等待结果之前任务就执行完成或线程本身被中断的话，`awaitDone()`就执行结束返回了)，执行过程是这样的，以`threadA`为例:

1.  第一轮`for`循环，执行的逻辑是`q == null`, 所以这时候会新建一个节点`q`。第一轮循环结束。
2.  第二轮`for`循环，执行的逻辑是`!queue`，这个时候会把第一轮循环中生成的节点的`netx`指针指向`waiters`，然后`CAS`的把节点`q`替换`waiters`。也就是把新生成的节点添加到`waiters`链表的首节点。如果替换成功，`queued=true`。第二轮循环结束。
3.  第三轮`for`循环，进行阻塞等待。要么阻塞特定时间，要么一直阻塞知道被其他线程唤醒。

在`threadA`和`threadB`都阻塞等待之后的`waiters`结果如图

![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAACbklEQVRoQ+2aMU4dMRCGZw6RC1CSSyQdLZJtKQ2REgoiRIpQkCYClCYpkgIESQFIpIlkW+IIcIC0gUNwiEFGz+hlmbG9b1nesvGW++zxfP7H4/H6IYzkwZFwQAUZmpJVkSeniFJKA8ASIi7MyfkrRPxjrT1JjZ8MLaXUDiJuzwngn2GJaNd7vyP5IoIYY94Q0fEQIKIPRGS8947zSQTRWh8CwLuBgZx479+2BTkHgBdDAgGAC+fcywoyIFWqInWN9BSONbTmFVp/AeA5o+rjKRJ2XwBYRsRXM4ZXgAg2LAPzOCDTJYQx5pSIVlrC3EI45y611osMTHuQUPUiYpiVooerg7TWRwDAlhSM0TuI+BsD0x4kGCuFSRVzSqkfiLiWmY17EALMbCAlMCmI6IwxZo+INgQYEYKBuW5da00PKikjhNNiiPGm01rrbwDwofGehQjjNcv1SZgddALhlJEgwgJFxDNr7acmjFLqCyJuTd6LEGFttpmkYC91Hrk3s1GZFERMmUT01Xv/sQljjPlMRMsxO6WULwnb2D8FEs4j680wScjO5f3vzrlNJszESWq2LYXJgTzjZm56MCHf3zVBxH1r7ftU1splxxKYHEgoUUpTo+grEf303rPH5hxENJqDKQEJtko2q9zGeeycWy3JhpKhWT8+NM/sufIhBwKI+Mta+7pkfxKMtd8Qtdbcx4dUQZcFCQ2I6DcAnLUpf6YMPxhIDDOuxC4C6djoQUE6+tKpewWZ1wlRkq0qUhXptKTlzv93aI3jWmE0Fz2TeujpX73F9TaKy9CeMk8vZusfBnqZ1g5GqyIdJq+XrqNR5AahKr9CCcxGSwAAAABJRU5ErkJggg==)

> `cancel(boolean)`

用户可以调用`cancel(boolean)`方法取消任务的执行，`cancel()`实现如下:

```
public boolean cancel(boolean mayInterruptIfRunning) {
    // 1. 如果任务已经结束，则直接返回false
    if (state != NEW)
        return false;
    // 2. 如果需要中断任务执行线程
    if (mayInterruptIfRunning) {
        // 2.1. 把任务状态从NEW转化到INTERRUPTING
        if (!UNSAFE.compareAndSwapInt(this, stateOffset, NEW, INTERRUPTING))
            return false;
        Thread t = runner;
        // 2.2. 中断任务执行线程
        if (t != null)
            t.interrupt();
        // 2.3. 修改状态为INTERRUPTED
        UNSAFE.putOrderedInt(this, stateOffset, INTERRUPTED); // final state
    }
    // 3. 如果不需要中断任务执行线程，则直接把状态从NEW转化为CANCELLED
    else if (!UNSAFE.compareAndSwapInt(this, stateOffset, NEW, CANCELLED))
        return false;
    // 4. 
    finishCompletion();
    return true;
}
```

`cancel()`方法会做下面几件事:

1.  判断任务当前执行状态，如果任务状态不为`NEW`，则说明任务或者已经执行完成，或者执行异常，不能被取消，直接返回`false`表示执行失败。
2.  判断需要中断任务执行线程，则
    
    1.  把任务状态从`NEW`转化到`INTERRUPTING`。这是个中间状态。
    2.  中断任务执行线程。
    3.  修改任务状态为`INTERRUPTED`。这个转换过程对应上图中的四。
3.  如果不需要中断任务执行线程，直接把任务状态从`NEW`转化为`CANCELLED`。如果转化失败则返回`false`表示取消失败。这个转换过程对应上图中的四。
    
4.  调用`finishCompletion()`。
    

> `finishCompletion()`

根据前面的分析，不管是任务执行异常还是任务正常执行完毕，或者取消任务，最后都会调用`finishCompletion()`方法，该方法实现如下:

```
private void finishCompletion() {
    // assert state > COMPLETING;
    for (WaitNode q; (q = waiters) != null;) {
        if (UNSAFE.compareAndSwapObject(this, waitersOffset, q, null)) {
            for (;;) {
                Thread t = q.thread;
                if (t != null) {
                    q.thread = null;
                    LockSupport.unpark(t);
                }
                WaitNode next = q.next;
                if (next == null)
                    break;
                q.next = null; // unlink to help gc
                q = next;
            }
            break;
        }
    }

    done();

    callable = null;        // to reduce footprint
}
```

这个方法的实现比较简单，依次遍历`waiters`链表，唤醒节点中的线程，然后把`callable`置空。  
被唤醒的线程会各自从`awaitDone()`方法中的`LockSupport.park*()`阻塞中返回，然后会进行新一轮的循环。在新一轮的循环中会返回执行结果 (或者更确切的说是返回任务的状态)。

> `report()`

`report()`方法用在`get()`方法中，作用是把不同的任务状态映射成任务执行结果。实现如下：

```
private V report(int s) throws ExecutionException {
    Object x = outcome;
    // 1. 任务正常执行完成，返回任务执行结果
    if (s == NORMAL)
        return (V)x;
    // 2. 任务被取消，抛出CancellationException异常
    if (s >= CANCELLED)
        throw new CancellationException();
    // 3. 其他状态，抛出执行异常ExecutionException
    throw new ExecutionException((Throwable)x);
}
```

映射关系如下图所示:  
![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAACbklEQVRoQ+2aMU4dMRCGZw6RC1CSSyQdLZJtKQ2REgoiRIpQkCYClCYpkgIESQFIpIlkW+IIcIC0gUNwiEFGz+hlmbG9b1nesvGW++zxfP7H4/H6IYzkwZFwQAUZmpJVkSeniFJKA8ASIi7MyfkrRPxjrT1JjZ8MLaXUDiJuzwngn2GJaNd7vyP5IoIYY94Q0fEQIKIPRGS8947zSQTRWh8CwLuBgZx479+2BTkHgBdDAgGAC+fcywoyIFWqInWN9BSONbTmFVp/AeA5o+rjKRJ2XwBYRsRXM4ZXgAg2LAPzOCDTJYQx5pSIVlrC3EI45y611osMTHuQUPUiYpiVooerg7TWRwDAlhSM0TuI+BsD0x4kGCuFSRVzSqkfiLiWmY17EALMbCAlMCmI6IwxZo+INgQYEYKBuW5da00PKikjhNNiiPGm01rrbwDwofGehQjjNcv1SZgddALhlJEgwgJFxDNr7acmjFLqCyJuTd6LEGFttpmkYC91Hrk3s1GZFERMmUT01Xv/sQljjPlMRMsxO6WULwnb2D8FEs4j680wScjO5f3vzrlNJszESWq2LYXJgTzjZm56MCHf3zVBxH1r7ftU1splxxKYHEgoUUpTo+grEf303rPH5hxENJqDKQEJtko2q9zGeeycWy3JhpKhWT8+NM/sufIhBwKI+Mta+7pkfxKMtd8Qtdbcx4dUQZcFCQ2I6DcAnLUpf6YMPxhIDDOuxC4C6djoQUE6+tKpewWZ1wlRkq0qUhXptKTlzv93aI3jWmE0Fz2TeujpX73F9TaKy9CeMk8vZusfBnqZ1g5GqyIdJq+XrqNR5AahKr9CCcxGSwAAAABJRU5ErkJggg==)  
如果任务处于`NEW`、`COMPLETING`和`INTERRUPTING`这三种状态的时候是执行不到`report()`方法的，所以没必要对这三种状态进行转换。

> `get(long,TimeUnit)`

带超时等待的获取任务结果，实现如下:

```
public V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
    if (unit == null)
        throw new NullPointerException();
    int s = state;
    if (s <= COMPLETING &&
        // 如果awaitDone()超时返回之后任务还没结束，则抛出异常
        (s = awaitDone(true, unit.toNanos(timeout))) <= COMPLETING)
        throw new TimeoutException();
    return report(s);
}
```

跟`get()`不同点在于`get(long,TimeUnit)`会在`awaitDone()`超时返回之后抛出`TimeoutException`异常。

> `isCancelled()`和`isDone()`

这两个方法分别用来判断任务是否被取消和任务是否执行完成，实现都比较简单，代码如下:

```
public boolean isCancelled() {
    return state >= CANCELLED;
}
```

```
public boolean isDone() {
    return state != NEW;
}
```

根据前面的分析，这两个方法就很容易理解不用多做解释了，O(∩_∩)O。

总结下，其实`FutureTask`的实现还是比较简单的，当用户实现`Callable()`接口定义好任务之后，把任务交给其他线程进行执行。`FutureTask`内部维护一个任务状态，任何操作都是围绕着这个状态进行，并随时更新任务状态。任务发起者调用`get*()`获取执行结果的时候，如果任务还没有执行完毕，则会把自己放入阻塞队列中然后进行阻塞等待。当任务执行完成之后，任务执行线程会依次唤醒阻塞等待的线程。调用`cancel()`取消任务的时候也只是简单的修改任务状态，如果需要中断任务执行线程的话则调用`Thread.interrupt()`中断任务执行线程。

### 第四部分:`Other`

有个值得关注的问题就是当任务还在执行的时候用户调用`cancel(true)`方法能否真正让任务停止执行呢？  
在前面的分析中我们直到，当调用`cancel(true)`方法的时候，实际执行还是`Thread.interrupt()`方法，而`interrupt()`方法只是设置中断标志位，如果被中断的线程处于`sleep()`、`wait()`或者`join()`逻辑中则会抛出`InterruptedException`异常。  
因此结论是:`cancel(true)`**_并不一定能够停止正在执行的异步任务_**。