package org.example.unidirectional;

import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;

public class UnidirectionalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n========================================");
        System.out.println("   UNIDIRECTIONAL ManyToMany Example");
        System.out.println("========================================");
        System.out.println("Navigation: Student -> Course (ONE WAY)");
        System.out.println("Course does NOT know about Student\n");
        
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        
        System.out.print("Enter course title: ");
        String courseTitle = scanner.nextLine();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            Student student = new Student(studentName);
            Course course = new Course(courseTitle);
            
            // UNIDIRECTIONAL: Only add course to student
            student.getCourses().add(course);
            
            session.persist(student);
            session.persist(course);
            
            transaction.commit()x   ;
            
            System.out.println("\n--- Saving Data ---");
            System.out.println("✓ Student saved with " + student.getCourses().size() + " course(s)");
            System.out.println("✓ Course saved (no student reference)");
            
            // Fetch and demonstrate navigation
            session = HibernateUtil.getSessionFactory().openSession();
            
            Student fetchedStudent = session.get(Student.class, student.getId());
            Course fetchedCourse = session.get(Course.class, course.getId());
            
            System.out.println("\n--- Testing Navigation ---");
            System.out.println("FROM Student -> Course:");
            System.out.println("  Student '" + fetchedStudent.getName() + "' enrolled in:");
            fetchedStudent.getCourses().forEach(c -> 
                System.out.println("    - " + c.getTitle())
            );
            
            System.out.println("\nFROM Course -> Student:");
            System.out.println("  Course '" + fetchedCourse.getTitle() + "' has students:");
            System.out.println("    ✗ CANNOT ACCESS - No reference exists!");
            System.out.println("    (Course class has no 'students' field)");
            
            session.close();
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            scanner.close();
            HibernateUtil.shutdown();
        }
    }
}
