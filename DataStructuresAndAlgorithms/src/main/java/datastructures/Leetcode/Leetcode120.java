package datastructures.Leetcode;

import java.util.Stack;

public class Leetcode120 {
    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("+")) {
                int x = stack.pop();
                int y=stack.pop();
                stack.push(x+y);
            } else if (tokens[i].equals("-")) {
                int x = stack.pop();
                int y=stack.pop();
                stack.push(y-x);
            } else if (tokens[i].equals("*")) {
                int x = stack.pop();
                int y=stack.pop();
                stack.push(x*y);
            } else if (tokens[i].equals("/")) {
                int x = stack.pop();
                int y=stack.pop();
                stack.push(y/x);
            } else {
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args){
        String[] tokens = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        System.out.println(evalRPN(tokens));
    }
}
