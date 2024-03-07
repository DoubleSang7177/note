package datastructures.greedyAlgprithm;
/*
* 牛羊吃草问题
* 先手和后手都是绝顶聪明的人
* 吃最后一捆的牛算赢
* 一次只能吃1捆 4捆 16捆 或 64捆...
* 每次轮到该牛吃时必须选择吃
* 只有0捆草时是后手赢
* */
public class Eat {
    /*
    * 递归法
    * */
    public static String winner(int n){
        if (n<5){
            return (n==0 || n==2 )?"后手":"先手";
        }
        int base=1;
        while (base<=n){
            if (winner(n-base).equals("后手")){
                return "先手";
            }
            if (base> n/4){//防止base*4之后溢出,溢出正数的最大边界
                break;
            }
            base*=4;
        }
        return "后手";
    }
    /*
    * 达标法 复杂度O(1)
    * */
    public static String winner2(int n){
        if (n%5==0 || n%5==2){
            return "后手";
        }else{
            return "先手";
        }
    }
    public static void main(String[] args) {
        for (int i = 1; i < 50; i++) {
            System.out.println(i+" "+winner(i)+" vs "+winner2(i));
        }
    }
}
