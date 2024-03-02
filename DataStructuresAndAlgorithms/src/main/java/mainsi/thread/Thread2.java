package mainsi.thread;
/*
* 实现Runnable接口
* */
public class Thread2 implements Runnable{
    @Override
    public void run() {
        System.out.println("my thread2..");
    }

    public static void main(String[] args) {
        Thread thread=new Thread(new Thread2());
        //start中是在调用run()方法
        thread.start();
    }
}
