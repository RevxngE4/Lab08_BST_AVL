import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Node {
    int key;
    Node left, right;
    int height;

    public Node(int item) {
        key = item;
        left = right = null;
        height = 1;
    }
    int getBalance() {
        return (left != null ? left.height : 0) - (right != null ? right.height : 0);
    }
}

class AVLTree {
    Node root;

    int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int getBalance(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    Node insert(Node node, int key) {
        if (node == null)
            return (new Node(key));

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
}
public class AVl {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        try {
            File file = new File("src\\input.txt");
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();

            for (int i = 0; i < n; i++) {
                int leftChild = scanner.nextInt();
                int rightChild = scanner.nextInt();
                tree.root = tree.insert(tree.root, i + 1);
            }

            scanner.close();

            printBalances(tree.root);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printBalances(Node node) {
        if (node != null) {
            System.out.println(node.getBalance());
            printBalances(node.left);
            printBalances(node.right);
        }
    }
}