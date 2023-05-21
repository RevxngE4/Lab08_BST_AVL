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

class AVLTree {
    Node root;

    Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else {
            node.right = insert(node.right, key);
        }

        return node;
    }

    Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    Node delete(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = delete(root.left, key);
        } else if (key > root.key) {
            root.right = delete(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            Node temp = minValueNode(root.right);
            root.key = temp.key;
            root.right = delete(root.right, temp.key);
        }

        return root;
    }

    void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.key + " " + (node.left != null ? node.left.key : 0) + " "
                    + (node.right != null ? node.right.key : 0));
            inOrderTraversal(node.right);
        }
    }
}

public class AVLDeletion {
    public static void main(String[] args) {
        String fileName = "src\\input4.txt";

        AVLTree tree = new AVLTree();
        int deleteKey = 0;

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {
                String[] line = scanner.nextLine().split(" ");
                int key = Integer.parseInt(line[0]);

                tree.root = tree.insert(tree.root, key);

                if (i == n - 1) {
                    deleteKey = Integer.parseInt(line[0]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tree.root = tree.delete(tree.root, deleteKey);

        tree.inOrderTraversal(tree.root);
    }
}
