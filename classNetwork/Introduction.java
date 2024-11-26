package classNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Introduction {
    public static void main(String[] args) {
        boolean Debugging = false;

        Scanner scanner = new Scanner(System.in);
        String[] studentFilePaths = {
            "classNetwork/students.csv",
            "src/classNetwork/students.csv",
            "students.csv"
        };
        String[] networkFilePaths = {
            "classNetwork/network.csv",
            "src/classNetwork/network.csv",
            "network.csv"
        };

        String studentFilePath = null;
        String networkFilePath = null;
        boolean validStudentFile = false;
        boolean validNetworkFile = false;
        int studentPathIndex = 0;
        int networkPathIndex = 0;

        SocialNetwork network = new SocialNetwork();

        // Load students.csv (Enrollment, First Name, Last Name, Wait)
        while (!validStudentFile) {
            if (studentPathIndex < studentFilePaths.length) {
                studentFilePath = studentFilePaths[studentPathIndex];
                studentPathIndex++;
            } else {
                System.out.print("Please enter a valid file path for students.csv (or type 'quit' to end the program): ");
                studentFilePath = scanner.nextLine();
                if ("quit".equalsIgnoreCase(studentFilePath)) {
                    System.out.println("Program ended.");
                    System.exit(0);
                }
            }
        
            try (Scanner fileScanner = new Scanner(new File(studentFilePath))) {
                // Skip the header
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }
        
                // Process students.csv
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(","); // Assuming CSV is comma-separated
        
                    try {
                        int enrollmentNumber = Integer.parseInt(parts[0].trim());
                        String firstName = parts[1].trim();
                        String lastName = parts[2].trim();
                        int waitDays = Integer.parseInt(parts[3].trim());
        
                        // Add student to the network
                        network.addStudent(enrollmentNumber, firstName, lastName, waitDays);
                        if (Debugging) {
                            System.out.println("added student " + firstName + " with waitDays " + waitDays);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line (invalid number format): " + line);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Skipping invalid line (missing fields): " + line);
                    }
                }
                validStudentFile = true;
        
            } catch (FileNotFoundException e) {
                System.out.println("The students.csv file could not be found at: " + studentFilePath + "\nTrying a different path...");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred while reading students.csv: " + e.getMessage());
            }
        } 

        // Load network.csv (Enrollment, First Name, Last Name, Connection 1, Connection 2, Connection 3, Connection 4, Connection 5)
        while (!validNetworkFile) {
            if (networkPathIndex < networkFilePaths.length) {
                networkFilePath = networkFilePaths[networkPathIndex];
                networkPathIndex++;
            } else {
                System.out.print("Please enter a valid file path for network.csv (or type 'quit' to end the program): ");
                networkFilePath = scanner.nextLine();
                if ("quit".equalsIgnoreCase(networkFilePath)) {
                    System.out.println("Program ended.");
                    System.exit(0);
                }
            }
        
            try (Scanner fileScanner = new Scanner(new File(networkFilePath))) {
                // Skip the header
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }
        
                // Process network.csv
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(","); // Assuming CSV is comma-separated
        
                    try {
                        // Parse the student's information
                        int enrollmentNumber = Integer.parseInt(parts[0].trim());
                        String firstName = parts[1].trim();
                        //String lastName = parts[2].trim();

                        // Parse and add connections (columns 3 to 7)
                        for (int i = 3; i < 8; i++) { // Assuming connections are in fixed positions (indices 3 to 7)
                            if (i < parts.length && !parts[i].trim().isEmpty()) {
                                try {
                                    int connectionId = Integer.parseInt(parts[i].trim());
                                    if (enrollmentNumber != connectionId) {
                                        network.addConnection(enrollmentNumber, connectionId);
                                        if (Debugging) {
                                            System.out.println("added connection of " + firstName + " to " + connectionId);
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Skipping invalid connection ID: " + parts[i] + " in line: " + line);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line (invalid number format): " + line);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Skipping invalid line (missing fields): " + line);
                    }
                }
                validNetworkFile = true;
        
            } catch (FileNotFoundException e) {
                System.out.println("The network.csv file could not be found at: " + networkFilePath + "\nTrying a different path...");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred while reading network.csv: " + e.getMessage());
            }
        }

        System.out.println("Files loaded successfully!");

        // Choices Menu
        while (true) {
            System.out.println("\n----- Class Network Management -----\n" +
                    "1. Print network list for a student\n" +
                    "2. Find shortest path between two students\n" +
                    "3. Disconnect a student\n" +
                    "4. Increase wait days\n" +
                    "5. Decrease wait days\n" +
                    "6. Exit\n" +
                    "-----------------------------------");
            int choice = -1; // Initialize choice with a default value
    	try {
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt(); // Try to read an integer choice
    	} catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number between 1 and 6.");
        scanner.nextLine(); // Clear the invalid input from the scanner
        continue; // Skip the rest of the loop and prompt again
    }

            switch (choice) {
                case 1: // print network list for a student
 		int studentId = -1;
		   while (true){
         		try{
				System.out.print("Enter student ID: ");
          			studentId = scanner.nextInt();
            			String firstName = network.getName(studentId);
		   		if (firstName == null){
				 throw new NullPointerException("Student ID : " + studentId + "is not a valid ID number and cannot be found. Please retry.");                                } 
				List<Integer> networkList = network.getNetworkList(studentId);
                                System.out.println(firstName + "\'s Network list: " + networkList);
                                break;}
			catch (InputMismatchException e){
            			System.out.println("Invalid input. Please enter a student ID with NUMBERS ONLY: ");
            			scanner.nextLine();
						}
                        catch(NullPointerException e){
				System.out.println("Student ID '" + studentId + "' is not a valid ID number. Please retry.");
						}
			 } break;
				

                                       
                case 2: // find shortest path
		    int startId = 0;
		    int endId = 0;
		    String startName;
		    String endName;
		   while(true){
			try{
		   	     System.out.println("Enter start student ID:");
                             startId = scanner.nextInt();

                             startName = network.getName(startId);
		               if (startName == null){
			      throw new NullPointerException("Student ID does not exist.");
                                  } 
//for END ID
                              System.out.println("Enter end student ID:");
       		 	      endId = scanner.nextInt(); // Read end student ID
       	 		      endName = network.getName(endId);

        		      if (endName == null) {
            			throw new NullPointerException("End Student ID does not exist.");}
				break;
		    } catch(InputMismatchException e)
		    { 
			System.out.println("Invalid input, please enter NUMBERS ONLY: ");
			scanner.nextLine();
		    }
 		    catch(NullPointerException e)
		    {
                   	 System.out.println("Student ID inputted is not a valid ID number. Please retry.");
		    }
			  }
//FINDS THE SHORTEST PATH NOW
                        Map<Integer, Integer> result = network.findShortestPath(startId, endId);
                    if (result != null && !result.isEmpty()) {
                        int days = result.keySet().iterator().next();
                        int length = result.values().iterator().next();
                        System.out.println("Shortest path from " + startName + " to " + endName + ": " +
                            days + " days, " + length + " nodes");
                    } else {
                        System.out.println("No path found.");
                    }
                    break;
//CASE THREE STARTS HERE: 
                case 3: // disconnect student
		int fromId = 0;
		int toId = 0;
		String fromName = null;
		String toName = null;
		while(true){
		 try{
                    System.out.println("Enter source student ID:");
                    fromId = scanner.nextInt();
                    fromName = network.getName(fromId);
		    if (fromName == null)
		    {
			throw new NullPointerException("This Student ID doesn't exist.");
		     }
                    System.out.println("Enter target student ID:");
                    toId = scanner.nextInt();
                    toName = network.getName(toId);
		   if (toName == null)
		   {
			throw new NullPointerException("This Student ID doesn't exist.");} 
		break;
		  }catch(InputMismatchException e){
			System.out.println("Invalid input, please enter NUMBERS ONLY: ");
			scanner.nextLine();
		  }catch(NullPointerException e){
			System.out.println("This Student ID doesn't exist, please enter a valid ID.");
			scanner.nextLine();
		     }
                  }	
                    network.disconnect(fromId, toId);
                    // rn it will still say "disconnected successfully" even if it did nothing
                    // needs to be fixed so it says something like "connection doesnt exist" if it doesn't
                    System.out.println("Disconnected " + fromName + " from " + toName + " successfully.");
                    break;

//CASE 4 = increase wait days
                case 4: 
		int studentIdDec = 0;
		int daysInc = 0;
		String studentName = null;
			while(true){
		    		try{
                    	System.out.println("Enter student ID:");
                    	studentIdDec = scanner.nextInt();
			studentName = network.getName(studentIdDec);

			if (studentName == null) 
			{ throw new NullPointerException("This student ID doesn't exist.");
			}

                    	System.out.println("Enter number of days to increase:");
                    	daysInc = scanner.nextInt();
			if (daysInc < 0)
			{
			 throw new IllegalArgumentException("Number of days cannot be less than 0! Try again!"); }break; 
			}catch(InputMismatchException e){
				System.out.println("Invalid input, please enter NUMBERS ONLY: "); 
				scanner.nextLine();
			}catch (NullPointerException e){
				System.out.println("Student ID doesn't exist, please try again.");	scanner.nextLine();
			}catch(IllegalArgumentException e){
				System.out.println("Number of days cannot be less than 0! Try again!");
				scanner.nextLine();}
		
		      	 	}
                      		

                    network.modifyWaitDays(studentIdDec, daysInc, true);
                    System.out.println("Wait days increased.");
                    break;
//CASE 5
                case 5: // decrease wait days
		    int stdudentIdDec = 0;
		    int daysDec = 0;
		    String studentName1 = null;

	            while(true){
		    try{
                    	System.out.println("Enter student ID:");
                    	studentIdDec = scanner.nextInt();
			studentName1 = network.getName(studentIdDec);
			if (studentName1 == null){
			throw new NullPointerException("This student ID doesn't exist.");       }

                    	System.out.println("Enter number of days to decrease:");
                    daysDec = scanner.nextInt();
		    if (daysDec < 0){
			throw new IllegalArgumentException("Number of days cannot be less than 0! Try again.");} break;
			} catch(InputMismatchException e){
				System.out.println("Invalid input, please enter NUMBERS ONLY: ");
				scanner.nextLine();
			} catch(NullPointerException e){
				System.out.println("This student ID doesnt exist, please try again");
				scanner.nextLine();
			} catch (IllegalArgumentException e){
				System.out.println("Number of Days cannot be less than 0! Please try again");
				scanner.nextLine();
			}
		}

                    network.modifyWaitDays(studentIdDec, daysDec, false);
                    System.out.println("Wait days decreased.");
                    break;
//CASE 6
                case 6: // exit
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
