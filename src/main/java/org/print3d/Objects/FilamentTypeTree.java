package org.print3d.Objects;

import java.util.*;

public class FilamentTypeTree {
    private TreeNode root;

    private static class TreeNode {
        String category;
        List<TreeNode> children;

        TreeNode(String category) {
            this.category = category;
            this.children = new ArrayList<>();
        }
    }

    public FilamentTypeTree() {
        root = new TreeNode("Filament Types");
    }

    public void addCategory(String parentCategory, String newCategory) {
        TreeNode parentNode = findNode(root, parentCategory);
        if (parentNode != null) {
            parentNode.children.add(new TreeNode(newCategory));
        }
    }

    public void removeCategory(String category) {
        removeNodeRecursive(root, category);
    }

    private boolean removeNodeRecursive(TreeNode currentNode, String category) {
        for (Iterator<TreeNode> iterator = currentNode.children.iterator(); iterator.hasNext();) {
            TreeNode child = iterator.next();
            if (child.category.equals(category)) {
                iterator.remove();
                return true;
            }
            if (removeNodeRecursive(child, category)) {
                return true;
            }
        }
        return false;
    }

    private TreeNode findNode(TreeNode node, String category) {
        if (node.category.equals(category)) {
            return node;
        }
        for (TreeNode child : node.children) {
            TreeNode result = findNode(child, category);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public List<String> getSubcategories(String category) {
        TreeNode node = findNode(root, category);
        if (node != null) {
            List<String> subcategories = new ArrayList<>();
            for (TreeNode child : node.children) {
                subcategories.add(child.category);
            }
            return subcategories;
        }
        return Collections.emptyList();
    }

    public void printTree() {
        printNodeRecursive(root, 0);
    }

    private void printNodeRecursive(TreeNode node, int level) {
        System.out.println("  ".repeat(level) + node.category);
        for (TreeNode child : node.children) {
            printNodeRecursive(child, level + 1);
        }
    }
}