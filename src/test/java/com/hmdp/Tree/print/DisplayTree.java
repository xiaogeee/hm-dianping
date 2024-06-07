package com.hmdp.Tree.print;

import com.hmdp.Tree.TreeNode;

import javax.swing.*;
import java.awt.*;

public class DisplayTree {

    public static void showTree(TreeNode root) {
        JFrame frame = new JFrame("Binary Tree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreePanel(root));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    static class TreePanel extends JPanel {
        private final TreeNode root;
        private final int NODE_SIZE = 30;
        private final int LEVEL_HEIGHT = 60;

        public TreePanel(TreeNode root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) {
                drawTree(g, root, getWidth() / 2, 30, getWidth() / 4);
            }
        }

        private void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
            g.setColor(Color.BLACK);
            g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(node.getVal()), x - 5, y + 5);

            if (node.getLeft() != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y, x - xOffset, y + LEVEL_HEIGHT);
                drawTree(g, node.getLeft(), x - xOffset, y + LEVEL_HEIGHT, xOffset / 2);
            }
            if (node.getRight() != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x, y, x + xOffset, y + LEVEL_HEIGHT);
                drawTree(g, node.getRight(), x + xOffset, y + LEVEL_HEIGHT, xOffset / 2);
            }
        }
    }
}
