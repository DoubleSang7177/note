package datastructures.violentRecursion;

/*
* 暴力递归
* 数值不同的牌排成一条线，玩家A和玩家B依次拿走每一张牌，规定玩家A先拿，玩家B后拿
* 一个玩家只能拿走最左或最右的牌，玩家A和玩家B都绝顶聪明，请返回最后获胜者的分数
* */
public class CardsInline {
    public static int win(int[] arr){
        if (arr==null || arr.length==0){
            return 0;
        }
        //0到arr.length-1范围上，一个玩家当先手，另一个玩家当后手
        return Math.max(first(arr,0,arr.length-1),second(arr,0,arr.length-1));
    }
    //先手玩家
    public static int first(int[] arr,int l,int r){
        if (l==r){
            return arr[l];
        }
        //作为先手，我会选择对我自己最有利的，所以选max
        return Math.max(arr[l]+second(arr,l+1,r),arr[r]+second(arr,l,r-1));
    }
    //后手玩家
    public static int second(int[] arr,int l,int r){
//        如果l==r，作为后手，这唯一的牌会被先手拿走，所以只能返回0
        if (l==r){
            return 0;
        }
        //作为后手，先手会把最坏的情况给我，所以选min
        return Math.min(first(arr,l+1,r),first(arr,l,r-1));
    }
}
