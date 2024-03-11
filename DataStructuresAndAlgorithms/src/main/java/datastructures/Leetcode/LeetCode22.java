package datastructures.Leetcode;

import java.util.ArrayList;
import java.util.List;

public class LeetCode22 {
    public static List<String> generateParenthesis(int n) {
        List<String> result=new ArrayList<>();
        process(n,n,0,new StringBuilder(),result);
        return result;
    }
    public static void process(int left,int right,int count,StringBuilder sb,List<String> result){
        if (left==0 && right==0 && count==0){
            result.add(sb.toString());
            return;
        }
        /*
        * 可以加左括号
        * */
        if (left>0){
            sb.append('(');
            int index= sb.length()-1;
            process(left-1,right,count+1,sb,result);
            sb.delete(index,sb.length());
        }
        /*
        * 可以选择右括号
        * */
        if (count>=1 && right>0 ){
            sb.append(')');
            process(left,right-1,count-1,sb,result);
        }
    }
    public static void main(String[] args) {
        for (String s : generateParenthesis(3)) {
            System.out.println(s);
        }
    }
}
