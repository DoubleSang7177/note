package datastructures.greedyAlgprithm;
/*
* 有n个苹果
* 只能用8容量的袋子和6容量的袋子装
* 而且每个袋子都要装满，否则不卖，返回-1
* 找出装好n个苹果的最少袋子数量
* */
public class MinBagsForApple {
    public static int minBags2(int apple){
        return process(apple, apple /8);
    }
    public static int process(int apple, int eight){
        int rest = apple - eight * 8;
        if (rest>=24){
            return -1;
        }
        if (eight==0){
            return apple % 6==0?apple/6:-1;
        }
        if (rest % 6 != 0){
            return process(apple,eight-1);
        }else{
            return eight + rest /6;
        }
    }

    public static int minBags(int apple){
        if (apple<=0){
            return -1;
        }
        int bag6=-1;
        int bag8=apple/8;
        int rest=apple-bag8*8;
        while (bag8>=0 && rest<24){
            int bag6Result=minBag6(rest);
            if (bag6Result!=-1){
                bag6=bag6Result;
                break;
            }
            rest=apple- 8 * (--bag8);
        }
        return bag6==-1?-1:bag6+bag8;
    }
    /*
    * 达标法
    * 根据结果的分布规律
    * 编写代码（可以得到最优解）
    * */
    public static int minBagsAwesome(int apple){
        if ((apple&1) !=0){//奇数直接返回
            return -1;
        }
        if (apple<18){
            return (apple==6 || apple==8)?1:
                            (apple==2 || apple==4 || apple==10)?-1:
                                    (apple==12 || apple==14 || apple==16)?2:-1;
        }
        return (apple-18)/8+3;
    }

    private static int minBag6(int rest) {
        return rest % 6 !=0 ?-1:rest/6;
    }

    public static void main(String[] args) {
//        System.out.println(minBags2(13));
//        System.out.println(minBags(13));
        for (int i = 0; i <= 100; i++) {
            int v1=minBags(i);
            int v2=minBagsAwesome(i);
            System.out.println(i+" : "+v1+" vs "+ v2);
        }
    }
}

