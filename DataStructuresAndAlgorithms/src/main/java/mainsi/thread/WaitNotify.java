package mainsi.thread;
/*
* notify()和notifyAll()的区别
* wait(Long)和sleep(Long)的线程都会等待相应毫秒值后醒来
* wait()如果不唤醒会一直等下去
* wait()和wait(Long)可以被notify()和notifyAll()唤醒
* */
public class WaitNotify {
    static final Object lock = new Object();
    static String string =" new String()";

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "...........waiting...");
                try {
                    lock.wait();//wait()如果不唤醒会一直等下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "...被唤醒了");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {//加锁
                System.out.println(Thread.currentThread().getName() + "...........waiting...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "...被唤醒了");
            }
        }, "t2");
        t1.start();
        t2.start();

        Thread.sleep(2000);
        synchronized (lock){
//            lock.notify();//随机唤醒wai线程
            lock.notifyAll();//唤醒所有wai线程
        }

    }
}
