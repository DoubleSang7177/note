

## 打破双亲委派机制

```java
public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
   }


protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);//判断是否被当前的类加载器加载过
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);//实现向上委派
                    } else {//说明是扩展类加载器
                        c = findBootstrapClassOrNull(name);//让启动类加载器去加载 "Bootstrap"
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {//说明父类加载器都没有加载成功，应该由当前类加载器来加载
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);//该方法为抽象方法，由子类实现

                    // this is the defining class loader; record the stats
                    PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    PerfCounter.getFindClasses().increment();
                }
            }
            // 连接
            if (resolve) {
                resolveClass(c);
            }

         return c;
        }
    }

```

### loadClass()方法的resolve默认传递false，所以此方法的调用不会进行B类的连接和初始化阶段！

```java
/*
        * loadClass()方法的resolve默认传递false，所以此方法的调用不会进行
        * B类的连接和初始化阶段
        * */
        Class<?> clazz = classLoader.loadClass("com.itheima.my.B");
        System.out.println(clazz.getClassLoader());
```

![bf510cd2-1a72-4688-bbba-f7bb48d63abd](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/bf510cd2-1a72-4688-bbba-f7bb48d63abd.png)

Class.forName()才会真正执行连接和初始化

```java
/*
* Class.forName()才会真正执行连接和初始化
* */
        Class.forName("com.itheima.my.B");
```

![ad2d33cd-8d1e-40ee-85a8-d56e4a152ee2](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/ad2d33cd-8d1e-40ee-85a8-d56e4a152ee2.png)

## 垃圾回收算法

### 1. 标记-清除算法 Mark Sweep GC

步骤

1. 标记阶段：将所有存活的对象进行标记。Java中通过可达性分析算法，从GC Root开始通过引用链遍历出所有引用对象

2. 清除阶段：从内存中删除没有被标记的对象

优缺点

1. 优点：实现简单。

2. 缺点：(1). （内存）碎片化问题。(2). 分配速度慢。由于内存碎片的存在，需要维护一个空闲链表，极有可能发生每次需要遍历到链表的最后才能获得合适的内存空间

### 2. 复制算法 Copying GC

步骤

1. 准备两块空间From空间和To空间，在对象分配阶段，只能使用其中一块空间（From空间）

2. 在垃圾回收GC阶段，将From空间中的存活对象复制到To空间

3. 将两块空间的From和To名字互换

优缺点

1. 优点：吞吐量高；不会发生碎片化

2. 缺点：内存使用效率低

### 3. 标记-整理算法 Mark Compact GC

步骤

1. 标记阶段，将所有存活的对象进行标记。Java中使用可达性分析算法，从GC Root开始通过引用链遍历出所有存活对象

2. 整理阶段，将内存中的存活对象移动到堆的一端，清理掉剩余的堆内存空间。

优缺点

1. 优点：内存使用效率高；不会发生碎片化

2. 缺点：整理阶段的效率不高

### 4. 分代GC算法 Generational GC

步骤

1. 将整个内存区域分为年轻代(存放存活时间比较短的对象)和老年代(存放存活时间比较长的对象)，年轻代分为伊甸园区(Eden区)和两个幸存者区

2. 整理阶段，将内存中的存活对象移动到堆的一端，清理掉剩余的堆内存空间。

优缺点

1. 优点：内存使用效率高；不会发生碎片化

2. 缺点：整理阶段的效率不高

## 垃圾回收器

垃圾回收器是垃圾回收算法的具体实现，垃圾回收器分为年轻代和老年代，除了G1之外的垃圾回收器都需要组合使用。

![91c24bc2-f066-40c9-94e5-8e78f9144774](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/91c24bc2-f066-40c9-94e5-8e78f9144774.png)

1. 年轻代-Serial垃圾回收器
   ![09b6d683-15c3-40e4-af79-0bf9a134a544](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/09b6d683-15c3-40e4-af79-0bf9a134a544.png)

2. 老年代-SerialOld垃圾回收器
   ![d17597d4-4adf-4f04-9726-0d31c678c564](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/d17597d4-4adf-4f04-9726-0d31c678c564.png)

3. 年轻代-ParNew垃圾回收器
   ![814d9c84-0c4d-4296-86e4-16cb04c27533](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/814d9c84-0c4d-4296-86e4-16cb04c27533.png)

4. 老年代- CMS(Concurrent Mark Sweep)垃圾回收器
   ![402fab34-f4ac-4de0-88ed-7b8f3b6d4f56](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/402fab34-f4ac-4de0-88ed-7b8f3b6d4f56.png)

5. 年轻代-Parallel Scavenge垃圾回收器
   ![72e8afaf-6977-4fa8-a44f-607f15538598](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/72e8afaf-6977-4fa8-a44f-607f15538598.png)

6. 老年代-Parallel Old垃圾回收器
   ![88efcacd-1d59-4741-b905-4569162b1ce3](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/88efcacd-1d59-4741-b905-4569162b1ce3.png)

7. G1垃圾回收器
   ![8d74a0fb-ef05-4d55-b9b7-f5942d9e0c6c](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/8d74a0fb-ef05-4d55-b9b7-f5942d9e0c6c.png)
   ![be6ad143-3a4d-49cb-8bc8-1ccd0386d07a](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/be6ad143-3a4d-49cb-8bc8-1ccd0386d07a.png)
   ![e8d3897b-a2da-4288-bc50-0e7fa42c6538](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/e8d3897b-a2da-4288-bc50-0e7fa42c6538.png)
   ![40a52ce2-ebd8-434e-a248-ff0a6c54ce3c](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/40a52ce2-ebd8-434e-a248-ff0a6c54ce3c.png)

## Arthas

### 启动arthas

首先打开arthas-boot.jar存在的目录，在cmd中打开，运行以下代码

```properties
java -jar arthas-boot.jar
```

![4c7effb3-3b55-42dc-bc46-b7c055615023](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/4c7effb3-3b55-42dc-bc46-b7c055615023.png)

![c57bab45-be66-4aea-8167-d8589c73fd39](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/c57bab45-be66-4aea-8167-d8589c73fd39.png)

### 常见命令

查看垃圾回收器

```properties
dashboard -n 1
```

查看当前堆内存的使用情况: 例如每1秒钟查看一次

```java
dashboaed -i 1000
```

![472fd903-4889-42d7-b70b-f521fcb428b2](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/472fd903-4889-42d7-b70b-f521fcb428b2.png)

![bd336491-f378-4087-89e5-4dc61df70ae4](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/bd336491-f378-4087-89e5-4dc61df70ae4.png)

heap行为年轻代，使用g1垃圾回收器

## 虚拟机配置

//-XX:+PrintFlagsFinal  ：启动时打印出配置项的最终值

-XX:GCTimeRatio = 19  ：吞吐量

-XX:MaxGCPauseMillis=10 : 最大暂停时间

## 内存泄漏和内存溢出

1. 内存泄露：在Java中如果不再使用一个对象，但是该对象依然在GC ROOT的引用链上，
   这个对象就不会被垃圾回收器回收，这种情况就称之为内存泄漏。

内存泄露的常见场景

设置虚拟机的最大堆内存大小和初始堆内存大小为1g

![d9118ecb-3b82-4f50-b422-0b39ed687d8f](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/d9118ecb-3b82-4f50-b422-0b39ed687d8f.png)

```java
/*
    * 定义一个map集合，每次登录放入3m的数据
    * */
    private static Map<Long, Object> userCache = new HashMap<>();

/**
     * 登录接口 传递id,放入hashmap中
     */
    @PostMapping("/login")
    public void login(Long id){
        userCache.put(id,new byte[1024 * 1024 * 300]);
    }

    /*
    * 退出登录
    * */
    @GetMapping("/logout")
    public void logout(Long id){
        userCache.remove(id);
    }

```

![51cb2d90-a37b-415f-b9c7-a8650bca4ac6](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/51cb2d90-a37b-415f-b9c7-a8650bca4ac6.png)

连续登录四个用户后，报出堆内存溢出的异常。

![e1cfbc2b-ed0d-4fcd-91d8-6327d03bf6a7](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/e1cfbc2b-ed0d-4fcd-91d8-6327d03bf6a7.png)

### 代码中的内存泄露

#### <mark>equals()和hashCode()导致的内存泄漏</mark>

在定义新类时没有重写正确的equals()和hashCode()方法。在使用HashMap的场景下，

如果使用这个类对象作为key，HashMap在判断key是否已经存在时会使用这些方法，如

果重写方式不正确，会导致相同的数据被保存多份。

```java
public class Demo2 {
    public static long count = 0;
    public static Map<Student,Long> map = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        while (true){
            if(count++ % 100 == 0){
                Thread.sleep(10);
            }
//            Thread.sleep(10); //休眠一会儿不然visualVM压根检测不到
            Student student = new Student();
            student.setId(1);
            student.setName("张三");
            System.out.println(count);
            map.put(student,1L);
        }
    }
}
```

HashMap在进行put()操作时会根据key计算出hash值，在计算hash值时会调用key的hashCode()方法，如果不重写则会调用父类的hashCode()方法，可能会计算出错，接着会根据计算出的hash值定位到数组中的某个位置，如果该位置存在元素，会根据key的equals()方法计算key是否一致，如果key一致则直接替换value,如果key不一致，则走链表或者红黑树查找逻辑，其中也会使用equals查看是否相同。

#### <mark>内部类引用外部类</mark>

⚫ 1、非静态的内部类默认会持有外部类，尽管代码上不再使用外部类，所以如果有地方引

用了这个非静态内部类，会导致外部类也被引用，垃圾回收时无法回收这个外部类。

⚫ 2、匿名内部类对象如果在非静态方法中被创建，会持有调用者对象，垃圾回收时无法回

收调用者。

```java
public class Outer {
    private byte[] bytes = new byte[1024*1024]; //外部类持有数据
    private  String name = "测试";

    class Inner {
        private String name;

        public Inner() {
            this.name = Outer.this.name;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.in.read();
        int count = 0;
        ArrayList<Inner> inners = new ArrayList<>();
        while (true) {
            if (count++ % 100 == 0) {
                Thread.sleep(10); //为了VisualVM连接到程序睡眠10毫秒
            }
            System.out.println(++count);
            inners.add(new Outer().new Inner());
        }
    }
}
```

![dc143c8c-36bc-4505-8279-c17898457085](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/dc143c8c-36bc-4505-8279-c17898457085.png)

![70ae7d79-2d62-42c6-9f8f-a494ab0dd2ce](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/70ae7d79-2d62-42c6-9f8f-a494ab0dd2ce.png)

![bcc9ea4a-fb2f-4ce9-a646-d33ba55329ab](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/bcc9ea4a-fb2f-4ce9-a646-d33ba55329ab.png)

出现问题的原因是，每次创建一个内部类Iner对象是，都会创建一个外部类Outer对象，这些外部类对象不会使用，但是一直在GC Root链上，所以会出现内存泄露问题。

修改后的代码

```java
public class Outer {
    private byte[] bytes = new byte[1024*1024]; //外部类持有数据
    private static String name = "测试";

    static class Inner {
        private String name;

        public Inner() {
            this.name = Outer.name;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.in.read();
        int count = 0;
        ArrayList<Inner> inners = new ArrayList<>();
        while (true) {
            if (count++ % 100 == 0) {
                Thread.sleep(10); //为了VisualVM连接到程序睡眠10毫秒
            }
            System.out.println(++count);
            inners.add(new Inner());
        }
    }
}

```

![fe113294-3a6e-436c-8665-ce93319b9742](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/fe113294-3a6e-436c-8665-ce93319b9742.png)

内部类如果不是static修饰，在创建内部类时会关联到外部类

解决方案：

1、这个案例中，使用内部类的原因是可以直接获取到外部类中的成员变量值，简化开发。如果不想持有外部类

对象，应该使用静态内部类。

2、使用静态方法，可以避免匿名内部类持有调用者对象。

#### <mark> 方法中创建匿名内部类对象</mark>

```java
public class Outer {
    private byte[] bytes = new byte[1024];
    public List<String> newList() {
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
        }};
        return list;
    }

    public static void main(String[] args) throws IOException {
        System.in.read();
        int count = 0;
        ArrayList<Object> objects = new ArrayList<>();
        while (true){
            System.out.println(++count);
            objects.add(new Outer().newList());
        }
    }
}
```

![1608e1a2-1a50-4b93-afe8-b285e300e563](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/1608e1a2-1a50-4b93-afe8-b285e300e563.png)

![275e8a50-ffcc-4fc1-a75b-5180e5f2516e](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/275e8a50-ffcc-4fc1-a75b-5180e5f2516e.png)

![b07b3544-e694-4b0e-bdbd-affff64ab0c6](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/b07b3544-e694-4b0e-bdbd-affff64ab0c6.png)

反编译内部类的字节码文件，可知内部类的构造方法中带有外部调用者

![ff7cb0e1-770e-4b47-9ab5-049104daa100](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/ff7cb0e1-770e-4b47-9ab5-049104daa100.png)

修改

```java
public class Outer {
    private byte[] bytes = new byte[1024];
    public static List<String> newList() {
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
        }};
        return list;
    }

    public static void main(String[] args) throws IOException {
        System.in.read();
        int count = 0;
        ArrayList<Object> objects = new ArrayList<>();
        while (true){
            System.out.println(++count);
            objects.add(newList());
        }
    }
}
```

反编译内部类的字节码文件

![188733f8-d1da-4a84-822d-502d7dd39072](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/188733f8-d1da-4a84-822d-502d7dd39072.png)

使用静态方法，可以避免匿名内部类持有调用者对象。

#### <mark>ThreadLocal的使用</mark>

如果仅仅使用手动创建的线程，就算没有调用ThreadLocal的remove方法清理数据，也不会

产生内存泄漏。因为当线程被回收时，ThreadLocal也同样被回收。但是如果使用线程池就

不一定了。

该类中没有使用remove方法，但是线程会被回收，ThreadLocal也同样被回收。

```java
public class Demo5_1 {
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            new Thread(() -> {
                threadLocal.set(new byte[1024 * 1024 * 10]);
            }).start();
            Thread.sleep(10);
        }


    }
}
```

![209b11fe-2e17-4d2d-a7e3-4e8ee2530777](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/209b11fe-2e17-4d2d-a7e3-4e8ee2530777.png)

![8d381f36-1648-4d02-9ef2-f49de6c37bdc](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/8d381f36-1648-4d02-9ef2-f49de6c37bdc.png)

利用线程池创建线程，需要remove在 ThreadLoca中的方法

```java
public class Demo5 {
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        //核心线程数和最大线程数都是最大的，并且线程池中线程的存活时间设置为0，线程会长期驻留，不会被回收
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Integer.MAX_VALUE, Integer.MAX_VALUE,
                0, TimeUnit.DAYS, new SynchronousQueue<>());//SynchronousQueue<>()保证队列中不会存放任何数据（这是什么意思捏？）
        int count = 0;
        while (true) {
            System.out.println(++count);
            threadPoolExecutor.execute(() -> {
                threadLocal.set(new byte[1024 * 1024]);
               // threadLocal.remove();
            });
            Thread.sleep(10);
        }


    }
}
```

![30ab8267-95e2-4c52-9e2a-436ce59c128b](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/30ab8267-95e2-4c52-9e2a-436ce59c128b.png)

![e5b5644a-287b-4cea-a6cc-5648e17ef750](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/e5b5644a-287b-4cea-a6cc-5648e17ef750.png)

![54e21515-f1d6-4a5e-a36f-3d32824b449d](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/54e21515-f1d6-4a5e-a36f-3d32824b449d.png)

```java
public class Demo5 {
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        //核心线程数和最大线程数都是最大的，并且线程池中线程的存活时间设置为0，线程会长期驻留，不会被回收
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Integer.MAX_VALUE, Integer.MAX_VALUE,
                0, TimeUnit.DAYS, new SynchronousQueue<>());//SynchronousQueue<>()保证队列中不会存放任何数据（这是什么意思捏？）
        int count = 0;
        while (true) {
            System.out.println(++count);
            threadPoolExecutor.execute(() -> {
                threadLocal.set(new byte[1024 * 1024]);
               // threadLocal.remove();
            });
            Thread.sleep(10);
        }


    }
}
```



![64741b54-7c33-45b7-b1cb-2b3cddaebe09](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/64741b54-7c33-45b7-b1cb-2b3cddaebe09.png)

![110f5cca-3352-45e3-a6df-f8c8784d5bc4](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/110f5cca-3352-45e3-a6df-f8c8784d5bc4.png)

解决方案：

线程方法执行完，一定要调用ThreadLocal中的remove方法清理对象。

#### <mark> String的intern方法</mark>

问题：

JDK6中字符串常量池位于堆内存中的<u>Perm Gen永久代</u>中，如果不同字符串的intern方法被大量调用，字符串常量池会不停的变大超过永久代内存上限之后就会产生内存溢出问题。

解决方案：

1、注意代码中的逻辑，尽量不要将随机生成的字符串加入字符串常量池

2、增大永久代空间的大小，根据实际的测试/估算结果进行设置-XX:MaxPermSize=256M

```java
public class Demo6 {
    public static void main(String[] args) {
        while (true){
            List<String> list = new ArrayList<String>();

            int i = 0;
            while (true) {
                //String.valueOf(i++).intern(); //JDK1.6 perm gen 不会溢出
                list.add(String.valueOf(i++).intern()); //溢出
                System.out.println(i);
            }
        }
    }
}
```

![93e10354-7620-4e4a-95d1-dbe7c924185e](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/93e10354-7620-4e4a-95d1-dbe7c924185e.png)

#### 通过静态字段保存对象

问题：

如果大量的数据在静态变量中被长期引用，数据就不会被释放，如果这些数据不再使用，就成为了内存泄漏。

解决方案：

1、尽量减少将对象长时间的保存在静态变量中，如果不再使用，必须将对象删除（比如在集合中）或者将静态变量设置为null。

2、使用单例模式时，尽量使用懒加载，而不是立即加载。

3、Spring的Bean中不要长期存放大对象，如果是缓存用于提升性能，尽量设置过期时间定期失效。

```java
@Lazy //懒加载
@Component
public class TestLazy {
    private byte[] bytes = new byte[1024 * 1024 * 1024];
}
```

设置成懒加载，在没有创建类的对象的情况下，spring boot项目能够正常启动，如果不加懒加载的注解，会堆内存溢出的异常

> org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'testLazy' defined in file [C:\LearningNote\JVM\2、实战篇代码\day01\day05\day05\jvm-optimize\target\classes\com\itheima\jvmoptimize\leakdemo\demo7\TestLazy.class]: Instantiation of bean failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.itheima.jvmoptimize.leakdemo.demo7.TestLazy]: Constructor threw exception; nested exception is java.lang.OutOfMemoryError: Java heap space

设置成懒加载，创建对象依然会报错

![33ee7c33-3bb6-4b35-90f8-c4b2bb383393](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/33ee7c33-3bb6-4b35-90f8-c4b2bb383393.png)

![12eeb589-c26a-4000-8e19-34e1e1b47934](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/12eeb589-c26a-4000-8e19-34e1e1b47934.png)

#### 缓存案例

设置最大推内存为100M

```java
/*
* 缓存案例
* 使用Caffeine框架，时一种比较出色的java缓存框架
* */
public class CaffineDemo {
    public static void main(String[] args) throws InterruptedException {
        Cache<Object, Object> build = Caffeine.newBuilder()//创建一个缓存对象
                 .expireAfterWrite(Duration.ofMillis(100))//设置过期时间，expireAfterWrite即写入之后多长时间过期
                .build();
        int count = 0;
        while (true){
            build.put(count++,new byte[1024 * 1024 * 10]);//往缓存中放入数据
            Thread.sleep(100L);
        }
    }
}}
    }
}
```

![b98af9fc-4767-4b6e-8ef1-73b79f172ce9](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/b98af9fc-4767-4b6e-8ef1-73b79f172ce9.png)

#### <mark>资源没有正常关闭</mark>

问题：

连接和流这些资源会占用内存，如果使用完之后没有关闭，这部分内存不一定会出现内存泄漏，但是会导致close方法不被执行。

解决方案：

1、为了防止出现这类的资源对象泄漏问题，必须在finally块中关闭不再使用的资源。

2、从 Java 7 开始，使用try-with-resources语法可以用于自动关闭资源。

![870a6d0f-ebad-461c-ba3d-5d46c482dda6](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/870a6d0f-ebad-461c-ba3d-5d46c482dda6.png)

### 并发请求问题

```java
/**
     * 大量数据 + 处理慢+并发请求 ---》导致内存泄露
     */
    @GetMapping("/test")
    public void test1() throws InterruptedException {
        byte[] bytes = new byte[1024 * 1024 * 100];//100m
        Thread.sleep(10 * 1000L);//睡眠10秒
    }
```

![0b442543-1c9d-48d5-911b-9d009dc79540](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/0b442543-1c9d-48d5-911b-9d009dc79540.png)

将数据放入静态变量中，导致内存泄露

```java
/**
     * 登录接口 传递id和name,放入hashmap中
     */
    @PostMapping("/login2")
    public void login2(Long id,String name){

        userCache.put(id,new UserEntity(id,name));
    }
```

![d2111a2e-6b0e-49ee-8f40-dc7f84da1e00](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/d2111a2e-6b0e-49ee-8f40-dc7f84da1e00.png)

![7fc3af6a-9b5d-4aab-93dc-ea506fe43cde](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/7fc3af6a-9b5d-4aab-93dc-ea506fe43cde.png)





## VisualVM

在jdk8的bin目录中有，可以直接使用，也可以在idea中安装VisualVM插件，配置后使用

![867d07ac-1360-4dbf-8876-b8d41321d4c0](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/867d07ac-1360-4dbf-8876-b8d41321d4c0.png)

 远程调用设置

```java
java -jar -Djava.rmi.server.hostname=192.168.52.145 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9122 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false jvm-service.jar
```

不建议连接到生产环境上的服务器上，可以连接到测试环境进行监控

## arthas-tunnel-server

导入jar包

![8b521e10-be3d-4500-8d4a-00dcc67a8de4](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/8b521e10-be3d-4500-8d4a-00dcc67a8de4.png)

运行

```java
nohup java -jar -Darthas.enable-pages=true arthas-tunnel-server-3.7.1-fatjar.jar &
```

![09d9ca9a-64fc-4271-b7d0-3509fe10eac8](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/09d9ca9a-64fc-4271-b7d0-3509fe10eac8.png)

![da033f8a-2e8e-47eb-9040-df3d266b3092](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/da033f8a-2e8e-47eb-9040-df3d266b3092.png)

运行spring-boot项目

![6ef5f213-7d09-4fe5-abba-448ddc62cc3c](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/6ef5f213-7d09-4fe5-abba-448ddc62cc3c.png)

![99aac66d-e43f-40d0-b290-825d36e2e899](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/99aac66d-e43f-40d0-b290-825d36e2e899.png)

在上面的浏览器端应该能看到，但是我启动spring-boot项目报错了
