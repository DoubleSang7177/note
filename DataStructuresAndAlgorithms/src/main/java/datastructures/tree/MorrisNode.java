package datastructures.tree;
/*
* MorrisNode实现二叉树遍历
* 不是系统压栈，是改变叶节点的指针指向
* */
public class MorrisNode {
    public static void morrisNode(TreeNode head){
        if (head ==null){
            return;
        }
        TreeNode cur=head;
        TreeNode mostRight;
        while (cur!=null){//过流程
            mostRight=cur.left;//找cur左子树上的最右节点，从左子树头节点开始寻找mostRight
            if (mostRight!=null){//有左子树
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){//这是第一次来到cur
                    System.out.println(cur.val);
                    mostRight.right=cur;
                    cur=cur.left;
                    continue;
                }else{//mostRight.right==cur
                    mostRight.right=null;//之前来过cur,接下来要移到cur.right
                }
            }
            cur=cur.right;//cur没有左子树或者已经走完左子树
        }

    }

    /*
    * morris实现二叉树先序遍历
    * */
    public static void morrisPre(TreeNode head){
        if (head ==null){
            return;
        }
        TreeNode cur=head;
        TreeNode mostRight;
        while (cur!=null){//过流程
            mostRight=cur.left;//找cur左子树上的最右节点，从左子树头节点开始寻找mostRight
            if (mostRight!=null){//有左子树
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){//这是第一次来到cur
                    System.out.println(cur.val);
                    mostRight.right=cur;
                    cur=cur.left;
                    continue;
                }else{//mostRight.right==cur
                    mostRight.right=null;//之前来过cur,接下来要移到cur.right
                }
            }else{
                System.out.println(cur.val);
            }
            cur=cur.right;//cur没有左子树或者已经走完左子树
        }

    }

    /*
     * morris实现二叉树中序遍历
     * */
    public static void morrisIn(TreeNode head){
        if (head ==null){
            return;
        }
        TreeNode cur=head;
        TreeNode mostRight;
        while (cur!=null){//过流程
            mostRight=cur.left;//找cur左子树上的最右节点，从左子树头节点开始寻找mostRight
            if (mostRight!=null){//有左子树
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){//这是第一次来到cur
                    mostRight.right=cur;
                    cur=cur.left;
                    continue;
                }else{//mostRight.right==cur
                    mostRight.right=null;//之前来过cur,接下来要移到cur.right
                }
            }
            System.out.println(cur.val);//第二次来到cur和没有左子树时打印
            cur=cur.right;//cur没有左子树或者已经走完左子树
        }

    }

    /*
     * morris实现二叉树后序遍历
     * */
    public static void morrisAfter(TreeNode head){
        if (head ==null){
            return;
        }
        TreeNode cur=head;
        TreeNode mostRight;
        while (cur!=null){//过流程
            mostRight=cur.left;//找cur左子树上的最右节点，从左子树头节点开始寻找mostRight
            if (mostRight!=null){//有左子树
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){//这是第一次来到cur
                    mostRight.right=cur;
                    cur=cur.left;
                    continue;
                }else{//mostRight.right==cur
                    mostRight.right=null;//之前来过cur,接下来要移到cur.right
                    printEdge(cur.left);//第二次到达某节点时打印该节点的左子树右边界
                }
            }
            cur=cur.right;//cur没有左子树或者已经走完左子树
        }
        printEdge(head);//最后打印整棵树的右边界
        System.out.println();

    }
    /*
    * 逆序打印以x为头的这颗树的右边界
    * */
    public static void printEdge(TreeNode x){
        TreeNode tail=reverse(x);
        TreeNode cur=tail;
        while (cur!=null){
            System.out.print(x.val+" ");
            cur=cur.right;
        }
        reverse(tail);
    }

    private static TreeNode reverse(TreeNode from) {
        TreeNode pre=null;
        TreeNode next =null;
        while (from !=null){
            next = from.right;
            from.right=pre;
            pre= from;
            from = next;
        }
        return pre;
    }
}