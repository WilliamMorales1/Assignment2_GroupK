package classNetwork;

import java.util.Scanner;

public class Introduction {
    public static void main(String[] args) {
        SocialNetwork network = new SocialNetwork();
        Scanner scanner = new Scanner(System.in);

        // Get stuff from CSVs, should be similar to assignment 2
        // Code Here
        
        // Choices Menu
        while (true) {
            System.out.println("Choose an operation:\n" +
                    "1. Print network list for a student\n" +
                    "2. Find shortest path between two students\n" +
                    "3. Disconnect a student\n" +
                    "4. Increase wait days\n" +
                    "5. Decrease wait days\n" +
                    "6. Exit");
            int choice = scanner.nextInt();

            if (choice == 6) break;

            switch (choice) {
            	case 1: 
            		// 1. Print network list for a user-selected student A.
                    System.out.println("Enter student ID:");
                    int studentId = scanner.nextInt();
                    List<Integer> networkList = network.getNetworkList(studentId);
                    System.out.println("Network list: " + networkList);
                case 2: 
                	// 2. Find quickest path for A to B in (Dijkstra’s Algorithm). Print both the number of days it
                    // takes to follow this path and the number of nodes on this path.
                    System.out.println("Enter start student ID:");
                    int startId = scanner.nextInt();
                    System.out.println("Enter end student ID:");
                    int endId = scanner.nextInt();
                    Map<Integer, Integer> result = network.findShortestPath(startId, endId);
                    if (result != null && !result.isEmpty()) {
                        int days = result.keySet().iterator().next();
                        int length = result.values().iterator().next();
                        System.out.println("Shortest path: " + days + " days, " + length + " nodes");
                    } else {
                        System.out.println("No path found.");
                    }
                case 3:
                	// 3. “Disconnect” a user-selected student B from A’s network. (remove edge)
                    System.out.println("Enter source student ID:");
                    int fromId = scanner.nextInt();
                    System.out.println("Enter target student ID:");
                    int toId = scanner.nextInt();
                    network.disconnect(fromId, toId);
                    System.out.println("Disconnected successfully.");
                case 4:
                	// 4. Increase number of wait days for user-selected student.
                    System.out.println("Enter student ID:");
                    int studentId = scanner.nextInt();
                    System.out.println("Enter number of days to increase:");
                    int days = scanner.nextInt();
                    network.modifyWaitDays(studentId, days, true);
                    System.out.println("Wait days increased.");
                case 5:
                	// 5. Decrease number of wait days for user-selected student.
                    System.out.println("Enter student ID:");
                    int studentId = scanner.nextInt();
                    System.out.println("Enter number of days to decrease:");
                    int days = scanner.nextInt();
                    network.modifyWaitDays(studentId, days, false);
                    System.out.println("Wait days decreased.");
                case 6:
                	// 6. exit program
                	System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                	System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}