package datastructures.graph;
/*
* 图生成器
* 也就是把用户给出的图结构以自己熟悉的图方式表现出来
* */
public class GraphGenerator {

    /*
    * matrix矩阵
    * N*3
    * 第一列代表from节点的值 第二列代表to节点的值 第三列代表边的权重
    * */
    public static Graph createGraph(int[][] matrix){
        Graph graph=new Graph();
        for (int i = 0; i < matrix.length; i++) {
            int from = matrix[i][0];
            int to = matrix[i][1];
            int weight = matrix[i][2];
            if (!graph.nodes.containsKey(from)){
                graph.nodes.put(from,new Node(from));
            }
            if (!graph.nodes.containsKey(to)){
                graph.nodes.put(to,new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge=new Edge(weight,fromNode,toNode);
            fromNode.out++;
            fromNode.next.add(toNode);
            fromNode.edges.add(edge);
            toNode.in++;
            graph.edges.add(edge);
        }
        return graph;
    }
}
