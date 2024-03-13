package mainsi.thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
/*
* 核心线程数满了
* 会判断阻塞队列是否为空，不空则加入阻塞队列
* 核心线程和阻塞队列都已满，则会创建临时线程执行任务
* 创建的临时线程数不会超过 最大线程数-核心线程数
* */

public class TestThreadPoolExecutor {

    public static class MyTask implements Runnable {
        private final String name;
        private final long duration;

        public MyTask(String name) {
            this(name,0);
        }

        public MyTask(String name, long duration) {
            this.name = name;
            this.duration = duration;
        }

        @Override
        public void run() {
            Logger.getGlobal().warning("running..."+this);
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "name='" + name+ '}';
        }
    }

    public static void main(String[] args) {
        AtomicInteger c=new AtomicInteger(1);
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                3,
                0,
                TimeUnit.MILLISECONDS,
                queue,
                r -> new Thread(r, "myThread" + c.getAndIncrement()),//线程工厂，给线程起名字
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        threadPool.submit(new MyTask("1",3600000));
        System.out.println(queue);
        threadPool.submit(new MyTask("2",3600000));
        System.out.println(queue);
        threadPool.submit(new MyTask("3"));
        System.out.println(queue);
        threadPool.submit(new MyTask("4"));
        System.out.println(queue);
        threadPool.submit(new MyTask("5",360000));
        System.out.println(queue);
        threadPool.submit(new MyTask("6",360000));
        System.out.println(queue);
//        threadPool.submit(new MyTask("6",3600000));
//        System.out.println(queue);
    }

}
