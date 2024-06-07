package com.hmdp.Tree.BST;


import com.hmdp.Tree.TreeNode;
import lombok.val;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 根据有序数组构建平衡二叉树
        int[] nums = {-10, -3, 0, 5, 9};
        TreeNode root = sortedArrayToBST(nums, 0, nums.length);

        // 根据二叉树根节点遍历二叉树
        System.out.println("-----迭代方法中序遍历------");
        List<Integer> res = inorder(root);
        System.out.println(res.toString());

        System.out.println("-----递归方法中序遍历------");

        // 递归方法中序遍历二叉树
        res.clear();
        inorder(root, res);
        System.out.println(res);

        // 迭代后序遍历二叉树
        System.out.println("-----递归方法后序遍历------");
        res.clear();
        res = postOrder(root);

    }

    private static List<Integer> postOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if(node != null) {
                stack.pop();
                stack.push(node);
                stack.push(null);
                if(node.getRight() != null) {
                    stack.push(node.getRight());
                }
                if(node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
            } else {
                stack.pop();
                node = stack.pop();
                res.add(node.getVal());
            }
        }
        return res;
    }

    private static void inorder(TreeNode root, List<Integer> result) {
        if(root == null) return;
        inorder(root.getLeft(), result);
        result.add(root.getVal());
        inorder(root.getRight(), result);
    }

    private static List<Integer> inorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if(node != null) {
                stack.pop();
                if(node.getRight() != null) {
                    stack.push(node.getRight());
                }
                stack.push(node);
                stack.push(null);
                if(node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
            } else {
                stack.pop();
                node = stack.pop();
                res.add(node.getVal());
            }
        }
        return res;
    }

    private static TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if(left >= right) {
            return null;
        }
        if(right - left == 1) {
            return new TreeNode(nums[left]);
        }
        int mid = left + ((right - left) / 2);
        TreeNode root = new TreeNode(nums[mid]);
        root.setLeft(sortedArrayToBST(nums, left, mid));
        root.setRight(sortedArrayToBST(nums, mid + 1, right));
        return root;
    }
}
