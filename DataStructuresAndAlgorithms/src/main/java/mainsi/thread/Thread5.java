package mainsi.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/*
* 利用线程池
* */
public class Thread5 implements Runnable{
    public static ThreadLocal tl=new ThreadLocal();
    @Override
    public void run() {
        System.out.println("thread 5");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Thread5());
        tl.set("");
    }
}
