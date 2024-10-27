package Bookstore;

public class AVLTree {
    class Node {
        int orderID;
        String bookName;
        Node left, right;
        int nodeheight;

        Node(int orderID, String bookName) {
            this.orderID = orderID;
            this.bookName = bookName;
            nodeheight = 1;
        }
    }

    private Node root;
    
    // Method to return the height of the tree
    public int getHeight() {
        return height(root);
    }

    // Helper function to get the height of a node
    private int height(Node n) {
        return (n == null) ? 0 : n.nodeheight;
    }
    
    // Count the total number of nodes in the tree
    public int getNodeCount() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // Helper function to get maximum of two integers
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Right rotation (single rotation)
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.nodeheight = max(height(y.left), height(y.right)) + 1;
        x.nodeheight = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotation (single rotation)
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.nodeheight = max(height(x.left), height(x.right)) + 1;
        y.nodeheight = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get balance factor of a node
    private int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    // Insert method to add a new order
    public void insert(int orderID, String bookName) {
        root = insert(root, orderID, bookName);
    }

    private Node insert(Node node, int orderID, String bookName) {
        // Normal BST insert
        if (node == null)
            return new Node(orderID, bookName);

        if (orderID < node.orderID)
            node.left = insert(node.left, orderID, bookName);
        else if (orderID > node.orderID)
            node.right = insert(node.right, orderID, bookName);
        else
            return node; // Duplicate orderID is not allowed

        // Update height of this ancestor node
        node.nodeheight = 1 + max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);

        // If the node becomes unbalanced, there are 4 cases

        // Left Left Case
        if (balance > 1 && orderID < node.left.orderID)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && orderID > node.right.orderID)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && orderID > node.left.orderID) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && orderID < node.right.orderID) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Search method to find a node by Order ID and return the book name
    public String search(int orderID) {
        return search(root, orderID);
    }

    private String search(Node node, int orderID) {
        if (node == null) {
            return null; // Order ID not found
        }
        
        if (orderID < node.orderID) {
            return search(node.left, orderID); // Search left subtree
        } else if (orderID > node.orderID) {
            return search(node.right, orderID); // Search right subtree
        } else {
            return node.bookName; // Order ID found, return book name
        }
    }

    // Delete a node
    public void delete(int orderID) {
        root = delete(root, orderID);
    }

    private Node delete(Node root, int orderID) {
        // Perform standard BST delete
        if (root == null)
            return root;

        // If the order ID to be deleted is smaller than the root's order ID,
        // then it lies in left subtree
        if (orderID < root.orderID)
            root.left = delete(root.left, orderID);

        // If the order ID to be deleted is greater than the root's order ID,
        // then it lies in right subtree
        else if (orderID > root.orderID)
            root.right = delete(root.right, orderID);

        // If the key is same as root's key, then this is the node to be deleted
        else {
            // Node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp;
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                Node temp = findMinOrder(root.right);

                // Copy the inorder successor's data to this node
                root.orderID = temp.orderID;
                root.bookName = temp.bookName;

                // Delete the inorder successor
                root.right = delete(root.right, temp.orderID);
            }
        }

        // If the tree had only one node, then return
        if (root == null)
            return root;

        // Update height of the current node
        root.nodeheight = max(height(root.left), height(root.right)) + 1;

        // Get the balance factor
        int balance = getBalance(root);

        // If the node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Find node with minimum order ID
    public Node findMinOrder() {
        return findMinOrder(root);
    }

    private Node findMinOrder(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Find node with maximum order ID
    public Node findMaxOrder() {
        return findMaxOrder(root);
    }

    private Node findMaxOrder(Node node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    // In-order traversal to print all books in ascending order
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("OrderID: " + node.orderID + ", Book: " + node.bookName);
            inOrderTraversal(node.right);
        }
    }
}
