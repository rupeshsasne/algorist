package com.radix2.hr.tree;

import java.util.*;

public class TopView {

    private static class Node {
        Node left;
        Node right;
        int data;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private static class NodeWrapper {
        Node node;
        int horizontalDistance;

        NodeWrapper(Node node, int horizontalDistance) {
            this.node = node;
            this.horizontalDistance = horizontalDistance;
        }
    }

    private static void topView(final Node root) {
        if (root == null) {
            return;
        }

        Queue<NodeWrapper> queue = new LinkedList<>();
        queue.add(new NodeWrapper(root, 0));

        Map<Integer, Node> map = new TreeMap<>();

        while (!queue.isEmpty()) {
            NodeWrapper wrapper = queue.poll();

            if (!map.containsKey(wrapper.horizontalDistance)) {
                map.put(wrapper.horizontalDistance, wrapper.node);
            }

            if (wrapper.node.left != null) {
                queue.add(new NodeWrapper(wrapper.node.left, wrapper.horizontalDistance - 1));
            }

            if (wrapper.node.right != null) {
                queue.add(new NodeWrapper(wrapper.node.right, wrapper.horizontalDistance + 1));
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<Integer, Node> entry : map.entrySet()) {
            if (stringBuilder.length() > 0)
                stringBuilder.append(" ");

            stringBuilder.append(entry.getValue().data);
        }

        System.out.println(stringBuilder.toString());
    }

    private static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        } else {
            Node cur;
            if (data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while (t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        topView(root);
    }
}
