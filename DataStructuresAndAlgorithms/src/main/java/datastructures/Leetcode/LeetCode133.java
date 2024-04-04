package datastructures.Leetcode;

import java.util.*;

public class LeetCode133 {
    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    static HashMap<Integer, Node> map = new HashMap<>();
    static Set<Integer> flag = new HashSet<>();

    public static Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        HashMap<Integer,Node> x=map;
        Set<Integer> y=flag;
        flag.add(node.val);
        Node nodeCopy = map.getOrDefault(node.val, new Node(node.val));
        List<Node> neighborsCopy = new ArrayList<>();
        for (Node neighbor : node.neighbors) {
            Node neighborCopy = map.getOrDefault(neighbor.val, new Node(neighbor.val));
            neighborsCopy.add(neighborCopy);
        }
        nodeCopy.neighbors = neighborsCopy;
        for (Node neighbor : node.neighbors) {
            if (!flag.contains(neighbor.val)) {
                cloneGraph(neighbor);
            }
        }
        return nodeCopy;
    }

    public static void main(String[] args){
        Node n1=new Node(1);
        Node n2=new Node(2);
        Node n3=new Node(3);
        Node n4=new Node(4);
        List<Node> ne1=new ArrayList<>();
        ne1.add(n2);
        ne1.add(n4);
        List<Node> ne2=new ArrayList<>();
        ne2.add(n1);
        ne2.add(n3);
        List<Node> ne3=new ArrayList<>();
        ne3.add(n2);
        ne3.add(n4);
        List<Node> ne4=new ArrayList<>();
        ne4.add(n1);
        ne4.add(n3);
        n1.neighbors=ne1;
        n2.neighbors=ne2;
        n3.neighbors=ne3;
        n4.neighbors=ne4;
        System.out.println(cloneGraph(n1));


        Set<Integer> path = new HashSet<>();
        path.add(1);
        path.add(2);
        path.add(2);
        path.add(2);
        path.add(3);
        path.add(4);
        path.remove(3);
        path.remove(1);
        for(int i:path){
            System.out.println(i);
        }

    }
}
