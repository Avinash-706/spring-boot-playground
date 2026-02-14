package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.example.entity.Student;

import java.util.Scanner;


public class App {
    public static void createStudent(SessionFactory factory){
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            Student student = new Student();

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the name : ");
            String inputName = sc.next();
            System.out.print("Enter the age : ");
            int inputAge = sc.nextInt();

            student.setName(inputName);
            student.setAge(inputAge);
            session.persist(student);

            transaction.commit();
            System.out.println(" Create: Student Saved - " + student);
        }
        catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        createStudent(factory);
    }
}
