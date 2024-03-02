package datastructures.Leetcode;

public class LeetCode150 {
    public static int[] twoSum(int[] numbers, int target) {
        int[] arr=new int[2];
       /* int i=0,j=1;
        while (i<numbers.length && j<numbers.length){
            if(numbers[i]+numbers[j]==target){
                arr[0]=i+1;
                arr[1]=j+1;
                break;
            }else{
                if(j==numbers.length-1){
                    i++;
                    j=i+1;
                }else{
                    j++;
                }
            }
        }*/
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i +1; j < numbers.length; j++) {
                if(numbers[i]+numbers[j]==target){
                    arr[0]= i +1;
                    arr[1]= j +1;
                    return arr;
                }
            }
        }
        return arr;
    }
    public static void main(String[] args) {
        int[] ints = twoSum(new int[]{2,3,4,7}, 9);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }

    public static int[] twoSum2(int[] numbers, int target) {
        int i=0;
        int j=numbers.length-1;
        while (i<j){
            int sum = numbers[i] + numbers[j];
            if(sum>target){
                j--;
            } else if (sum < target) {
                i++;
            }else{
                return new int[]{i+1,j+1};
            }
        }
        return new int[]{-1,-1};

    }
}
