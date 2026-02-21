package org.example.unidirectional;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;
import java.util.List;

public class UnidirectionalDemo {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Unidirectional Many-to-One Relationship Demo ===");
        System.out.println("Department IDs: Auto-generated (10, 20, 30...)");
        System.out.println("Employee IDs: Auto-generated (101, 102, 103...)\n");
        
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Create Department");
            System.out.println("2. Create Employee");
            System.out.println("3. Display All Departments");
            System.out.println("4. Display All Employees");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    createDepartment();
                    break;
                case 2:
                    createEmployee();
                    break;
                case 3:
                    displayDepartments();
                    break;
                case 4:
                    displayEmployees();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    HibernateUtil.shutdown();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    private static void createDepartment() {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            System.out.print("Enter Department Name: ");
            String deptName = scanner.nextLine();
            
            Department department = new Department(deptName);
            
            session.save(department);
            transaction.commit();
            
            System.out.println("✓ Department created successfully with ID: " + department.getId());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error creating department: " + e.getMessage());
        }
    }
    
    private static void createEmployee() {
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            System.out.print("Enter Employee Name: ");
            String empName = scanner.nextLine();
            
            // Display available departments
            List<Department> departments = session.createQuery("from Department", Department.class).list();
            
            if (departments.isEmpty()) {
                System.out.println("No departments available. Please create a department first.");
                return;
            }
            
            System.out.println("\nAvailable Departments:");
            for (Department dept : departments) {
                System.out.println("  ID: " + dept.getId() + " - " + dept.getDeptname());
            }
            
            System.out.print("Enter Department ID to assign: ");
            Long deptId = scanner.nextLong();
            scanner.nextLine(); // consume newline
            
            Department department = session.get(Department.class, deptId);
            
            if (department == null) {
                System.out.println("Department with ID " + deptId + " not found!");
                return;
            }
            
            Employee employee = new Employee(empName);
            employee.setDepartment(department);
            
            session.save(employee);
            transaction.commit();
            
            System.out.println("✓ Employee created successfully with ID: " + employee.getId());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error creating employee: " + e.getMessage());
        }
    }
    
    private static void displayDepartments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n=== All Departments ===");
            List<Department> departments = session.createQuery("from Department", Department.class).list();
            
            if (departments.isEmpty()) {
                System.out.println("No departments found.");
            } else {
                for (Department dept : departments) {
                    System.out.println(dept);
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving departments: " + e.getMessage());
        }
    }
    
    private static void displayEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n=== All Employees ===");
            List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
            
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                for (Employee emp : employees) {
                    System.out.println(emp);
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
    }
}
