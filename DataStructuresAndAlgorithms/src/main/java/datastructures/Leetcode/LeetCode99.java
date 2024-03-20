package datastructures.Leetcode;
import java.util.ArrayList;
import java.util.List;
/*
*给你二叉搜索树的根节点 root ，该树中的
* 恰好两个节点的值被错误地交换。请在不改
* 变其结构的情况下，恢复这棵树。
* */
public class LeetCode99 {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 恢复二叉搜索树
     * @param root 根节点
     */
    public static void recoverTree(TreeNode root) {
        List<TreeNode> res=new ArrayList<>();
        mid(root,res);
        // 1 2 3 4 5 6 7         1 5 3 4 2 6 7
        //1 2 4 3 5 6 7
        /*
        * 找到二叉搜索树中序遍历得到值序列的不满足条件的位置。
        * 如果有两个，我们记为 i和j（i<j且 ai>ai+1 && aj>aj+1，
        * 那么对应被错误交换的节点即为 ai对应的节点和 aj+1对应的节点，我们分别记为 x 和 y。
        * 如果有一个，我们记为 i，那么对应被错误交换的节点即为 ai对应的节点和 ai+1对应的节点，
        * 我们分别记为 x 和 y。交换 x 和 y 两个节点即可。
        * */
        List<Integer> index =new ArrayList<>();
        for (int i = 0; (i+1) < res.size(); i++) {
            if (res.get(i).val>res.get(i+1).val){
                index.add(i);
            }
        }
        if (index.size()==2){
            int  i= index.get(0);
            int  j= index.get(1);
            TreeNode n1 = res.get(i);
            TreeNode n2 = res.get(j + 1);
            swap(n1,n2);
        }else{
            TreeNode n1 = res.get(index.get(0));
            TreeNode n2 = res.get(index.get(0)+1);
            swap(n1,n2);
        }
    }

    /**
     * 搜索二叉树中序遍历
     * 中序遍历时间复杂度O(N) N为节点数
     * @param cur 当前遍历到的节点
     * @param res 中序遍历结果存放的集合
     */
    public static void mid(TreeNode cur, List<TreeNode> res){
        if (cur==null){
            return;
        }
        mid(cur.left,res);
        res.add(cur);
        System.out.println(cur.val);
        mid(cur.right,res);
    }

    /**
     * 交换两个节点值
     * @param n1 交换节点1
     * @param n2 交换节点2
     */
    private static void swap(TreeNode n1, TreeNode n2) {
        int temp=n1.val;
        n1.val=n2.val;
        n2.val=temp;
    }

    /*public static TreeNode[] process(TreeNode head) {
        if (head == null) {
            return null;
        }
        TreeNode[] left = process(head.left);
        TreeNode[] right = process(head.right);
        if (left==null && right==null){
            return new TreeNode[]{head,head};
        }else if (left==null){
            if (head.val>right[1].val){
                int temp = head.val;
                head.val = right[1].val;
                right[1].val = temp;
            }
            return new TreeNode[]{right[0],head};
        }else if (right==null){
            if (head.val<left[0].val){
                int temp = head.val;
                head.val = left[0].val;
                left[0].val = temp;
            }
            return new TreeNode[]{head,left[1]};
        }else{
            if (head.val > left[0].val && head.val < right[1].val) {
                return new TreeNode[]{right[0], left[1]};
            } else if (head.val < left[0].val) {
                int temp = head.val;
                head.val = left[0].val;
                left[0].val = temp;
                //交换后
                return new TreeNode[]{right[0], left[1]};//
            } else {
                int temp = head.val;
                head.val = right[1].val;
                right[1].val = temp;
                //交换后
                return new TreeNode[]{right[0], left[1]};
            }
        }
    }*/

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(3, null, new TreeNode(2, null, null)), null);
//        process(root);

        recoverTree(root);
        System.out.println();
    }
}
