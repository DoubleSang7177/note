package datastructures.Leetcode;
/*
* 超级洗衣机
* */
public class LeetCode517 {
    public static int findMinMoves(int[] machines) {
        if (machines==null || machines.length==0){
            return 0;
        }
        int total=0;
        for (int machine : machines) {
            total+=machine;
        }
        if (total%machines.length!=0){
            return -1;
        }
        int each=total/machines.length;
        int res=0;
        int leftSum=0;
        for (int i = 0; i < machines.length; i++) {
            int left=leftSum-i*each;
            int right=total-left-machines[i]-each*(machines.length-i-1);
            if(left<0 && right<0){//两边都赤字
                res=Math.max(res,Math.abs(left)+Math.abs(right));
            }else{
                res=Math.max(res,Math.max(Math.abs(left),Math.abs(right)));
            }
            leftSum+=machines[i];
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(findMinMoves(new int[]{0,3,0}));
    }
}
