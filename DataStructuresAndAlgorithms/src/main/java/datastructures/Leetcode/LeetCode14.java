package datastructures.Leetcode;
public class LeetCode14 {
    public static String longestCommonPrefix(String[] strs) {
        int min=strs[0].length();
        for (int i = 1; i < strs.length; i++) {
            if(strs[i].length()<min){
                min=strs[i].length();
            }
        }
        int i=0;
        boolean flag=true;
        for (; i < min;) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; ) {
                if(strs[j].charAt(i)!=c){
                    flag=false;
                    break;
                }else{
                    j++;
                }
            }
            if(flag){
                i++;
            }else{
                break;
            }
        }
        return strs[0].substring(0,i);
    }

    public static void main(String[] args) {
        String[] strings={"wstr","stren","strn"};
        System.out.println(longestCommonPrefix(strings));
    }
}
