package datastructures.tree;
/*
 * 找到某一个树节点的后继节点
 * 后继节点就是中序遍历过程中的后一个节点
 * */
public class GetSuccessorNode {
    /*
    * 带有父节点的树节点
    * */
    private static class Node {
        int value;
        Node parent;
        Node left;
        Node right;

        public Node(int value, Node parent, Node left, Node right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }
    /*
    * 找到后继节点
    * */
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {//如果某节点存在右子树，那么后继节点就是右子树上最左侧的节点
            return mostLeftNode(node.right);
        }else{
            Node parent = node.parent;
            while (parent!=null && node!=parent.left){//如果当前节点是其父节点的左孩子，那么后继节点就是父节点，否则需要向上窜
                //窜到顶了说明该节点是整棵树上最右侧的节点，那么后继节点就是null
                node=parent;
                parent=node.parent;
            }
            return parent;
        }
    }

    /*
     * 找到某树上最左的节点
     * */
    private static Node mostLeftNode(Node node) {
        if (node == null) {
            return null;
        }
        Node leftNode = mostLeftNode(node.left);
        return leftNode != null ? leftNode : node;

    }
}
