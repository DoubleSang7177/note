package mainsi.thread;
/*
* 底层都是继承Runnable接口
* 继承Thread类
* */
public class Thread1 extends Thread {
    public static void main(String[] args) {

        Thread1 myThread=new Thread1();
        myThread.start();
        System.out.println("---");
    }


    @Override
    public void run() {
        System.out.println("my thread....");
    }
}
