package org.example.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.entity.Student;

import java.util.List;

public class StudentService {
    
    private SessionFactory factory;
    
    public StudentService(SessionFactory factory) {
        this.factory = factory;
    }
    
    public void addStudent(String name, int age, String email, String phone) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Student student = new Student(name, age, email, phone);
            session.persist(student);
            
            tx.commit();
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Student getStudent(int id) {
        Session session = factory.openSession();
        
        try {
            Student student = session.get(Student.class, id);
            
            if (student == null) {
                System.out.println("❌ Not found");
            }
            
            return student;
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
    
    public List<Student> getAllStudents() {
        Session session = factory.openSession();
        
        try {
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            List<Student> students = query.list();
            
            System.out.println("Total: " + students.size());
            
            return students;
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
    
    public void updateStudent(int id, String name, int age, String email, String phone) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Student student = session.get(Student.class, id);
            
            if (student != null) {
                if (name != null && !name.isEmpty()) {
                    student.setName(name);
                }
                if (age > 0) {
                    student.setAge(age);
                }
                if (email != null && !email.isEmpty()) {
                    student.setEmail(email);
                }
                if (phone != null && !phone.isEmpty()) {
                    student.setPhone(phone);
                }
                
                session.merge(student);
                tx.commit();
                
            } else {
                System.out.println("❌ Not found");
            }
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void deleteStudent(int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Student student = session.get(Student.class, id);
            
            if (student != null) {
                session.remove(student);
                tx.commit();
            } else {
                System.out.println("❌ Not found");
            }
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
