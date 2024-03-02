package datastructures.Leetcode;

class MinStack {

    private int size;
    private Node<Integer> top;

    private static class Node<E> {
        Node<E> next;
        E value;
        public Node(E value,Node<E> next) {
            this.next = next;
            this.value = value;
        }
    }
    public MinStack() {
        top=new Node<>(-1,null);
        size=0;
    }

    public void push(int val) {
        top.next=new Node<>(val,top.next);
        size++;
    }

    public void pop() {
        if(size>0){
            top.next=top.next.next;
        }
    }

    public int top() {
        if(size>0){
           return top.next.value;
        }
        return -1;
    }

    public int getMin() {

        return 0;
    }

    public static void main(String[] args) {

        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();  // --> 返回 -3.
        minStack.pop();
        minStack.top();      //--> 返回 0.
        minStack.getMin();  //  --> 返回 -2.
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */