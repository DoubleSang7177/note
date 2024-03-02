package datastructures.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
* 图的宽度优先遍历
* */
public class BSF {

    /*
    * 从node出发，进行图的宽度优先遍历
    * */
    public static void bsf(Node node){
        if (node==null){
            return;
        }
        Queue<Node> queue=new LinkedList<>();
        //用set是为了防止有环图进入死循环
        HashSet<Node> set=new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next : cur.next) {
                if (!set.contains(next)){
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}
