package datastructures.search;
import java.util.Arrays;

/*
* 递归求斐波那契数列的第n项
* */
public class E06Fibonacci {
    public static int fibonacci(int n){
        int[] cache=new int[n+2];//数组大小为n+1的时候n=1,2...
        Arrays.fill(cache,-1);
        cache[0]=0;
        cache[1]=1;
        return f(n,cache);

    }
    private static int f(int n,int[] cache){
        if(cache[n]!=-1){
            return cache[n];
        }
        cache[n]=f(n-1,cache)+f(n-2,cache);
        return cache[n];
    }
}
