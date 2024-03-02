## 为什么不建议是使用Excutors来创建线程池

使用Excutors来创建线程池的例子

```java
/*
* 利用线程池
* */
public class Thread5 implements Runnable{
    @Override
    public void run() {
        System.out.println("thread 5");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Thread5());
    }
}
```

### Executors.newFixedThreadPool()

创建固定大小的线程池

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```

```java
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             Executors.defaultThreadFactory(), defaultHandler);
    }
```

* corePoolSize ：线程池中核心线程数的最大值。
* maximumPoolSize ：线程池中能拥有最多线程数 。
* LinkedBlockingQueue 用于缓存任务的阻塞队列 。 此处没有设置容量大小，默认是 Integer.MAX_VALUE，可以认为是无界的。

问题分析：  
从源码中可以看出， **虽然表面上 `newFixedThreadPool()` 中定义了 核心线程数 和 最大线程数 都是固定 nThreads 个，****但是当 线程数量超过 nThreads 时，多余的线程会保存到 LinkedBlockingQueue 中，而 LinkedBlockingQueue 没是无界的，导致其无限增大，最终内存撑爆。**

### Executors.newSingleThreadExecutor()

创建单个线程池

```java
public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }
```

创建单个线程池 ，线程池中只有一个线程。

优点： 创建一个单线程的线程池，保证线程的顺序执行 ；  
缺点： 与 newFixedThreadPool() 相同。

### 总结：

newFixedThreadPool()、newSingleThreadExecutor() 底层代码 中 **LinkedBlockingQueue 没有设置容量大小，默认是 Integer.MAX_VALUE**， 可以认为是无界的。线程池中多余的线程会被缓存到 LinkedBlockingQueue中，最终内存撑爆。

## 线程池状态

### running

表示线程池正常运行，既能接受新任务，也能正常处理队列中的任务

### shutdown

当调用线程池的shutdown()方法时，线程池会进入shutdown状态，表示线程正处于关闭状态，此状态下线程不会接受新任务，但是会继续把队列中的任务处理完。

### stop

当调用线程池的shutdownnow()方法时，线程池会进入stop状态，表示线程正处于停止状态，此状态下线程不会接受新任务，也不会处理队列中的任务，并且正在运行的线程也会被中断。

### tidying

线程池中没有线程在运行后，线程池的状态就会变为tidying,并且会调用terminated()方法，该方法是空方法，留给程序员进行扩展。

### terminated

terminated()方法执行完成后，线程池就会进入terminated状态。

## synchronized和ReentrantLock有哪些不同

### synchronized

Java中的一个关键字，自动加锁与自动释放锁，JVM层面的锁，非公平锁，锁的是对象，所信息保存在对象头中，底层有锁升级过程

### ReentrantLock

JDK提供的一个类，需要手动加锁与释放锁，API层面的锁，公平锁或非公平锁，int类型的state标识来标识锁的状态，没有锁升级过程。

公平锁和非公平锁

无论是公平锁还是非公平锁，他们底层实现都会使用AQS来进行排队，他们的区别在于线程在使用lock()方法加锁时：

1. 如果是公平锁，会先检查AQS队列是否存在线程在排队，如果有线程在排队，则当前线程也会进行排队。

2. 如果是非公平锁，则不会去检查是否有线程在排队，而是直接竞争锁。

无论是公平锁还是非公平锁，一旦被竞争到，都会进行排队，当释放锁时，都会唤醒排在最前面的线程，所以非公平锁只是体现在了线程加锁阶段，而没有体现在线程被唤醒阶段，ReentrantLock是可重入锁，无论是公平锁还是非公平锁，都是可重入的。

## ThreadLocal有哪些应用场景，它底层是如何实现的?

1. ThreadLocal是Java提供的线程本地存储机制，可以利用该机制将数据存储在某个线程内部，该线程可以在任意时刻，任意方法中获取缓存的数据。

2. ThreadLocal的底层是通过ThreadLocalMap来实现的，每个线程Thread对象都有一个ThreadLocalMap,Map的key为ThreadLocal对象，value为需要缓存的值

3. 在线程池中使用ThreadLocal会造成内存泄露，因为在使用完ThreadLocal对象后，需要把设置的key,value，也就是Entry对象进行回收，但是线程池中的线程不会被进行回收，而线程对象是通过强引用指向ThreadLocalMap,ThreadLocalMap也是通过强引用指向Entry对象，线程不会被回收，Entry对象也不会被回收，从而出现内存泄露，解决办法是在使用了ThreadLocal对象后，手动调用ThreadLocal的remove()方法，手动清除Entry对象。

## Tomcat中为什么要使用自定义类加载器

Tomcat可以部署多个应用，而每个应用中存在很多类，而且每个应用中的类是独立的，全类名可以是相同的，比如一个订单系统可能存在com.hema.User类，一个库存系统可能也存在com.hema.User类，一个Tomcat,不管内部部署了多少个应用，Tomcat启动之后就是一个Java进程，也就是一个JVM，所以如果Tomcat中只存在一个类加载器，比如默认的AppClassLoader,那么就只能加载一个com.hema.User类，这是有问题的，而在Tomcat中，会为部署的每一个应用都生成一个类加载器实例，名字叫做WebAppClassLoader,这样Tomcat中的每一个应用就可以使用自己的类加载器去加载自己的类，从而达到了应用之间的类隔离，不出现冲突。另外Tomcat还利用自定义加载器实现了热加载功能。

## JDK JRE JVM

1. JDK（java se development kit)：java标准开发包，它提供了编译和运行java程序的各种工具和资源，包括java编译器，java运行时环境，以及常用的Java类库等

2. JRE (java runtime environment)，Java运行环境，用于运行java的字节码文件，jre中包含了jvm及jvm工作需要的类库，普通用户只需要安装jre来运行java程序，而程序开发者需要安装jdk来编译和调试程序。

3. JVM(JAVA Virtual Mechine)：java虚拟机，是jre的一部分，它是整个java实现跨平台最核心的额部分，负责运行字节码文件，也就是*.class文件。

### hashCode()和equals()方法的联系

在java中，每个对象可以调用自己的hashCode()方法来得到自己的哈希值(hashCode),相当于对象的指纹，通常来说世界上没有两个完全相同的指纹，但是在java中做不到这么绝对，但是我们人仍然可以用hashCode来做一些判断，比如：

1. 如果两个对象的hashCode不同，那么这两个对象肯定是不同的对象；

2. 如果两个对象的hashCode相同，代表可能是同一个对象也有可能不是同一个；

3. 如果两个对象相同那么hashCode肯定就相同。

在java的一些集合类的实现中，会比较两个对象是否相同，会根据上面的原则，会先调用hashCode()方法得到hashCode，如果hashCode不相同那么肯定是不同的对象，可以直接认为两个对象不相同，如果hashCode相同，会进一步调用equals()方法进行比较，就是用来最终确定两个对象是否相同的。通常，equals()方法会实现比较重，逻辑比较多，而hashCode()主要就是得到一个哈希值实际上就是一个数字，相对而言比较轻，所以在比较两个对象时，通常会根据hashCode比较一下。

所以，如果重写了equals()方法，就要重写hashCode()方法，一定要保证遵守以上规则。

## String,StringBuffer,StringBuilder的区别

1. String是常量，不可变的，如果尝试去修改，会新生成一个字符串对象，StringBuffer,StringBuilder是可变的。

2. StringBuffer是线程安全的，StringBuilder是线程不安全的，所以单线程环境下StringBuilder效率会更高。

## 泛型中的extends和super的区别

1. < ？extends T>表示T在内的任何T的子类
2. < ? super T>表示T在内的任何T的父类

## 重载和重写的区别

重载：发生在同一个类中，方法名必须相同，参数类型不同，个数不同，<mark>顺序不同</mark>，方法返回值和访问修饰符可以不同，只有返回值不同其他相同时，在编译时会报错（说明重载和返回值没有关系）。

重写：发生在父子类中，方法名，参数列表必须相同，返回值范围小于等于父类，抛出的异常范围小于等于父类，访问修饰符范围大于等于父类；如果父类方法的访问修饰符类private，则子类不能重写该方法。

## List和Set的区别

1. list 有序，按对象进入的顺序保存对象，可重复，允许有多个Null元素对象，可以使用Iterator取出所有元素，在逐一遍历，还可以使用get(int index)获取指定下标的元素。

2. set无序不可重复，最多允许有一个null对象，取元素时只能使用
   Iterator接口取得所有元素，在逐一遍历各个元素

## ArrayList和LinkedList区别

1. 底层的数据结构不同，ArrayList底层是基于数组实现的，LinkedList底层是基于链表实现的

2. 由于底层数据结构不同，他们使用的场景也不同，ArrayList适合随机查找，LinkedList更适合添加和删除，增删查的时间复杂度不同

3. 另外LinkedList和ArrayList都实现了List接口，但是LinkedList还额外实现了Deque接口，所以LinkedList还可以当队列来使用。

## Jdk1.7到Jdk1.8 HashMap发生了什么变化



## Spring的两大特性

### IOC 控制反转

1. 控制：控制对象的创建，控制对象内属性的赋值。

2. 反转：表示对象控制权的转移。

> 把对象的创建，初始化，销毁交给spring来管理，而不是由开发者控制，就是实现控制反转。IOC思想源于IOC容器完成，IOC容器的底层就是对象工厂(<mark>BeanFactory</mark>接口)。IOC的原理是基于<mark>xml解析，工厂设计模式，反射</mark>实现的。使用IOC可以降低代码的耦合度。

3. spring提供的IOC容器实现的两种方式

> 1. BeanFactory: IOC容器的基本实现，spring内部的使用接口，不提供开发人员使用。在加载配置文件时不会创建对象，在获取对象（使用）时才会创建对象。
> 
> 2. ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用。在加载配置文件时就会<mark>把在配置文件对象进行创建</mark>（这话啥意思？）

4. IOC操作bean管理的2中形式

> 1. 基于xml方式的bean管理，spring提供了<bean><property>等标签
> 
> 2. 基于注解的bean管理，spring主要提供了@Component@Service等注解

### AOP 面向切面编程

1. 概念
   
   > 可以利用aop对业务逻辑的各个部分进行隔离，从而使得业务逻辑的各个部分之间的耦合度降低，提高程序的可重用性，同时提高了开发效率。通俗来说就是不通过修改源代码方式，在主干功能里面添加新功能。

2. 底层原理：使用动态代理来实现
   
   > 1. 有接口的情况，使用jdk动态代理。即创建接口实现类代理对象，增强类的方法。
   > 
   > 2. 没有接口的情况，使用CGLIB动态代理。即创建子类的代理对象，增强类的方法。

3. AOP的专业术语
   
   > 1. 连接点：类里面可以被增强的方法。
   > 
   > 2. 切入点：类里面实际被真正增强的方法。
   > 
   > 3. 通知(增强)：(1).实际增强的逻辑部分成为通知(增强)
   >    
   >    (2).类型：前置通知，后置通知，环绕通知，异常通知，最终通知
   > 
   > 4. 切面：把通知应用到切入点的过程，成为切面。

## 单例Bean和单例模式

单例模式表示jvm中某个类的对象只能存在唯一一个

而单例Bean并不表示JVM中只能存在唯一的某一个类的Bean对象

## Spring中的事务是如何实现的

1.  Spring中的事务底层是基于数据库事务和AOP机制的

2. 首先对于使用了@Transactional注解的bean，Spring会创建一个代理对象作为Bean

3. 当调用代理对象的方法时，会先判断方法上是否加@Transactional注解

4. 如果加了，那么利用事务管理器创建一个数据库连接

5. 并且修改了数据库连接的autocommit属性为false,禁止此连接点的自动提交，这是实现spring事务的重要一步

6. 然后执行当前方法，方法会执行sql

7. 执行完方法后，如果没有出现异常，就直接提交事务

8. 如果出现异常，并且整个异常是需要回滚事务的就会回滚事务，否则仍然提交事务

9. spring事务的隔离级别就是数据库的隔离级别

10. spring事务的传播机制是spring事务自己实现的，也是spring事务中最复杂的

11. spring事务的传播机制是基于数据库连接来做的，一个数据库连接一个事务，如果传播机制配置为需要新开一个事务，那么实际上就是先建立一个数据库连接，在此新数据库连接上执行sql


