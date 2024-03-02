package datastructures.graph;

import java.util.*;

/*
* 拓扑排序
* */
public class SortedTopology {

    //有向无环图
    public static ArrayList<Node> sortedTopology(Graph graph){
        if (graph ==null){
            return null;
        }
        //收集入度为0的节点
        Queue<Node> queue=new LinkedList<>();
        //记录所有节点的入度
        HashMap<Node,Integer> map=new HashMap<>();
        //保存结果并返回
        ArrayList<Node> list=new ArrayList<>();
        for (Node node : graph.nodes.values()) {
            if (node.in==0){
                queue.add(node);
            }
            map.put(node,node.in);
        }
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            list.add(cur);
            for (Node next:cur.next){
                map.put(next,map.get(next)-1);
                if (map.get(next)==0){
                    queue.add(next);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        Node A=new Node(1);
        A.in=0;
        A.out=2;
        Node B=new Node(2);
        B.in=1;
        B.out=2;
        Node C=new Node(3);
        C.in=2;
        C.out=1;
        A.next.add(B);
        A.next.add(C);
        B.next.add(C);
        Node D=new Node(4);
        D.in=2;
        D.out=0;
        B.next.add(D);
        C.next.add(D);
        Edge e1 = new Edge(1, A, C);
        Edge e2 = new Edge(1, A, B);
        Edge e3 = new Edge(1, B, D);
        Edge e4 = new Edge(1, B, C);
        Edge e5 = new Edge(1, C, D);
        A.edges.add(e1);
        A.edges.add(e2);
        B.edges.add(e3);
        B.edges.add(e4);
        C.edges.add(e5);
        graph.nodes.put(1,A);
        graph.nodes.put(2,B);
        graph.nodes.put(3,C);
        graph.nodes.put(4,D);
        graph.edges.add(e1);
        graph.edges.add(e2);
        graph.edges.add(e3);
        graph.edges.add(e4);
        graph.edges.add(e5);
        for (Node x : sortedTopology(graph)) {
            System.out.print(x.value+" ");
        }
        System.out.println();
    }
}
