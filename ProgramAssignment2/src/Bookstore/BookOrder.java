// insert desc of what this file does

package Bookstore;
import java.io.File;
import java.util.Scanner;

public class BookOrder {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        
        // Step 1: Ask the user to enter the file path
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Please enter the file path for orders.csv (without quotes): "); // C:\Users\wsm52\Downloads\orders.csv for me
        String filePath = inputScanner.nextLine();
        
        // Step 2: Read data from the user-provided file path and insert into the tree
        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            
            // Skip the first line (header)
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            // Read and process each line
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(","); // Use tab as the delimiter based on your CSV format

                int orderID = Integer.parseInt(parts[0].trim());
                String bookName = parts[1].trim();
                
                // Assuming tree is already defined somewhere
                tree.insert(orderID, bookName); // Replace this with your actual tree insert code
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
        inputScanner.close();
        
        // Step 3: User menu
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
