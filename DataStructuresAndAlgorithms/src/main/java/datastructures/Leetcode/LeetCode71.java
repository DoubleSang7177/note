package datastructures.Leetcode;


import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class LeetCode71 {
    public static String simplifyPath(String path) {
        Stack<Character> stack=new Stack<>();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            stack.push(path.charAt(i));
        }
        stack.pop();
        for (int i = 1; i < path.length(); i++) {
            char c=stack.pop();
            if(sb.length()!=0&&c=='/'&& sb.charAt(sb.length()-1)=='/'){
                continue;
            }else{
                sb.append(c);
            }
        }
        return sb.reverse().toString();

    }

    public static void main(String[] args) {
        System.out.println(simplifyPath2("/home//foo/"));
    }

    public static String simplifyPath2(String path){
        Deque<String> stack = new LinkedList<>();
        for (String item : path.split("/")) {
            if (item.equals("..")) {
                if (!stack.isEmpty()) stack.pop();
            } else if (!item.isEmpty() && !item.equals(".")) stack.push(item);
        }
        String res = "";
        for (String s : stack) {
            System.out.println(s);
        }
        for (String d : stack) res = "/" + d + res;

        return res.isEmpty() ? "/" : res;
    }
}
