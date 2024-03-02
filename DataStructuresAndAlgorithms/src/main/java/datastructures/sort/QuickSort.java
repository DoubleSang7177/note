package datastructures.sort;

/*
* 快速排序3.0
* 时间复杂度。。。O(N^2)?
* */
public class QuickSort {
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        long start = System.nanoTime();
        process(arr, 0, arr.length - 1);
        long end = System.nanoTime();
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(end - start);
        long start2 = System.nanoTime();
        process2(arr, 0, arr.length - 1);
        long end2 = System.nanoTime();

        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(end2 - start2);
        System.out.println((end2 - start2) < (end - start));

    }

    public static void process2(int arr[], int left, int right) {
        if (left < right) {
            swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
            int[] points = partition2(arr, left, right);
            process2(arr, left, points[0] - 1);
            process2(arr, points[1] + 1, right);
        }

    }

    public static void process(int arr[], int left, int right) {
        if (left < right) {
            swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
            int[] points = partition(arr, left, right);
            process(arr, left, points[0] - 1);
            process(arr, points[1] + 1, right);
        }
    }

    private static int[] partition2(int[] arr, int left, int right) {
        int l = left - 1;
        int r = right;
        while (left < r) {
            if (arr[left] < arr[right]) {
                swap(arr, ++l, left++);
            } else if (arr[left] > arr[right]) {
                swap(arr, left, --r);
            } else {
                left++;
            }
        }
        swap(arr, r, right);
        return new int[]{l + 1, r};
    }

    private static void swap2(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    private static int[] partition(int[] arr, int left, int right) {
        int l = left;
        int r = right - 1;
        while (left <= r) {//快慢指针，由于left比l快，所以不用判断r是否小于l
            if (arr[left] < arr[right]) {
                swap(arr, l++, left++);
            } else if (arr[left] > arr[right]) {
                swap(arr, left, r--);
            } else {
                left++;
            }
        }
        swap(arr, ++r, right);
        return new int[]{l, r};
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {6, 3, 2, 2, 1, 3, 81, -23, 11, 65, 22, 1, 1, -1, 23, -5, 5, 921, 2, 2, 111, 3, 22, 1, 2, 3, 21, 2, 1, -1, 23, 12, 11};
//        int[] arr={7,9,5,9,5,4,5,0};
        quickSort(arr);
       /* for (int i : arr) {
            System.out.printEdge(i + " ");
        }*/

    }
}
