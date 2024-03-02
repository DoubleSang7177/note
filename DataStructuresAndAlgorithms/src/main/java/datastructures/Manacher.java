package datastructures;

import java.util.LinkedList;

/*
* 求最大回文串长度
* */
public class Manacher {

    /*
    * 用'#'对原始字符串进行填充
    * */
    public static char[] manacherString(String str){
        char[] chars = str.toCharArray();
        char[] res=new char[str.length()*2+1];
        int index=0;
        for (int i = 0; i < res.length; i++) {
            res[i]=(i & 1)==0?'#':chars[index++];
        }
        return res;
    }

    public static int maxLcpsLength(String str){
        char[] chars = manacherString(str);//得到填充后的字符串
        int[] pArr=new int[chars.length];
        int r=-1;//所有以c为中心扩的回文串中最靠右的右边界+1
        int c=-1;
        int max=Integer.MIN_VALUE;
        for (int i = 0; i < chars.length; i++) {
            //先找出不需要考虑的范围
           pArr[i]= r>i?Math.min(r-i,pArr[c*2-i]):1;// i>=r时，pArr[i]=1暴力扩！不用考虑的区域只有自己
            //r>i时，i分在边界上和边界内两种情况，在边界上那么pArr[i]=r-i=1（因为r-i<=pArr[c*2-i])
            // 边界内那么就是Math.min(r-i,pArr[c*2-i])
           while (i+pArr[i]<chars.length && i-pArr[i]>-1){
               if (chars[i+pArr[i]]==chars[i-pArr[i]]){
                   pArr[i]++;
               }else{
                   break;
               }
           }
           //判断暴力扩有没有扩出最右边界，有则更新r
           if (i+pArr[i]>r){
               r=i+pArr[i];
               c=i;
           }
           //更新最大回文半径
           max=Math.max(max,pArr[i]);
        }
        //返回最大回文串长度-》原始串的最大回文串长度=填充串的最大回文半径-1
        return max-1;
    }

    public static void main(String[] args) {
        LinkedList<Integer> list=new LinkedList<>();
        list.addLast(12);
        list.addLast(13);
        list.addLast(16);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        System.out.println(maxLcpsLength("01213210"));
    }
}
