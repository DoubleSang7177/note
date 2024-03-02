package datastructures.greedyAlgprithm;
/*
* 贪心算法
* N皇后问题
* 还有一个位运算能够改善常熟级别的但是没听懂。。。
* */
public class Num {
    /*
    * 经典解法
    * */
    public static int num(int n){
        if (n<1){
            return 0;
        }
        int[] record=new int[n];
        return process(n,0,record);
    }

    /*
    * 潜台词：record[0...i-1]上的任何两个皇后都不共行，不共列，也不共斜线
    * 目前来到了第i行
    * record[0..i-1]表示0到i-1行放了皇后的位置
    * n代表整体有多少行
    * 返回值是摆完所有的皇后，合理的摆法有多少种
    * */
    private static int process(int n, int i, int[] record) {
        if (n==i){//终止行
            return 1;
        }
        int sum=0;
        for (int j = 0; j < n; j++) {//当前在i行，尝试i行所有的列
            if (isValid(record,i,j)){//判断能不能放（i,j)位置
                record[i]=j;//满足条件，放皇后
                sum+=process(n,i+1,record);//放该位置后，下面的行有多少种合理摆法，进行统计
            }
        }
        return sum;//返回所有合理摆法的统计结果
    }

    /*
    * 查看如果把皇后放在(i,j)的位置
    * 是否和record[0....i-1]上已经摆放好的皇后冲突
    * 只要判断不共行和不共斜线就行，因为不在同一行，所有不会共行
    * */
    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {//i行之前的某个k行的皇后
            if (record[k]==j || Math.abs(i-k)==Math.abs(record[k]-j)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        num(14);
        System.out.println("cost time :"+(System.currentTimeMillis() - start)+"ms");


        int[] arr=new int[4];
        System.out.println(process(4, 0, arr));

    }
}
