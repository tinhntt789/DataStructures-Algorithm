package com.Tinhntt.AssignmentStudent;

import java.awt.Desktop;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>(); // List to store students
    private HashMap<String, Student> studentMap = new HashMap<>(); // Map for quick lookup by ID
    private List<String> logs = new ArrayList<>(); // Activity log

    // Add a new student to the system
    public void addStudent(String id, String name, double score) {
        if (studentMap.containsKey(id)) {
            System.out.println("Student ID already exists.");
            return;
        }
        Student student = new Student(id, name, score);
        students.add(student);
        studentMap.put(id, student);
        System.out.println("Student added successfully.");
        logs.add("Added student: " + name + " (ID: " + id + "), Score: " + score);
    }

    // Delete a student by ID
    public void deleteStudent(String id) {
        Student student = studentMap.remove(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        students.remove(student);
        System.out.println("Student deleted successfully.");
        logs.add("Deleted student with ID: " + id);
    }

    // Edit student information by ID
    public void editStudent(String id, String name, double score) {
        Student student = studentMap.get(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        student.name = name;
        student.score = score;
        System.out.println("Student updated successfully.");
        logs.add("Updated student: " + name + " (ID: " + id + "), New Score: " + score);
    }

    // View ranking of students sorted by score using merge sort
    public void viewRanking() {
        students = mergeSort(students); // Sort students by score (descending)
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // Merge Sort implementation for sorting students by score in descending order
    private ArrayList<Student> mergeSort(ArrayList<Student> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        ArrayList<Student> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Student> right = new ArrayList<>(list.subList(mid, list.size()));

        return merge(mergeSort(left), mergeSort(right));
    }

    // Merge two sorted lists into one (descending order)
    private ArrayList<Student> merge(ArrayList<Student> left, ArrayList<Student> right) {
        ArrayList<Student> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).score >= right.get(j).score) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    // Search for a student by ID
    public void searchStudent(String id) {
        Student student = studentMap.get(id);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(student);
            logs.add("Searched for student with ID: " + id);
        }
    }

    // Export student report to a .txt file including original list, sorted list, and logs
    public void exportReport() {
        File file = new File("report.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("=== Student Report ===\n");
            writer.write("Generated at: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n");

            writer.write("1. Student List (Original Order):\n");
            for (Student student : students) {
                writer.write("• " + student.name + ": " + student.score + " (" + student.getRank() + ")\n");
            }

            ArrayList<Student> sortedStudents = mergeSort(new ArrayList<>(students));
            writer.write("\n2. Student List (Sorted by Score):\n");
            for (Student student : sortedStudents) {
                writer.write("• " + student.name + ": " + student.score + " (" + student.getRank() + ")\n");
            }

            writer.write("\n3. Activity Logs:\n");
            for (String log : logs) {
                writer.write("• " + log + "\n");
            }

            writer.write("\nReport successfully exported to 'report.txt'\n");
            System.out.println("Report has been successfully exported to 'report.txt'.");
            System.out.println("Path: " + file.getAbsolutePath());
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file); // Automatically open file after export
            }
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }

    // Export student list to CSV file
    public void exportToCSV() {
        File file = new File("students.csv");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("ID,Name,Score,Rank\n");
            for (Student student : students) {
                writer.write(student.id + "," + student.name + "," + student.score + "," + student.getRank() + "\n");
            }
            System.out.println("Student list has been successfully exported to 'students.csv'.");
            System.out.println("Path: " + file.getAbsolutePath());
            logs.add("Exported student list to CSV file.");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file); // Automatically open CSV after export
            }
        } catch (IOException e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
        }
    }
}
