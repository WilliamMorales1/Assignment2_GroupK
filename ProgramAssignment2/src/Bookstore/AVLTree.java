// insert desc of what this file does

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
    private int treeheight;

    // Insert, Delete, Search, Traversal, and Balancing methods go here.
    public void insert(int orderID, String bookName) {
        // Add insertion logic and AVL balancing here
    }
    
    // More methods to implement:
    // - delete
    // - search
    // - inOrderTraversal
    // - findMinOrder (next book)
    // - findMaxOrder (last book)
    // - height and size getters

    // tree rotations - the code he put in class
}