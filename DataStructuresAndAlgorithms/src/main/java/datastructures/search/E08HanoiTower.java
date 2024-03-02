package datastructures.search;
import java.util.LinkedList;
/*
* 多路递归-汉诺塔
* */
public class E08HanoiTower {
    static LinkedList<Integer> a=new LinkedList<>();
    static LinkedList<Integer> b=new LinkedList<>();
    static LinkedList<Integer> c=new LinkedList<>();
    public static void hanoiTower(int n){
        init(n);
        print();
        move(n,a,b,c);
        print();
    }
    static void init(int n){
        for (int i = n; i >= 1; i--) {
            a.addLast(i);
        }
    }
    static void print(){
        System.out.println("-----------");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
    static void move(int n,LinkedList<Integer> a,LinkedList<Integer> b,LinkedList<Integer> c){
        if(n==0){
            return;
        }
        move(n-1,a,c,b);
        c.addLast(a.removeLast());
        move(n-1,b,a,c);
    }
}
