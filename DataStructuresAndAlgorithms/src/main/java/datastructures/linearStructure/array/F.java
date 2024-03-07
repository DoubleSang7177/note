package datastructures.linearStructure.array;
/*
* 函数改造
* */
public class F {
    /*
    * 给定一个等概率返回1~5的数的函数->改成等概率返回1 ~ 7 的函数
    * */
    public static int f(){
        return (int)(Math.random()*5+1);
    }
    /*
    * 第一步：把f()改成等概率返回0 和 1 的函数，不能改变f()内部值
    * */
    public static int row(){
        int res;
        do{
           res=f();
        }while (res==3);
        return res<3?0:1;
    }
    /*
    * 把row()改成等概率返回1 ~ 7 的函数，不能改变row()内部值
    * */
    public static int g(){
        int res;
        do{
            res=(row()<<2)+(row()<<1)+row();
        }while (res==7);
        return res+1;
    }
    /*
    * 给定一个等概率返回13~21的函数
    * */
    public static int x(){
        return (int)(Math.random()*9+13);
    }
    /*
    * 第一步：把x()加工成0和1发生器
    * */
    public static int x1(){
        int res;
        do{
            res=x();
        }while (res==21);
        return res<17?1:0;
    }
    /*
    * 把x1()改成等概率返回30~59的函数
    * */
    public static int x2(){
        int res;
        do {
            res=(x1()<<5)+(x1()<<4)+(x1()<<3)+(x1()<<2)+x1();
        }while (res>29);
        return res+30;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(x()+" "+x1()+" "+x2());
        }
        for (int i = 0; i < 50; i++) {
            System.out.println(f()+" "+row()+" "+g());
        }
    }
}
