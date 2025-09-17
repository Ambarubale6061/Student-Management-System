1import java.io.*;
import java.util.*;

public class StudentManagementSystem {
    private static List<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadStudents();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Student Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Save & Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> searchStudent(sc);
                case 4 -> updateStudent(sc);
                case 5 -> deleteStudent(sc);
                case 6 -> {
                    saveStudents();
                    System.out.println("Data saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        students.add(new Student(roll, name, course, marks));
        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int roll = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.println("Found: " + s);
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void updateStudent(Scanner sc) {
        System.out.print("Enter Roll No to update: ");
        int roll = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.print("Enter New Name: ");
                s.setName(sc.nextLine());

                System.out.print("Enter New Course: ");
                s.setCourse(sc.nextLine());

                System.out.print("Enter New Marks: ");
                s.setMarks(sc.nextDouble());

                System.out.println("Student updated!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        int roll = sc.nextInt();
        sc.nextLine();

        students.removeIf(s -> s.getRollNo() == roll);
        System.out.println("Student deleted if existed!");
    }

    private static void saveStudents() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                pw.println(s.getRollNo() + "," + s.getName() + "," + s.getCourse() + "," + s.getMarks());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                int roll = Integer.parseInt(data[0]);
                String name = data[1];
                String course = data[2];
                double marks = Double.parseDouble(data[3]);
                students.add(new Student(roll, name, course, marks));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        }
    }
}