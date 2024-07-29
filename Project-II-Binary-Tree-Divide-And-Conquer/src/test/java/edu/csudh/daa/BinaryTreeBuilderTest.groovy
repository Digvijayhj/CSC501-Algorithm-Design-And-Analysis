package edu.csudh.daa


import spock.lang.Specification
import java.util.logging.Logger
import java.util.stream.IntStream

class BinaryTreeBuilderTest extends Specification {

    private static final Logger logger = Logger.getLogger(BinaryTreeBuilderTest.class.getName())

    def "test buildTree with valid and invalid inputs"() {
        given:
        BinaryTreeBuilder constructor = new BinaryTreeBuilder()
        long startTime = System.currentTimeMillis()

        when:
        TreeNode result = null
        try {
            result = constructor.buildTree(inorder as int[], postorder as int[])
        } catch (Exception e) {
            shouldThrowException = e
            println e.message
        }
        long endTime = System.currentTimeMillis()


        then:

        if(Objects.isNull(shouldThrowException) && Objects.nonNull(result))
            assert isTreeEqual(result, expected)

        logTime(startTime, endTime, "Test with inorder: ${inorder.size()} and postorder: ${postorder.size()}")

        println "Constructed Tree:\n${result}"


        where:
        description                           | inorder               | postorder              | expected                          | shouldThrowException
        "Empty tree"                          | []                    | []                     | null                              | null
        "Single node tree"                    | [1]                   | [1]                    | new TreeNode(1)                   | null
        "Three node tree"                     | [2, 1, 3]             | [2, 3, 1]              | createTree([1, 2, 3])             | null
        "Complex tree"                        | [4, 2, 5, 1, 6, 3, 7] | [4, 5, 2, 6, 7, 3, 1]  | createTree([1, 2, 3, 4, 5, 6, 7]) | null
        "Large tree with 20 nodes"            | generateInorder(20)   | generatePostorder(20)  | expectedTree(20)                  | null
        "Large tree with 50 nodes"            | generateInorder(50)   | generatePostorder(50)  | expectedTree(50)                  | null
        "Large tree with 100 nodes"           | generateInorder(100)  | generatePostorder(100) | expectedTree(100)                 | null
        "Large tree with 200 nodes"           | generateInorder(200)  | generatePostorder(200) | expectedTree(200)                 | null
        "Invalid input - length mismatch"     | [1, 2]                | [1]                    | null                              | IllegalArgumentException
        "Invalid input - extra postorder"     | [1]                   | [1, 2]                 | null                              | IllegalArgumentException
        "Invalid input - invalid order"       | [1, 2, 3]             | [3, 2, 1]              | null                              | IllegalArgumentException
        "Invalid input - incorrect structure" | [2, 1, 3]             | [2, 1, 3]              | null                              | IllegalArgumentException
    }

    boolean isTreeEqual(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true
        if (a == null || b == null) return false
        return a.val == b.val && isTreeEqual(a.left, b.left) && isTreeEqual(a.right, b.right)
    }

    TreeNode createTree(List<Integer> vals) {
        if (vals.isEmpty()) return null
        TreeNode root = new TreeNode(vals[0])
        Queue<TreeNode> queue = new LinkedList<>()
        queue.add(root)
        int i = 1
        while (i < vals.size()) {
            TreeNode curr = queue.poll()
            if (vals[i] != null) {
                curr.left = new TreeNode(vals[i])
                queue.add(curr.left)
            }
            i++
            if (i < vals.size() && vals[i] != null) {
                curr.right = new TreeNode(vals[i])
                queue.add(curr.right)
            }
            i++
        }
        return root
    }

    int[] generateInorder(int n) {
        return IntStream.range(0, n).toArray()
    }

    int[] generatePostorder(int n) {
        return IntStream.range(0, n).boxed().sorted((a, b) -> b - a).mapToInt(Integer::intValue).toArray()
    }

    TreeNode expectedTree(int n) {
        TreeNode root = new TreeNode(0)
        for (int i = 1; i < n; i++) {
            root = insert(root, i)
        }
        return root
    }

    TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val)
        }
        if (val < root.val) {
            root.left = insert(root.left, val)
        } else {
            root.right = insert(root.right, val)
        }
        return root
    }

    void logTime(long startTime, long endTime, String message) {
        long duration = endTime - startTime
        logger.info("${new Date(startTime)}: Start - ${message}")
        logger.info("${new Date(endTime)}: End - ${message}")
        println String.format("Duration:  ${duration}ms")
    }
}