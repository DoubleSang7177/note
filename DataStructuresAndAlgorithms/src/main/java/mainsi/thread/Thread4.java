package mainsi.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/*
* 实现Callable接口
* */
public class Thread4 implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("线程任务");
        return "返回结果";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task=new FutureTask<>(new Thread4());
        Thread thread=new Thread(task);
        thread.start();
        String result = task.get();
        System.out.println(result);
    }
}
