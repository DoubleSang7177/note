package datastructures.graph;

/*
* 图的边
* */
public class Edge {
    public int weight;//权重或者长度
    public Node from;
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
