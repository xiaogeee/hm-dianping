package com.hmdp.letcode.lc347;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //输入数组长度n
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        //输入数组
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        //输入k
        int k = sc.nextInt();
        //调用方法
        int[] res = topKFrequent(nums, k);
        //输出结果
        System.out.print("[");
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + " ");
        }
        System.out.print("]");

    }

    private static int[] topKFrequent(int[] nums, int k) {
        int[] res = new int[k];
        //统计每个数字出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            queue.add(new int[]{num, count});
        }
        for (int i = 0; i < k; i++) {
            res[i] = queue.poll()[0];
        }
        return res;

    }
}
