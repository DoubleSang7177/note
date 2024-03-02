package datastructures.search;
/*
* 存在爆栈问题
* */
public class E07Sum {
    public static long sum(long n){
        if(n==1){
            return n;
        }
        return n+sum(n-1);
    }
}
