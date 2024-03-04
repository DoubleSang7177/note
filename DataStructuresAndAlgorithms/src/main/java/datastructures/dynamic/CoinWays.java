package datastructures.dynamic;

public class CoinWays {
    public static int way1(int[] arr, int aim){
        return process(arr,0,aim);
    }

    public static int process(int[] arr,int index,int res){
        if (index==arr.length){
            return res==0?1:0;
        }
        int sum=0;
        for (int count = 0; count * arr[index] <= res; count++) {
            sum+=process(arr,index+1,res-arr[index]*count);
        }
        return sum;
    }

    public static int way2(int[] arr,int aim){
        if (arr==null || arr.length==0){
            return 0;
        }
        int n=arr.length;
        int[][] dp=new int[n+1][aim+1];
        dp[n][0]=1;
        for (int index = n-1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int sum=0;
                for (int count = 0; count*arr[index] <= rest; count++) {
                    sum+=dp[index+1][rest -arr[index]*count];
                }
                dp[index][rest]=sum;

            }
        }
        return dp[0][aim];
    }
    public static int way3(int[] arr,int aim){
        if (arr==null || arr.length==0){
            return 0;
        }
        int n=arr.length;
        int[][] dp=new int[n+1][aim+1];
        dp[n][0]=1;
        for (int index = n-1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest]+=dp[index+1][rest];
                if (rest-arr[index]>=0) {
                    dp[index][rest]+=dp[index][rest-arr[index]];
                }
            }
        }
        return dp[0][aim];
    }

    public static int[] generateRandomArray(int len,int max){
        int[] array = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i]= (int) (Math.random()*max)+1;
        }
        return array;
    }
    public static void main(String[] args) {
        System.out.println(process(new int[]{1, 23, 4}, 0, 100));
        int testTime=10000;
        int len=10;
        int max=10;
        for (int i = 0; i < testTime; i++) {
            int[] array = generateRandomArray(len, max);
            int aim = (int) (Math.random() * 3 * max) + max;
            if (way1(array,aim)!=way2(array,aim)||way2(array,aim)!=way3(array,aim)){
                System.out.println("oops!");
                break;
            }

        }
    }
}
