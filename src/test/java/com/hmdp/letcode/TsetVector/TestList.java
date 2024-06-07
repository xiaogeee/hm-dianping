package com.hmdp.letcode.TsetVector;

import java.util.*;

public class TestList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Integer set = list.set(1, 4);
        System.out.println(set);
        System.out.println(list.toString());
    }
}
