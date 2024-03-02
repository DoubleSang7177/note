package datastructures.tree;
/*
* 将一个纸条对折N次
* 从上到下打印折痕的方向,打印结果满足下面的二叉树中序遍历打印
*               凹          二叉树特点：头凹，其余树节点左孩子为凹，右孩子为凸
*             /    \        折叠次数N等于树的高度
*           凹      凸
*          /  \    /  \
*        凹    凸  凹  凸
* */
public class TreeRelatedExercise {

    /*
    * i为节点的层数
    * N为一共的层数
    * down==true 凹
    * down==false 凸
    * */
    public static void print(int N){
        p(N,1,true);
    }
    public static void p(int N,int i,boolean down){
        if (i>N){
            return;
        }
        p(N,i+1,true);
        System.out.print(down ?"凹":"凸");
        p(N,i+1,false);
    }
    public static void main(String[] args) {
        print(3);
    }
}
