package datastructures.violentRecursion;

/*
* weights[i]和values[i]分别给代表i号物品的重量和价值，bag表示
* 一个载重bag的袋子，所装物品的重量不能超过这个重量
* */
public class Knapsack {

    public static int process(int[] weights,int[] values,int i,int bag,int usedWeight){
        if (usedWeight>bag){
            return -values[i-1];
        }
        if (i==weights.length){
            return 0;
        }
        //可以装
        return Math.max(values[i]+process(weights,values,i+1,bag,usedWeight+weights[i]),
                process(weights,values,i+1,bag,usedWeight));
    }

    public static int process2(int[] weights,int[] values,int i,int bag,
                               int alreadyWeight,int alreadyValue){
        if (alreadyWeight>bag){
            return 0;
        }
        if (i==weights.length){
            return alreadyValue;
        }
        //可以装
        return Math.max(
                process2(weights,values,i+1,bag,alreadyWeight+weights[i],alreadyValue+values[i]),
                process2(weights,values,i+1,bag,alreadyWeight,alreadyValue)
        );
    }

    public static void main(String[] args) {
        int[] weights={12,13,1,24,56,23};
        int[] values={12,13,15,16,18,120};
        System.out.println(process(weights, values, 0, 130,0));
        System.out.println(process2(weights,values,0,130,0,0));
    }
}
