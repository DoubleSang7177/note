package datastructures.graph;

import java.util.ArrayList;

/*
* 图节点
* */
public class Node {
    public int value;
    public int in;//入度：入节点个数
    public int out;//出度：出节点个数
    public ArrayList<Node> next;
    public ArrayList<Edge> edges;

    public Node() {
        this.next=new ArrayList<>();
        this.edges=new ArrayList<>();
    }

    public Node(int value) {
        this.next=new ArrayList<>();
        this.edges=new ArrayList<>();
        this.value = value;
    }

    public Node(int value, int in, int out, ArrayList<Node> next, ArrayList<Edge> edges) {
        this.value = value;
        this.in = in;
        this.out = out;
        this.next = next;
        this.edges = edges;
    }
}
