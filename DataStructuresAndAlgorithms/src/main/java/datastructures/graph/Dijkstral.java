package datastructures.graph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/*
* 迪杰斯特拉算法
* 用一个HashMap记录到每个节点的距离
* 用一个HashSet集合记录锁定(不在更新到该节点的距离，已经找到 到该节点的最短距离)的节点
* */
public class Dijkstral {
    public static HashMap<Node,Integer> dijkstral(Node head){
        //记录从指定节点到所有节点的最短距离
        HashMap<Node,Integer> distanceMap=new HashMap<>();
        //如果找到到某节点的最短距离，那么锁住该节点和对应距离
        Set<Node> selected=new HashSet<>();
        //开始节点到自己的距离为零，并且目前没有被锁定
        distanceMap.put(head,0);
        //首先找到到指定节点距离最短的并且没有被锁定的节点
        Node minNode=getMinDistanceAndUnSelectedNode(distanceMap,selected);//这里得到的minNode就是指定节点自己
        while (minNode!=null){
            int minNodeDistance= distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode=minNode!=edge.to?edge.to:minNode;//无向图，能确保edge.to是这个边上的另一端的节点吗？有点疑问
//                TrieNode toNode=minNode!=edge.to; //为了安全起见还是判断以下
                if (!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode,minNodeDistance+edge.weight);
                }else {
                    distanceMap.put(toNode,Math.min(distanceMap.get(toNode),minNodeDistance+edge.weight));
                }
            }
            selected.add(minNode);
            minNode=getMinDistanceAndUnSelectedNode(distanceMap,selected);
        }
        return distanceMap;
    }

    private static Node getMinDistanceAndUnSelectedNode(HashMap<Node, Integer> distanceMap, Set<Node> selected) {
        int minDistance=Integer.MAX_VALUE;
        Node minNode=null;
        for (Node node : distanceMap.keySet()) {
            if (distanceMap.get(node)<minDistance && !selected.contains(node)){
                minNode=node;
                minDistance=distanceMap.get(node);
            }
        }
        return minNode;
    }
}
