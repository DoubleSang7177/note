package datastructures.graph;

import java.util.HashMap;
import java.util.HashSet;
/*
* 图结构
* */
public class Graph {
    public HashMap<Integer, Node> nodes;//点集
    public HashSet<Edge> edges;//边集

    public Graph() {
        this.nodes=new HashMap<>();
        this.edges=new HashSet<>();
    }

    public Graph(HashMap<Integer, Node> nodes, HashSet<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

}
