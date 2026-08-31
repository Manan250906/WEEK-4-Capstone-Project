import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 * Console-based entry point for the Student Management System.
 * Provides a menu to Add, Update, Delete, and View student records.
 */
public class Main {

    private static final StudentDAO studentDAO = new StudentDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM (JDBC+MySQL) ");
        System.out.println("=========================================");

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    viewAllStudents();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    viewStudentById();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    DBConnection.closeConnection();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. View Student by ID");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // ---------- ADD ----------
    private static void addStudent() {
        System.out.println("\n-- Add New Student --");
        String name = readNonEmptyString("Name: ");
        int age = readInt("Age: ");
        String course = readNonEmptyString("Course: ");
        String email = readNonEmptyString("Email: ");
        String phone = readNonEmptyString("Phone: ");

        Student student = new Student(name, age, course, email, phone);
        boolean success = studentDAO.addStudent(student);

        if (success) {
            System.out.println("Student added successfully with ID: " + student.getId());
        } else {
            System.out.println("Failed to add student.");
        }
    }

    // ---------- VIEW ALL ----------
    private static void viewAllStudents() {
        System.out.println("\n-- All Students --");
        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        printTableHeader();
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // ---------- VIEW BY ID ----------
    private static void viewStudentById() {
        int id = readInt("\nEnter Student ID: ");
        Student student = studentDAO.getStudentById(id);

        if (student == null) {
            System.out.println("No student found with ID: " + id);
        } else {
            printTableHeader();
            System.out.println(student);
        }
    }

    // ---------- UPDATE ----------
    private static void updateStudent() {
        int id = readInt("\nEnter ID of student to update: ");
        Student existing = studentDAO.getStudentById(id);

        if (existing == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }

        System.out.println("Leave field blank to keep current value.");

        System.out.print("Name [" + existing.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) existing.setName(name);

        System.out.print("Age [" + existing.getAge() + "]: ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                existing.setAge(Integer.parseInt(ageStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid age, keeping previous value.");
            }
        }

        System.out.print("Course [" + existing.getCourse() + "]: ");
        String course = scanner.nextLine().trim();
        if (!course.isEmpty()) existing.setCourse(course);

        System.out.print("Email [" + existing.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) existing.setEmail(email);

        System.out.print("Phone [" + existing.getPhone() + "]: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) existing.setPhone(phone);

        boolean success = studentDAO.updateStudent(existing);
        System.out.println(success ? "Student updated successfully." : "Failed to update student.");
    }

    // ---------- DELETE ----------
    private static void deleteStudent() {
        int id = readInt("\nEnter ID of student to delete: ");
        Student existing = studentDAO.getStudentById(id);

        if (existing == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }

        System.out.print("Are you sure you want to delete '" + existing.getName() + "'? (y/n): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("y")) {
            boolean success = studentDAO.deleteStudent(id);
            System.out.println(success ? "Student deleted successfully." : "Failed to delete student.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ---------- Input helpers ----------
    private static String readNonEmptyString(String prompt) {
        String value;
        do {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
        } while (value.isEmpty());
        return value;
    }

    private static int readInt(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return value;
    }

    private static void printTableHeader() {
        System.out.printf("%-5s %-20s %-5s %-20s %-25s %-15s%n",
                "ID", "Name", "Age", "Course", "Email", "Phone");
        System.out.println("---------------------------------------------------------------------------------");
    }
}
