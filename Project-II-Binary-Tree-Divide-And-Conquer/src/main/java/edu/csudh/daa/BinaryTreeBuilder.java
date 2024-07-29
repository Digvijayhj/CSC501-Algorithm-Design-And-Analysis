package edu.csudh.daa;

import java.util.*;

/**
 * Class to construct a binary tree from inorder and postorder traversal lists.
 */
public class BinaryTreeBuilder {

    /**
     * Builds a binary tree from inorder and postorder traversal lists.
     *
     * @param inorder   The inorder traversal list.
     * @param postorder The postorder traversal list.
     * @return The root of the constructed binary tree.
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length != postorder.length) {
            throw new IllegalArgumentException("Invalid input: The lengths of inorder and postorder lists must be the same.");
        }

        // Initial validation of inorder and postorder lists
        if (!isValidTraversal(inorder, postorder)) {
            throw new IllegalArgumentException("Invalid input: The inorder and postorder lists do not form a valid binary tree.");
        }

        Map<Integer, Integer> inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return buildTreeRecursive(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, inorderIndexMap);
    }

    private boolean isValidTraversal(int[] inorder, int[] postorder) {
        return isValidTraversal(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private boolean isValidTraversal(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) {
            return postStart > postEnd;
        }

        // Root value is the last element in postorder
        int rootValue = postorder[postEnd];

        // Find the root in inorder list
        int inorderRootIndex = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootValue) {
                inorderRootIndex = i;
                break;
            }
        }

        if (inorderRootIndex == -1) {
            return false;
        }

        int leftTreeSize = inorderRootIndex - inStart;
        int rightTreeSize = inEnd - inorderRootIndex;

        // Validate left subtree
        Set<Integer> leftInorderSet = new HashSet<>();
        for (int i = inStart; i < inorderRootIndex; i++) {
            leftInorderSet.add(inorder[i]);
        }

        Set<Integer> leftPostorderSet = new HashSet<>();
        for (int i = postStart; i < postStart + leftTreeSize; i++) {
            leftPostorderSet.add(postorder[i]);
        }

        if (!leftInorderSet.equals(leftPostorderSet)) {
            return false;
        }

        // Validate right subtree
        Set<Integer> rightInorderSet = new HashSet<>();
        for (int i = inorderRootIndex + 1; i <= inEnd; i++) {
            rightInorderSet.add(inorder[i]);
        }

        Set<Integer> rightPostorderSet = new HashSet<>();
        for (int i = postStart + leftTreeSize; i < postEnd; i++) {
            rightPostorderSet.add(postorder[i]);
        }

        if (!rightInorderSet.equals(rightPostorderSet)) {
            return false;
        }

        // Recursively validate left and right subtrees
        return isValidTraversal(inorder, inStart, inorderRootIndex - 1, postorder, postStart, postStart + leftTreeSize - 1) &&
                isValidTraversal(inorder, inorderRootIndex + 1, inEnd, postorder, postStart + leftTreeSize, postEnd - 1);
    }

    private TreeNode buildTreeRecursive(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd, Map<Integer, Integer> inorderIndexMap) {
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }

        // Root is the last element in postorder
        int rootValue = postorder[postEnd];
        TreeNode root = new TreeNode(rootValue);

        // Find the index of the root in the inorder list
        int inorderIndex = inorderIndexMap.get(rootValue);

        // Number of nodes in the left subtree
        int leftTreeSize = inorderIndex - inStart;

        // Recursively construct the left and right subtrees
        root.left = buildTreeRecursive(inorder, inStart, inorderIndex - 1, postorder, postStart, postStart + leftTreeSize - 1, inorderIndexMap);
        root.right = buildTreeRecursive(inorder, inorderIndex + 1, inEnd, postorder, postStart + leftTreeSize, postEnd - 1, inorderIndexMap);

        return root;
    }
}
