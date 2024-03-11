package datastructures.linearStructure.array;

/*
* 左右括号匹配问题
* */
public class NeedParentheses {

    public static int needParentheses(String str){
        int res=0;
        int count=0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)=='('){//左括号
                count++;
            }else{
                if (count==-1){
                    res+=1;
                }else{
                    count--;
                }
            }
        }
        return res+count;
    }

    public static boolean checkValidString2(String s) {
        if (s.isEmpty()){
            return true;
        }
        int change=0;
        int left =0;
        int right=0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)=='('){
                left++;
            } else if (s.charAt(i)=='*'){
                change++;
            }else{
                right++;
                //消费 （ 还是 *
                if (right>(left+change)){
                    return false;
                }

            }
        }
        return change>= Math.abs(left-right);
    }


}
