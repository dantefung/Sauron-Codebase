> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [www.cnblogs.com](https://www.cnblogs.com/inspred/p/9425311.html)

一，Worker Thread 模式

也叫 ThreadPool(线程池模式)

二，示例程序

情景：  
一个工作车间有多个工人处理请求，客户可以向车间添加请求。  
请求类：Request  
定义了请求的信息和处理该请求的方法  
车间类：Channel  
定义了车间里的工人，存放请求的容器。接收请求的方法，处理完请求后取出请求的方法  
客户类：ClientThread  
创建请求，并把请求交给车间  
工人类：WorkerThread  
处理请求

```
public class Request {

    private final String name;
    private final int number;
    private static final Random random = new Random();
    public Request(String name,int number){
        this.name = name;
        this.number = number;
    }

    /**
     * 处理该请求的方法
     */
    public void execute(){
        System.out.println(Thread.currentThread().getName()+" executes "+this);
        try {
            Thread.sleep(random.nextInt(1000));
        }catch (InterruptedException e){

        }
    }

    @Override
    public String toString() {
        return " [ Request from "+ name + " NO." +number+" ] ";
    }
}

```

```
public class Channel {

    private static final int MAX_REQUEST = 100 ;
    private final Request[] requestQueue;
    private int tail;
    private int head;
    private int count;

    private final WorkerThread[] threadPool;
    public Channel(int threads){
        this.requestQueue = new Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        threadPool = new WorkerThread[threads];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new WorkerThread("Worker-"+i,this);
        }
    }

    /**
     * 启动线程
     */
    public void startWorkers(){
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].start();
        }
    }

    /**
     * 接受请求
     * @param request
     */
    public synchronized void putRequest(Request request){
        while (count >= requestQueue.length){
            try{
                wait();
            }catch (InterruptedException e){

            }
        }
        requestQueue[tail] = request;
        tail = (tail + 1)%requestQueue.length;
        count++;
        notifyAll();
    }

    public synchronized Request takeRequest(){
        while (count <= 0){
            try {
                wait();
            }catch (InterruptedException e){

            }
        }
        Request request = requestQueue[head];
        head = (head + 1)%requestQueue.length;
        count--;
        notifyAll();
        return request;
    }
}

```


```
public class ClientThread extends Thread {

    private final Channel channel;
    private static final Random random = new Random();
    public ClientThread(String name,Channel channel){
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true ; i++) {
                Request request = new Request(getName(),i);
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            }
        }catch (InterruptedException e){

        }
    }
}

```


```
public class WorkerThread extends Thread{
    private final Channel channel;
    public WorkerThread(String name,Channel channel){
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true){
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}

```


```
public class Test {
    public static void main(String[] args) {

        Channel channel = new Channel(5);
        channel.startWorkers();
        new ClientThread("aaa",channel).start();
        new ClientThread("bbb",channel).start();
        new ClientThread("ccc",channel).start();

    }
}

```

三，使用场景

1, 提高吞吐量  
由于启动新线程需要花费时间，所以这个模式可以通过轮流反复的使用线程来提高吞吐量  
2, 容量控制  
可以控制工人的数量 (控制同时处理请求的线程的数量)  
3, 调用和执行的分离 (类似消息中间件)  
Client 角色负责发送工作请求，Worker 角色负责处理请求。将方法的调用和执行分离开来  
这样可以提高响应速度，调用方执行完后不必等待执行方，调用完成后可以做别的事情

  
四，通过 java.util.concurrent 包创建线程池


```
public class Request17 implements Runnable {
    private final String name;
    private final int number;
    private static final Random random = new Random();
    public Request17(String name,int number){
        this.name = name;
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" executes "+this);
        try {
            Thread.sleep(random.nextInt(1000));
        }catch (InterruptedException e){

        }
    }

    @Override
    public String toString() {
        return " [ Request from "+ name + " NO." +number+" ] ";
    }
}

```


```
public class ClientThread17 extends Thread {
    private final ExecutorService executorService;
    private static final Random random = new Random();

    public ClientThread17(String name,ExecutorService executorService){
        super(name);
        this.executorService = executorService;

    }

    @Override
    public void run() {
        try {
            for (int i = 0; true ; i++) {
                Request17 request17 = new Request17(getName(),i);
                executorService.execute(request17);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){

        }catch (RejectedExecutionException e){
            System.out.println(getName()+ " ： "+ e);
        }
    }
}

```


```
public class Test17 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            new ClientThread17("aaa",executorService).start();
            new ClientThread17("bbb",executorService).start();
            new ClientThread17("ccc",executorService).start();

            Thread.sleep(5000);
        }catch (InterruptedException e){

        }finally {
            //主线程执行大约5秒后，关闭线程池。关闭线程池后，execute方法会被拒绝执行，并抛出异常 RejectedExecutionException异常啊
            executorService.shutdown();
        }
    }
}

```