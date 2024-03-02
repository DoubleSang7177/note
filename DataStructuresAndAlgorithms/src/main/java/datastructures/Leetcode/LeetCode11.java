package datastructures.Leetcode;

public class LeetCode11 {
    public static int maxArea(int[] height) {
        int max=0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i+1; j < height.length; j++) {
                int k= Math.min(height[j], height[i]);
                if((j-i)*k>max){
                    max=(j-i)*k;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1,1}));
    }

    public static int maxArea2(int[] height) {
        int max=0;
        int i=0,j=height.length-1;
        while (i<j){
            max=height[i]<height[j]?
                    Math.max(max,(j-i)*height[i++]):
                    Math.max(max,(j-i)*height[j++]);
        }
        return max;
    }
}
