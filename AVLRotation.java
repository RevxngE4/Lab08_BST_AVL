import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Node {
    int key;
    int height;
    Node left;
    Node right;

    Node(int key) {
        this.key = key;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

class AVLTree {
    Node root;

    int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    Node rotateLeft(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node rotateRight(Node x) {
        Node y = x.left;
        Node T2 = y.right;

        y.right = x;
        x.left = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

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
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }

        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }

        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
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

public class AVLRotation {
    public static void main(String[] args) {
        String fileName = "src\\input2.txt";

        AVLTree tree = new AVLTree();

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

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tree.root = tree.rotateLeft(tree.root);

        tree.inOrderTraversal(tree.root);
    }
}