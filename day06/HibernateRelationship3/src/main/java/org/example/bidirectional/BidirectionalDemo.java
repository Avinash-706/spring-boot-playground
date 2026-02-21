package org.example.bidirectional;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;
import java.util.List;

public class BidirectionalDemo {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Bidirectional Many-to-One Relationship Demo ===");
        System.out.println("Department IDs: Auto-generated (10, 20, 30...)");
        System.out.println("Employee IDs: Auto-generated (101, 102, 103...)\n");
        
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Create Department");
            System.out.println("2. Create Employee");
            System.out.println("3. Display All Departments with Employees");
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
                    displayDepartmentsWithEmployees();
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
            
            DepartmentBi department = new DepartmentBi(deptName);
            
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
            List<DepartmentBi> departments = session.createQuery("from DepartmentBi", DepartmentBi.class).list();
            
            if (departments.isEmpty()) {
                System.out.println("No departments available. Please create a department first.");
                return;
            }
            
            System.out.println("\nAvailable Departments:");
            for (DepartmentBi dept : departments) {
                System.out.println("  ID: " + dept.getId() + " - " + dept.getDeptname());
            }
            
            System.out.print("Enter Department ID to assign: ");
            Long deptId = scanner.nextLong();
            scanner.nextLine(); // consume newline
            
            DepartmentBi department = session.get(DepartmentBi.class, deptId);
            
            if (department == null) {
                System.out.println("Department with ID " + deptId + " not found!");
                transaction.rollback();
                return;
            }
            
            EmployeeBi employee = new EmployeeBi(empName);
            
            // Use helper method to maintain bidirectional relationship
            department.addEmployee(employee);
            
            session.update(department);
            transaction.commit();
            
            System.out.println("✓ Employee created successfully with ID: " + employee.getId());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error creating employee: " + e.getMessage());
        }
    }
    
    private static void displayDepartmentsWithEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n=== All Departments with Employees ===");
            List<DepartmentBi> departments = session.createQuery("from DepartmentBi", DepartmentBi.class).list();
            
            if (departments.isEmpty()) {
                System.out.println("No departments found.");
            } else {
                for (DepartmentBi dept : departments) {
                    System.out.println("\n" + dept);
                    if (dept.getEmployees().isEmpty()) {
                        System.out.println("  No employees in this department.");
                    } else {
                        System.out.println("  Employees:");
                        for (EmployeeBi emp : dept.getEmployees()) {
                            System.out.println("    - ID: " + emp.getId() + ", Name: " + emp.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving departments: " + e.getMessage());
        }
    }
    
    private static void displayEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n=== All Employees ===");
            List<EmployeeBi> employees = session.createQuery("from EmployeeBi", EmployeeBi.class).list();
            
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                for (EmployeeBi emp : employees) {
                    System.out.println(emp);
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
    }
}
