import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Node {
    int key;
    Node left;
    Node right;

    Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}

class BinaryTree {
    Node root;

    void insert(int key) {
        root = insertNode(root, key);
    }

    Node insertNode(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insertNode(node.left, key);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key);
        }

        return node;
    }

    void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.key + " " + (node.left != null ? node.left.key : 0) + " " + (node.right != null ? node.right.key : 0));
            inOrderTraversal(node.right);
        }
    }
}

public class InsertionInBinaryTree {
    public static void main(String[] args) {
        String fileName = "src\\input3.txt";

        BinaryTree tree = new BinaryTree();
        int insertKey = 0;

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int n = Integer.parseInt(scanner.nextLine());

            Node[] nodes = new Node[n];

            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i + 1);
            }

            for (int i = 0; i < n; i++) {
                String[] line = scanner.nextLine().split(" ");
                int key = Integer.parseInt(line[0]);
                int leftChild = Integer.parseInt(line[1]);
                int rightChild = Integer.parseInt(line[2]);

                nodes[i].left = (leftChild != 0) ? nodes[leftChild - 1] : null;
                nodes[i].right = (rightChild != 0) ? nodes[rightChild - 1] : null;

                if (i == 0) {
                    tree.root = nodes[i];
                }
            }

            insertKey = Integer.parseInt(scanner.nextLine());

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tree.insert(insertKey);

        tree.inOrderTraversal(tree.root);
    }
}