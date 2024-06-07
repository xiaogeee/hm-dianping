package com.hmdp.redisTest;

import org.junit.jupiter.api.Test;

import java.util.*;

public class test2 {
    @Test
    public void mySqrt() {
        int x = 2147395599;
        //if(x == 0 || x == 1) return x;
        int left = 1, right = x / 2;
        while(left <= right){
            int mid = (left + right) / 2;
            long square = (long) mid * mid;
            if(square > x){
                right = mid - 1;
            }else if(square < x){
                left = mid + 1;
            }else{
                System.out.println(mid);
            }
        }
        System.out.println(right);
    }
    /**
     * 给定 s 和 t 两个字符串，当它们分别被输入到空白的文本编辑器后，如果两者相等，返回 true 。# 代表退格字符。
     *
     * 注意：如果对空文本输入退格字符，文本继续为空。
     */
    @Test
    public void backspaceCompare() {
        String s = "ab#c";
        String t = "ad#c";
        System.out.println(build(s).equals(build(t)));

    }
    private String build(String s){
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()){
            if(c != '#'){
                sb.append(c);
            }else if(sb.length() > 0){
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    /**
     * 输入：fruits = [1,2,1]
     * 输出：3
     * 解释：可以采摘全部 3 棵树。
     * fruits = [3,3,3,1,2,1,1,2,3,3,4]
     */
    @Test
    public void totalFruit() {
        int[] fruits = {3,3,3,1,2,1,1,2,3,3,4};
        int res = Integer.MIN_VALUE;
        int left = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for(int right = 0; right < fruits.length; right++){
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);
            while(map.size() > 2){
                map.put(fruits[left], map.get(fruits[left]) - 1);
                if(map.get(fruits[left]) == 0){
                    map.remove(fruits[left]);
                }
                left++;
            }
            res = Math.max(res, right - left + 1);
        }
        System.out.println(res);
    }

    @Test
    public void minWindow() {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        Map<Character, Integer> t_map = new HashMap<>();
        for(char c : t.toCharArray()){
            t_map.put(c, t_map.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;
        int res = Integer.MAX_VALUE;
        int count = t.length();
        int start = 0;
        while (right < s.length()) {
            char c = s.charAt(right);
            if (t_map.containsKey(c)) {
                int count_cur = t_map.get(c);
                if (count_cur > 0) {
                    count--;
                }
                t_map.put(c,count_cur - 1);
            }
            right++;
            while ( count == 0) {
                if (right - left < res) {
                    res = right - left;
                    start = left;
                }
                char c_left = s.charAt(left);
                if (t_map.containsKey(c_left)) {
                    int count_left = t_map.get(c_left);
                    if(count_left == 0){
                        count++;
                    }
                    t_map.put(c_left, count_left + 1);
                }
                left++;
            }
        }
        System.out.println(res == Integer.MAX_VALUE ? "" : s.substring(start, start + res));
    }

    /**
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     */
    @Test
    public void spiralOrder() {
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        List<Integer> res = new ArrayList<>();
        int n = matrix[0].length;
        int m = matrix.length;
        int left = 0, right = n - 1;
        int top = 0, bottom = m - 1;
        int i, j;
        //考虑初始left = right和top = bottom的情况
        if (left == right) {
            for (i = top; i <= bottom; i++) {
                res.add(matrix[i][left]);
            }
            System.out.println(res);
        }
        if (top == bottom) {
            for (j = left; j <= right; j++) {
                res.add(matrix[top][j]);
            }
            System.out.println(res);
        }
        while (left < right && top < bottom) {
            for (j = left; j < right; j++) {
                res.add(matrix[top][j]);
            }
            for (i = top; i < bottom; i++) {
                res.add(matrix[i][j]);
            }
            for (; j > left; j--) {
                res.add(matrix[i][j]);
            }
            for (; i > top; i--) {
                res.add(matrix[i][j]);
            }
            left++;
            right--;
            top++;
            bottom--;
            //增加边界处理条件
            if (left == right && top == bottom) {
                res.add(matrix[top][left]);
            } else if (left == right) {
                for (i = top; i <= bottom; i++) {
                    res.add(matrix[i][left]);
                }
            } else if (top == bottom) {
                for (j = left; j <= right; j++) {
                    res.add(matrix[top][j]);
                }
            }
        }
        System.out.println(res);
    }


}
