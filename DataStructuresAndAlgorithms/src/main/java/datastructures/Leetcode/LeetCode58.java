package datastructures.Leetcode;

import java.util.ArrayList;

public class LeetCode58 {
    public static int  lengthOfLastWord(String s) {
        ArrayList<String> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)!=' '){
                sb.append(s.charAt(i));
//                System.out.println(sb);
            }else{
                if(sb.length()!=0){
                    list.add(sb.toString());
                    sb.delete(0,sb.length());
                }
//                System.out.println(sb.length());
            }
        }

        if(sb.length()!=0){
            list.add(sb.toString());
        }

        System.out.println(list);
        System.out.println(list.get(list.size() - 1));

        return list.get(list.size() - 1).length();

    }

    public static void main(String[] args) {
        StringBuilder sb=new StringBuilder();
        sb.append('f');
        sb.append('f');
        System.out.println(sb.toString());
        lengthOfLastWord("   fly me   to   the moon  ");
    }
}
