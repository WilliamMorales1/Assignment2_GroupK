// insert desc of what this file does

package Bookstore;
import java.io.File;
import java.util.Scanner;

public class BookOrder {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        
        // Step 1: Read data from orders.csv and insert into the tree.
        try {
            Scanner scanner = new Scanner(new File("orders.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int orderID = Integer.parseInt(parts[0]);
                String bookName = parts[1];
                tree.insert(orderID, bookName);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
        // Step 2: User menu
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Bookstore Order Management ---");
            System.out.println("1. Add new order");
            System.out.println("2. Fulfill order (remove)");
            System.out.println("3. Print in-order list of book names");
            System.out.println("4. Find book by order ID");
            System.out.println("5. Find oldest order");
            System.out.println("6. Find latest order");
            System.out.println("7. Exit");

            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    // Add order logic
                    break;
                case 2:
                    // Remove order logic
                    break;
                case 3:
                    // Print in-order traversal
                    break;
                case 4:
                    // Search order by ID logic
                    break;
                case 5:
                    // Find oldest order logic
                    break;
                case 6:
                    // Find latest order logic
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            // Print tree metrics: number of nodes and height
            // tree.printMetrics();
        }
    }
}