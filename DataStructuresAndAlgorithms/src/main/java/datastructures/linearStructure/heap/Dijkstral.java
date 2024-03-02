package datastructures.linearStructure.heap;
import datastructures.graph.Edge;
import datastructures.graph.Node;
import java.util.HashMap;
/*
* 用自定义的小根堆实现迪杰斯特拉算法
* 向小根堆中加入某节点和到该节点的新距离之后
* 小根堆会根据传入的新距离判断是否要更新距离，
* 并且加入一个新节点会自动进行热排序，一直保持小根堆，
* pop的节点都是距离最小的节点，加入结果集中返回
*
* */
public class Dijkstral {
    public static HashMap<Node,Integer> dijkstral(Node head,int size){
        HashMap<Node,Integer> resultMap=new HashMap<>();
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head,0);
        while (!nodeHeap.isEmpty()) {
            NodeHeap.NodeRecord pop = nodeHeap.pop();
            Node cur= pop.node;
            int distance= pop.distance;
            for (Edge edge : cur.edges) {
                //将到当前节点的距离distance加上邻边的权重weight作为到to节点的新距离传入小根堆，小根堆会判断是否要更新成传入的距离，会做判断
                nodeHeap.addOrUpdateOrIgnore(edge.to,distance+ edge.weight);
            }
            resultMap.put(cur,distance);
        }
        return resultMap;
    }
    public static class NodeHeap{
        /*
         * distance操作
         * 1. 在堆上的，update
         * 2. 没进过堆的，new
         * 3. 进过堆但是不在堆上的，ignore
         * */
        public void addOrUpdateOrIgnore(Node node,int distance){
            //第一种情况：已经在堆上
            if (inHeap(node)){
                distanceMap.put(node,Math.min(distanceMap.get(node),distance));
                //冒泡
                insertHeapify(node,heapIndexMap.get(node));
            }
            //第二种情况：新进的节点，放在最后一个位置
            if (!isEntered(node)){
                nodes[size]=node;
                heapIndexMap.put(node,size);
                distanceMap.put(node,distance);
                //新加入，冒泡
                insertHeapify(node,size++);
            }
            //第三种情况：已经被考察完的节点，不做处理

        }
        public NodeRecord pop(){
            //弹出根节点
            NodeRecord nodeRecord=new NodeRecord(nodes[0],distanceMap.get(nodes[0]));
            //将到根节点的距离标为-1
            swap(0,size-1);
            //将要弹出的节点的下标表示成-1
            heapIndexMap.put(nodes[size-1],-1);
            //从距离map中删除该节点信息
            distanceMap.remove(nodes[size-1]);
            nodes[size-1]=null;
            //从新的根节点开始往下移动一轮
            heapify(0,--size);
            return nodeRecord;
        }
        //堆上节点
        private Node[] nodes;
        //key为堆上节点，value为该节点在堆上的位置
        private HashMap<Node,Integer> heapIndexMap;
        //key为堆上节点，value为A节点到该节点的距离
        private HashMap<Node,Integer> distanceMap;
        //堆上节点个数
        private int size;
        public NodeHeap(int size) {
            this.nodes = new Node[size];
            this.heapIndexMap = new HashMap<>();
            this.distanceMap = new HashMap<>();
            this.size=0;
        }
        public boolean isEmpty(){
            return size==0;
        }

        //标志一个节点是否进过堆
        private boolean isEntered(Node node){
            return heapIndexMap.containsKey(node);
        }
        //判断一个节点是不是在堆上
        private boolean inHeap(Node node){
            return isEntered(node) && heapIndexMap.get(node)!=-1;
        }
        //交换两个节点
        private void swap(int index1,int index2){
            heapIndexMap.put(nodes[index1],index2);
            heapIndexMap.put(nodes[index2],index1);
            Node temp=nodes[index1];
            nodes[index1]=nodes[index2];
            nodes[index2]=temp;
        }


        public static class NodeRecord{
            private Node node;
            private int distance;

            public NodeRecord(Node node, int distance) {
                this.node = node;
                this.distance = distance;
            }
        }

        //向下移动
        private void heapify(int index, int size) {
            int left=index*2+1;
            while (left<size){
                int min=left+1<size&&distanceMap.get(nodes[left])<distanceMap.get(nodes[left+1])?left:left+1;
                min=distanceMap.get(nodes[min])<distanceMap.get(nodes[index])?min:index;
                if (min==index){
                    return;
                }
                swap(min,index);
                index=min;
                left=index*2+1;
            }
        }
        //距离小的节点往上窜
        private void insertHeapify(Node node, Integer index) {
            while (distanceMap.get(nodes[index])<distanceMap.get(nodes[(index-1)/2])){
                swap(index,(index-1)/2);
                index=(index-1)/2;
            }
        }
    }
}
