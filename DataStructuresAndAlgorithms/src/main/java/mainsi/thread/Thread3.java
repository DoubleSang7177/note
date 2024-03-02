package mainsi.thread;
/*
* 匿名内部类方式
* */
public class Thread3 {
    public static void main(String[] args) {
        Thread thread=new Thread(()-> System.out.println("thread 3"));
        thread.start();
    }
}
