package datastructures.Leetcode;
import java.util.*;
import java.util.function.Consumer;

public class LeetCode15 {
    public static List<List<Integer>> threeSum(int[] nums) {
        nums=Arrays.stream(nums).sorted().toArray();
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
        List<List<Integer>> result=new ArrayList<>();
        for (int i = 0; i<nums.length-1&&nums[i] <= 0; i++) {
            for (int j = i+1; j < nums.length; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    if(nums[i]+nums[j]+nums[k]==0){
                        List<Integer> list=new ArrayList<>();
                        Collections.addAll(list,nums[i],nums[j],nums[k]);
                        list.sort(Comparator.comparingInt(o -> o));
                        if(!result.contains(list)){
                            result.add(list);
                        }
                    }
                }
            }
        }

        return result;

    }

    public static void main(String[] args) {
        List<List<Integer>> list = threeSum2(new int[]{0,0,0});
        list.forEach(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) {
                System.out.println(integers);
            }
        });

    }

    public static List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result=new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]>0) break;
            if (i>0&&nums[i]==nums[i-1]) continue;
            int l=i+1;
            int r=nums.length-1;
            while (l<r){
                int sum=nums[i]+nums[l]+nums[r];
                if(sum<0){
                    l++;
                }else if(sum>0){
                    r--;
                }else{
                    result.add(Arrays.asList(nums[i],nums[l],nums[r]));
                    while (r<l && nums[l]==nums[l+1]) l++;
                    while (r<l && nums[r]==nums[r-1]) r--;
                    l++;
                    r--;
                }
            }
        }


        return result;
    }
}
