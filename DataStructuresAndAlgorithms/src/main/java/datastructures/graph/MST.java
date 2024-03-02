package datastructures.graph;

import java.util.*;

/*
 * 找出图中的最小生成树
 * 最小生成树 MinimumSpanningTree
 * */
public class MST {
    public static HashMap<Node, List<Node>> listMap = new HashMap<>();

    //生成每个节点所在的集合表
    public static void getSetMap(Graph graph) {
        if (graph == null) {
            return;
        }
        for (Node cur : graph.nodes.values()) {
            List<Node> set = new ArrayList<>();
            set.add(cur);
            listMap.put(cur, set);
        }
    }

    //判断两个节点所在的集合是否相同
    public static boolean isSameSet(Node from, Node to) {
        List<Node> fromList = listMap.get(from);
        List<Node> toList = listMap.get(to);
        return fromList == toList;
    }

    //合并两个节点的集合
    public static void union(Node from, Node to) {
        List<Node> fromList = listMap.get(from);
        List<Node> toList = listMap.get(to);
        for (Node node : fromList) {
            toList.add(node);
            listMap.put(node, toList);
        }
    }

    //定义一个边比较器
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /*
     * k算法寻找最小生成树
     * */
    public static Set<Edge> kruskalMST(Graph graph) {
        Set<Edge> result = new HashSet<>();
        getSetMap(graph);
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        queue.addAll(graph.edges);
        while (!queue.isEmpty()) {
            Edge cur = queue.poll();
            if (!isSameSet(cur.from, cur.to)) {
                result.add(cur);
                union(cur.from, cur.to);
            }
        }
        return result;
    }
    /*
     * p算法寻找最小生成树
     * */
    public static Set<Edge> primMST(Graph graph) {
        Set<Edge> result = new HashSet<>();
        //标记过的边放入队列按照从小到大的顺序排列
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        //判断节点是否被考察过,考察过的放入集合中
        Set<Node> isCheck = new HashSet<>();
        for (Node node : graph.nodes.values()) {//为了处理整个图不连通的问题，森林
            //从一个没有被考察过的节点开始
            if (!isCheck.contains(node)){
                //把当前节点标记为已考察
                isCheck.add(node);
                //把该节点的所有边放入标记队列
                queue.addAll(node.edges);
                while (!queue.isEmpty()){
                    Edge poll = queue.poll();
                    if (!isCheck.contains(poll.to)){
                        result.add(poll);
                        isCheck.add(poll.to);
                        queue.addAll(poll.to.edges);
                    }
                }
            }
        }
        return result;
    }

    /*
     * 我自己写的p算法代码，有待考验
     * */
    public static Set<Edge> primMST2(Graph graph) {
        Set<Edge> result = new HashSet<>();
        //标记过的边放入队列按照从小到大的顺序排列
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        //判断节点是否被考察过,考察过的放入集合中
        Set<Node> isCheck = new HashSet<>();
        //判断边是否已经标记过
        HashMap<Edge, Boolean> isMark = new HashMap<>();
        //一开始所有的边都是没有标记过的
        for (Edge edge : graph.edges) {
            isMark.put(edge, false);
        }
        Stack<Node> stack=new Stack<>();
        for (Node value : graph.nodes.values()) {
            stack.push(value);//选择任意一个节点开始，，怎么选呢？
            //选择图中的任意节点开始寻找最小生成树
            while (!stack.isEmpty()) {
                ArrayList<Edge> edges = stack.pop().edges;
                //标记所有没有标记过的边放入队列
                for (Edge edge : edges) {
                    if (!isMark.get(edge)){
                        isMark.put(edge,true);
                        queue.add(edge);
                    }
                }
                //取出标记过的边中的最小
                Edge poll = queue.poll();
                while (!queue.isEmpty()){
                    if (!isCheck.contains(poll.to) ||!isCheck.contains(poll.from)){
                        result.add(poll);
                        isCheck.add(poll.to);
                        isCheck.add(poll.from);
                        stack.push(!isCheck.contains(poll.to)?poll.to:poll.from);
                    }else{
                        poll=queue.poll();
                    }
                }
            }
        }
        return result;
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
        Set<Edge> edges = primMST2(graph);
        for (Edge edge : edges) {
            System.out.println(edge.weight);
        }
        System.out.println("=======");
        Set<Edge> edges2 = primMST(graph);
        for (Edge edge : edges2) {
            System.out.println(edge.weight);
        }
    }

}
