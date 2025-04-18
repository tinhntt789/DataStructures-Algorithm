package com.Tinhntt.AssignmentStudent;

import java.util.Scanner;

public class StudentMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        // Infinite loop for menu until user chooses to exit
        while (true) {
            // Display menu options
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Student Ranking");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Export Report to TXT File");
            System.out.println("7. Export Student List to CSV");
            System.out.println("8. Exit");
            System.out.print("Choose an option (1-8): ");

            int choice = -1;
            // Input validation: ensure user enters a valid number
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                continue;
            }

            String id, name;
            double score;

            switch (choice) {
                case 1:
                    // Add new student
                    System.out.print("Enter Student ID: ");
                    id = scanner.nextLine();

                    System.out.print("Enter Student Name: ");
                    name = scanner.nextLine();
                    // Validate name (letters and spaces only)
                    if (!name.matches("^[a-zA-Z\\s]+$")) {
                        System.out.println("Invalid name! Name should not contain numbers or special characters.");
                        break;
                    }

                    try {
                        System.out.print("Enter Student Score (0 - 10): ");
                        score = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (score < 0 || score > 10) {
                            System.out.println("Invalid score! Score must be between 0 and 10.");
                            break;
                        }
                        manager.addStudent(id, name, score);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score input! Please enter a valid number (e.g., 8.5)");
                    }
                    break;

                case 2:
                    // Edit existing student by ID
                    System.out.print("Enter Student ID to Edit: ");
                    id = scanner.nextLine();

                    System.out.print("Enter New Name: ");
                    name = scanner.nextLine();
                    if (!name.matches("^[a-zA-Z\\s]+$")) {
                        System.out.println("Invalid name! Name should not contain numbers or special characters.");
                        break;
                    }

                    try {
                        System.out.print("Enter New Score (0 - 10): ");
                        score = Double.parseDouble(scanner.nextLine().replace(",", "."));
                        if (score < 0 || score > 10) {
                            System.out.println("Invalid score! Score must be between 0 and 10.");
                            break;
                        }
                        manager.editStudent(id, name, score);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score input! Please enter a valid number (e.g., 8.5)");
                    }
                    break;

                case 3:
                    // Delete student by ID
                    System.out.print("Enter Student ID to Delete: ");
                    id = scanner.nextLine();
                    manager.deleteStudent(id);
                    break;

                case 4:
                    // View students ranked by score using merge sort
                    System.out.println("\n=== Student Ranking (Sorted by Score - Merge Sort) ===");
                    manager.viewRanking();
                    break;

                case 5:
                    // Search student by ID
                    System.out.print("Enter Student ID to Search: ");
                    id = scanner.nextLine();
                    manager.searchStudent(id);
                    break;

                case 6:
                    // Export report to TXT
                    manager.exportReport();
                    System.out.println("Report has been successfully exported to 'report.txt'.");
                    break;

                case 7:
                    // Export student list to CSV
                    manager.exportToCSV();
                    System.out.println("Student list has been successfully exported to 'students.csv'.");
                    break;

                case 8:
                    // Exit the program
                    System.out.println("Exiting program... Goodbye!");
                    return;

                default:
                    // Handle invalid menu option
                    System.out.println("Invalid option. Please select a number from 1 to 8.");
                    break;
            }
        }
    }
}
