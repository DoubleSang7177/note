package mainsi.thread;
/*
* 匿名内部类方式
* 可以使用线程的join()方法保证线程的顺序执行
* */
public class Thread3 {
    public static void main(String[] args) {
        Thread t1 =new Thread(()-> System.out.println("t1......."));
        Thread t2=new Thread(()-> {
            try {
                t1.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("t2........");
        });
        t2.start();
        t1.start();
    }
}
