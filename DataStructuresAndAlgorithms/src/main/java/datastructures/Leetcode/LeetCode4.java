package datastructures.Leetcode;

public class LeetCode4 {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merge = new int[nums1.length + nums2.length];
        int p1 = 0;
        int p2 = 0;
        int index = 0;
        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] < nums2[p2]) {
                merge[index++] = nums1[p1++];
            } else {
                merge[index++] = nums2[p2++];
            }
        }
        if (p1 < nums1.length) {
            System.arraycopy(nums1, p1, merge, index, nums1.length - p1);
        }
        if (p2 < nums2.length) {
            System.arraycopy(nums2, p2, merge, index, nums2.length - p2);
        }
        if (merge.length % 2 == 0) {// 偶数
            return (merge[merge.length / 2] + merge[merge.length / 2 - 1]) / 2.0;
        } else {
            return merge[merge.length / 2];
        }
    }

    public static void main(String[] args){
        findMedianSortedArrays(new int[]{1,3},new int[]{2});
    }
}
