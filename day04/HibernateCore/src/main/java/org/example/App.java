package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.example.entity.Student;
import org.example.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) {
        
        SessionFactory factory = null;
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Connecting to database...");
            
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
            
            System.out.println("✅ Connected\n");
            
            StudentService service = new StudentService(factory);
            
            boolean running = true;
            while (running) {
                System.out.println("\n--- Student Management ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Student");
                System.out.println("3. View All");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Phone: ");
                        String phone = scanner.nextLine();
                        
                        service.addStudent(name, age, email, phone);
                        break;
                        
                    case 2:
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        
                        Student student = service.getStudent(id);
                        if (student != null) {
                            System.out.println("\nID: " + student.getId());
                            System.out.println("Name: " + student.getName());
                            System.out.println("Age: " + student.getAge());
                            System.out.println("Email: " + student.getEmail());
                            System.out.println("Phone: " + student.getPhone());
                        }
                        break;
                        
                    case 3:
                        List<Student> students = service.getAllStudents();
                        if (students != null && !students.isEmpty()) {
                            System.out.println("\nAll Students:");
                            for (Student s : students) {
                                System.out.println(s.getId() + ". " + s.getName() + " - " + s.getEmail());
                            }
                        }
                        break;
                        
                    case 4:
                        System.out.print("Enter ID: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        
                        Student existing = service.getStudent(updateId);
                        if (existing != null) {
                            System.out.print("New Name (or Enter to skip): ");
                            String newName = scanner.nextLine();
                            System.out.print("New Age (or 0 to skip): ");
                            int newAge = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("New Email (or Enter to skip): ");
                            String newEmail = scanner.nextLine();
                            System.out.print("New Phone (or Enter to skip): ");
                            String newPhone = scanner.nextLine();
                            
                            service.updateStudent(updateId, newName, newAge, newEmail, newPhone);
                        }
                        break;
                        
                    case 5:
                        System.out.print("Enter ID: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        
                        Student toDelete = service.getStudent(deleteId);
                        if (toDelete != null) {
                            System.out.print("Delete " + toDelete.getName() + "? (yes/no): ");
                            String confirm = scanner.nextLine();
                            if (confirm.equalsIgnoreCase("yes")) {
                                service.deleteStudent(deleteId);
                            } else {
                                System.out.println("Cancelled");
                            }
                        }
                        break;
                        
                    case 6:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                        
                    default:
                        System.out.println("Invalid choice");
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            if (factory != null) {
                factory.close();
            }
            scanner.close();
        }
    }
}