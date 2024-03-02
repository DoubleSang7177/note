package datastructures.violentRecursion;

import java.util.Stack;

/*
* 给出给栈，逆序这个栈，不能申请额外的数据结构，只能使用递归函数
* */
public class ReverseStackUsingRecursive {

    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()){
            return;
        }
        int f = f(stack);
        reverse(stack);
        stack.push(f);
    }

    public static int f(Stack<Integer> stack){
        int result=stack.pop();
        if (stack.isEmpty()){
            return result;
        }else{
            int last=f(stack);
            stack.push(result);
            return last;
        }
    }
}
