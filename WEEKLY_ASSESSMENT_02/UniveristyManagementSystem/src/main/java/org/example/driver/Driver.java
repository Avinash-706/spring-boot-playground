package org.example.driver;

import org.example.entity.Course;
import org.example.entity.Department;
import org.example.entity.IdCard;
import org.example.entity.Student;
import org.example.utility.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Driver {
    private static Scanner scanner = new Scanner(System.in);
    {
        System.out.println("\n--- Operation Window ---");
    }

    public void takeInput(){
        while (true) {
            System.out.println("1. Add Department");
            System.out.println("2. Add Student");
            System.out.println("3. Add Course");
            System.out.println("4. Enroll Student in Course");
            System.out.println("5. View All Departments");
            System.out.println("6. View All Students");
            System.out.println("7. View All Courses");
            System.out.println("8. View Student Details");
            System.out.println("9. View Department with Students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    enrollStudentInCourse();
                    break;
                case 5:
                    viewAllDepartments();
                    break;
                case 6:
                    viewAllStudents();
                    break;
                case 7:
                    viewAllCourses();
                    break;
                case 8:
                    viewStudentDetails();
                    break;
                case 9:
                    viewDepartmentWithStudents();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    HibernateUtil.shutdown();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }


    private static void addDepartment() {
        Session session = null;
        Transaction transaction = null;

        try {
            System.out.print("Enter Department Name: ");
            String name = scanner.nextLine();

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Department department = new Department(name);
            session.save(department);

            transaction.commit();
            System.out.println("\nDepartment added successfully with ID: " + department.getId() + "\n");
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void addStudent() {
        Session session = null;
        Transaction transaction = null;

        try {
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            System.out.print("Enter Department ID: ");
            Long deptId = scanner.nextLong();
            scanner.nextLine();

            Department department = session.get(Department.class, deptId);
            if (department == null) {
                System.out.println("Department not found!");
                return;
            }

            Student student = new Student(name);
            student.setDepartment(department);
            
            IdCard idCard = new IdCard();
            long cardNum = System.currentTimeMillis() % 1000 + 100;
            idCard.setCardNumber("ID" + cardNum);
            student.setIdCard(idCard);
            
            session.save(student);

            transaction.commit();
            System.out.println("Student added successfully with ID: " + student.getId());
            System.out.println("ID Card generated: " + idCard.getCardNumber() + "\n");
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void addCourse() {
        Session session = null;
        Transaction transaction = null;

        try {
            System.out.print("Enter Course Name: ");
            String name = scanner.nextLine();

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Course course = new Course(name);
            session.save(course);

            transaction.commit();
            System.out.println("Course added successfully with ID: " + course.getId() + "\n");
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void enrollStudentInCourse() {
        Session session = null;
        Transaction transaction = null;

        try {
            System.out.print("Enter Student ID: ");
            Long studentId = scanner.nextLong();

            System.out.print("Enter Course ID: ");
            Long courseId = scanner.nextLong();
            scanner.nextLine();

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            Course course = session.get(Course.class, courseId);

            if (student == null || course == null) {
                System.out.println("Student or Course not found!");
                return;
            }

            student.addCourse(course);
            session.update(student);

            transaction.commit();
            System.out.println("Student enrolled in course successfully!");
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void viewAllDepartments() {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Department> query = session.createQuery("FROM Department", Department.class);
            List<Department> departments = query.list();

            System.out.println("\n--- All Departments ---");
            for (Department dept : departments) {
                System.out.println("ID: " + dept.getId() + " | Name: " + dept.getName());
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void viewAllStudents() {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            List<Student> students = query.list();

            System.out.println("\n===== All Students =====");
            for (Student student : students) {
                String deptName = student.getDepartment() != null ? student.getDepartment().getName() : "N/A";
                System.out.println("ID: " + student.getId() + " | Name: " + student.getName() + " | Department: " + deptName);
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void viewAllCourses() {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery("FROM Course", Course.class);
            List<Course> courses = query.list();

            System.out.println("\n--- All Courses ---");
            for (Course course : courses) {
                System.out.println("ID: " + course.getId() + " | Name: " + course.getCourseName());
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }


    private static void viewStudentDetails() {
        Session session = null;

        try {
            System.out.print("Enter Student ID: ");
            Long studentId = scanner.nextLong();
            scanner.nextLine();

            session = HibernateUtil.getSessionFactory().openSession();
            Student student = session.get(Student.class, studentId);

            if (student == null) {
                System.out.println("Student not found!");
                return;
            }

            System.out.println("\n===== Student Details =====");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());

            if (student.getDepartment() != null) {
                System.out.println("Department: " + student.getDepartment().getName());
            }

            if (student.getIdCard() != null) {
                System.out.println("ID Card: " + student.getIdCard().getCardNumber());
            }

            System.out.println("Enrolled Courses:");
            for (Course course : student.getCourses()) {
                System.out.println("  - " + course.getCourseName());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }

    private static void viewDepartmentWithStudents() {
        Session session = null;

        try {
            System.out.print("Enter Department ID: ");
            Long deptId = scanner.nextLong();
            scanner.nextLine();

            session = HibernateUtil.getSessionFactory().openSession();
            Department department = session.get(Department.class, deptId);

            if (department == null) {
                System.out.println("Department not found!");
                return;
            }

            System.out.println("\n--- Department Details ---");
            System.out.println("ID: " + department.getId());
            System.out.println("Name: " + department.getName());
            
            System.out.println("\nStudents in this Department:");
            List<Student> students = department.getStudents();
            
            if (students.isEmpty()) {
                System.out.println("  No students enrolled in this department.");
            }
            else {
                for (Student student : students) {
                    System.out.println(" ID: " + student.getId() + " | Name: " + student.getName());
                }
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            if (session != null) session.close();
        }
    }

}
