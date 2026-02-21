package org.example.bidirectional;

import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Scanner;

public class BidirectionalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n========================================");
        System.out.println("   BIDIRECTIONAL ManyToMany Example");
        System.out.println("========================================");
        System.out.println("Navigation: Student <-> Course (TWO WAY)");
        System.out.println("Both entities know about each other\n");
        
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
            
            // BIDIRECTIONAL: Set both sides of the relationship
            student.getCourses().add(course);
            course.getStudents().add(student);
            
            session.persist(student);
            session.persist(course);
            
            transaction.commit();
            
            System.out.println("\n--- Saving Data ---");
            System.out.println("✓ Student saved with " + student.getCourses().size() + " course(s)");
            System.out.println("✓ Course saved with " + course.getStudents().size() + " student(s)");
            
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
            fetchedCourse.getStudents().forEach(s -> 
                System.out.println("    - " + s.getName())
            );
            System.out.println("    ✓ SUCCESS - Both directions work!");
            
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
