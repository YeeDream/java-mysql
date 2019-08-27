#### 一.常见概念

1.进程：每个进程都有独立的代码和数据空间（进程上下文），进程间的切换会有一个较大的开销，一个进程包含1-n个线程

2.线程：同一类线程共享代码和数据空间，每个线程有独立的运行栈和程序计数器()，线程切换开销小

3.线程和进程一样分为五个阶段：创建、就绪、运行、阻塞、终止

4.多线程是指操作系统能同时运行多个任务（程序）

5.多线程是指在同一程序中有多个顺序流在执行

6.并行与并发

    i.并行：多个CPU实例或者多台机器同时执行一段处理逻辑，是真正的同时
    
    ii.并发：通过CPU调度算法，让用户看上去同时执行，实际上从CPU操作层面不是真正的同时。并发往往在场景中有公用的资源，那么针对这个公用的资源往往产生瓶颈，我们会用TPS或者OPS来反应这个系统的处理能力。

#### 二.Java多线程

一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务。
    
一个线程不能独立存在，它必须是进程的一部分。一个进程一直运行，直到所有的非守护线程都结束运行后才能结束。
   
 多线程能满足程序员编写高效率的程序来达到充分利用CPU的目的。
 
 ++多线程的三种实现方法：++
 
```
//线程--->通过继承效果
public class DemoB extends Thread{
    private String name;
    public DemoB(String name){
        this.name=name;
    }
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println("我是"+name+"线程的第"+i+"次输出");
            try{
                Thread.sleep(1000);//让当前线程睡眠一秒钟
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        DemoB db1= new DemoB("A");
        DemoB db2= new DemoB("B");
        db1.start();//进入就绪操作，等待CPU调度
        db2.start();
    }
}

```




```
//多线程--->实现接口写法
public class DemoC implements Runnable{
    private String name;
    public DemoC(String name){
        this.name=name;
    }
    public void run(){
        for(int i=0;i<10l;i++){
            System.out.println("线程"+name+"执行了第"+i+"次");
        }
    }
    public static void main(String[] args){
        DemoC dc1=new DemoC("A");
        DemoC dc2=new DemoC("B");
        Thread t1=new Thread(dc1);
        Thread t2=new Thread(dc2);
        t1.start();
        t2.start();
    }
}
```


```
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class DemoE implements Callable {
    private String name;
    public DemoE(String name){
        this.name=name;
    }
    //类似于run方法，只是比run方法多了返回结果
    public Object call() throws Exception{
        for(int i=1;i<10;i++){
            System.out.println("我是"+name+"线程的第"+i+"次输出");
            Thread.sleep(1000);
        }
        return "我是"+name;
    }

    public static void main(String[] args)throws Exception {
        DemoE de1=new DemoE("A");
        DemoE de2=new DemoE("B");
        FutureTask task1=new FutureTask(de1);
        FutureTask task2=new FutureTask(de2);
        Thread t1=new Thread(task1);
        Thread t2=new Thread(task2);
        t1.start();
        t2.start();
        Object obj1=task1.get();
        Object obj2=task2.get();
        System.out.println(obj1);
        System.out.println(obj2);
    }
}
```


#### 三.生命周期

①新建状态----执行start()方法------>②就绪状态----执行run()方法----->③运行状态（~~运行状态~~⑤阻塞~~就绪状态~~）-----run()方法执行完成-------->④死亡状态

#### 四.线程的常见方法

1.==public void start()==

使该线程开始执行，Java虚拟机调用该线程的run方法

2.==public void run()==

如果该线程是使用独立的Runnable运行对象构造的，则调用该Runnable对象的run方法；否则，该方法不执行任何操作而返回。

3.==public final void setDaemon(boolean on)==

将该线程标记为守护线程或用户线程

4.==public final void join(long millisec)==

等待该线程终止的时间最长为millis毫秒

5.==public void interrupt()==

中断线程

6.==public static void yield()==

暂停当前正在执行的线程对象，并执行其它线程

7.==public static void sleep(long millisec)==

在指定的毫秒数内让当前正在执行的线程休眠（暂停执行），此操作受到系统计时器和调度程序精度和准确性的影响。

#### 五.其它概念

1.线程安全

如果你的代码在多线程下执行和在单线程下执行永远都能获得一样的结果，那么你的线程就是安全的

    i.不可变：String  Integer  Long 都是final类型的类
    ii.线程绝对安全：CopyOnWriteArrayList   CopyOnWriteArraySet
    iii.相对线程安全：Vector HashTable  会出现ConcurrentModificationException异常
    iv.线程非安全：ArrayList  LinkedList  HashMap等都是线程非安全的类

2.线程死锁

任何多线程应用程序都有死锁风险。当一组进程或线程中的每一个都在等待一个只有该组中另一个进程才能引起的事件时，我们就说这组进程或线程死锁了。

#### 六.线程池

1.概念：如果并发的线程数量很多，并且每个线程都是执行一个时间很短的任务就结束了，这样频繁创建线程就会大大降低系统的效率，因为频繁创建线程和销毁线程需要时间。那么有没有一种办法使得线程可以复用，就是执行完一个任务，并不被销毁，而是可以继续执行其他的任务？在Java中可以通过线程池来达到这样的效果。

2.Java通过Executor框架来实现线程池，刚才学到的Callable、Future接口以及将要学到ExecutorService的都属于这个框架

==*Executor*==：一个接口，其定义了一个接收Runnable对象的方法executor，其方法签名为executor(Runnable command),

==*ExecutorService*==：是一个比Executor使用更广泛的子类接口，其提供了生命周期管理的方法，以及可跟踪一个或多个异步任务执行状况返回Future的方法

==*AbstractExecutorService*==：ExecutorService执行方法的默认实现

==*ScheduledExecutorService*==：一个可定时调度任务的接口

==*ScheduledThreadPoolExecutor*==：ScheduledExecutorService的实现，一个可定时调度任务的线程池

==*ThreadPoolExecutor*==：线程池，可以通过调用Executors以下静态工厂方法来创建线程池并返回一个ExecutorService对象：

 | 
---|---
public void shutdown();关闭线程池。立即结束，并且不再添加新任务，等待原有任务结束后关闭线程池 | 
public void shutdownNow();关闭线程池。立即结束，并且不再添加新任务，不等待原有任务结束后关闭线程池 |

3.实现分类

(1)==newCachedThreadPool==:创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。这种类型的线程池特点是：
    
    a.工作线程的创建数量几乎没有限制(其实也有限制的,数目为Interger.MAX_VALUE),这样可灵活的往线程池中添加线程。
    b.如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。终止后，     如果你又提交了新的任务，则线程池重新创建一个工作线程。
    c.在使用CachedThreadPool时，一定要注意控制任务的数量，否则，由于大量线程同时运行，很有会造成系统瘫痪。


|
---|---
ExecutorService cached = Executors.newCachedThreadPool();    Cached.execute(Runnable的实现对象);|
    线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。 |

(2)==newFixedThreadPool==：创建一个定长线程池，可控制线程最大并发数，每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。FixedThreadPool是一个典型且优秀的线程池，它具有线程池提高程序效率和节省创建线程时所耗的开销的优点。但是，在线程池空闲时，即线程池中没有可运行任务时，它不会释放工作线程，还会占用一定的系统资源。


 |
---|---
ExecutorService fixed= Executors.newFixedThreadPool(5);fixed.execute(Runnable的实现对象); |
    线程池大小为5，这个参数指定了可以运行的线程的最大数目，超过这个数目的线程加进去以后，不会运行。其次，加入线程池的线程属于托管状态，线程的运行不受加入顺序的影响。 |

(3)==newSingleThreadExecutor==：创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO,优先级)执行。如果这个线程异常结束，会有另一个取代它，保证顺序执行。单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。


|
---|---
ExecutorService sin= Executors.newSingleThreadExecutor();sin.execute(Runnable的实现对象); |
不管放入多少个Runnable实现对象，执行的都是一个线程 |

(4)==newScheduleThreadPool==：创建一个定长的线程池，而且支持定时的以及周期性的任务执行，支持定时及周期性任务执行。（一般被定时器替代，不在重复说明了）

#### 七.多线程中的常见问题

###### 1.Run()和Start()之间的区别？

答：只是调用了start()方法，才会表现出多进程的特性，不同线程的run()方法里面的代码交替执行。如果只是调用run()方法，那么代码还是同步执行的，必须等待一个线程的run()方法里面的代码全部执行完毕之后，另外一个线程才可以执行其run()方法里面的代码。

###### 2.Runnable()接口和Callable()接口的区别？

答：Runnable()接口中的run()方法的返回值为void，他做的事情只是纯粹的去执行run()方法中的代码而已；Callable接口中地call()方法是有返回值的，与Future、FutureTask配合可以用来获取异步执行的结果。

###### 3.Sleep()方法和wait()方法的区别？

答：sleep()方法和wait方法都可以用来放弃CPU一定的时间，不同点在于如果线程持有某个对象的监视器（监视器对象同步），sleep方法不会放弃这个对象的监视器，wait方法会放弃这个对象的监视器，并且wait方法只能在同步中使用。

###### 4.为什么使用线程池？

答：有的时候，在我们处理一些大量的短小任务时，如果不使用线程池，则每次new Thread新建对象性能差；线程缺乏统一管理，可能无限制新建线程，相互之间竞争，及可能占用过多系统资源导致死机；缺乏更多功能，如定时执行、定期执行、线程中断。

那么当我们使用线程池时，重用存在的线程，减少对象创建、消亡的开销，性能佳；可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞；提供定时执行、定期执行、单线程、并发数控制等功能。

###### 5.shutdown()和shutdownNow()的区别？

答：shutDown()

当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态。此时，则不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。

shutdownNow()

根据JDK文档描述，大致意思是：执行该方法，线程池的状态立刻变成STOP状态，并试图停止所有正在执行的线程，不再处理还在池队列中等待的任务，当然，它会返回那些未执行的任务。它试图终止线程的方法是通过调用Thread.interrupt()方法来实现的，但是大家知道，这种方法的作用有限，如果线程中没有sleep 、wait、Condition、定时锁等应用, 
interrupt()方法是无法中断当前的线程的。所以，ShutdownNow()并不代表线程池就一定立即就能退出，它可能必须要等待所有正在执行的任务都执行完成了才能退出。
