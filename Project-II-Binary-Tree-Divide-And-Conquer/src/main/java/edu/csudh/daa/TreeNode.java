package edu.csudh.daa;

/**
 * Represents a Node in a binary tree.
 */
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
        left = null;
        right = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTree(this, sb, "", "");
        return sb.toString();
    }

    private void printTree(TreeNode node, StringBuilder sb, String padding, String pointer) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.val);
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("|  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.right != null) ? "├──" : "└──";

            printTree(node.left, sb, paddingForBoth, pointerForLeft);
            printTree(node.right, sb, paddingForBoth, pointerForRight);
        }
    }
}
