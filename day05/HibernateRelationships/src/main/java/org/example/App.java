package org.example;

import org.example.entity.Passport;
import org.example.entity.Person;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        System.out.println("\n========================================");
        System.out.println("  Hibernate One-to-One Relationship Demo");
        System.out.println("========================================\n");
        
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Create Person with Passport");
            System.out.println("2. View All Persons");
            System.out.println("3. Update Person");
            System.out.println("4. Delete Person");
            System.out.println("5. Search by Passport Number");
            System.out.println("6. Exit");
            System.out.print("\nChoose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: createPerson(); break;
                case 2: viewAllPersons(); break;
                case 3: updatePerson(); break;
                case 4: deletePerson(); break;
                case 5: searchByPassportNumber(); break;
                case 6: 
                    running = false;
                    System.out.println("\nGoodbye!");
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        }
        
        HibernateUtil.shutdown();
        scanner.close();
    }
    
    // CREATE Operation
    private static void createPerson() {
        Session session = null;
        Transaction tx = null;
        
        try {
            System.out.println("\n--- Create Person with Passport ---");
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter Country: ");
            String country = scanner.nextLine();
            
            System.out.print("Enter Issue Date (YYYY-MM-DD): ");
            LocalDate issueDate = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
            LocalDate expiryDate = LocalDate.parse(scanner.nextLine());
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            
            // Create entities
            Person person = new Person(name, email, age);
            Passport passport = new Passport(country, issueDate, expiryDate);
            
            // Establish relationship
            person.setPassport(passport);
            
            // Save - passport number generated automatically by @GenericGenerator
            session.save(person);
            
            tx.commit();
            
            System.out.println("\n✓ Success! Person and Passport created.");
            System.out.println("  Person ID: " + person.getPersonId() + " (@SequenceGenerator)");
            System.out.println("  Passport Number: " + passport.getPassportNumber() + " (@PrePersist auto-generated)");
            System.out.println("\n  Annotations Used:");
            System.out.println("  - @SequenceGenerator for Person ID");
            System.out.println("  - @PrePersist for Passport Number generation");
            System.out.println("  - CASCADE saves Passport automatically");
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
    
    // READ Operation
    private static void viewAllPersons() {
        Session session = null;
        
        try {
            System.out.println("\n--- All Persons ---");
            
            session = HibernateUtil.getSessionFactory().openSession();
            List<Person> persons = session.createQuery("FROM Person", Person.class).list();
            
            if (persons.isEmpty()) {
                System.out.println("No persons found.");
                return;
            }
            
            System.out.println("Total: " + persons.size() + " person(s)\n");
            
            for (Person p : persons) {
                System.out.println("ID: " + p.getPersonId());
                System.out.println("Name: " + p.getName());
                System.out.println("Email: " + p.getEmail());
                System.out.println("Age: " + p.getAge());
                
                if (p.getPassport() != null) {
                    Passport pass = p.getPassport();
                    System.out.println("\nPassport Details:");
                    System.out.println("  Number: " + pass.getPassportNumber());
                    System.out.println("  Country: " + pass.getCountry());
                    System.out.println("  Issue: " + pass.getIssueDate());
                    System.out.println("  Expiry: " + pass.getExpiryDate());
                }
                System.out.println("---");
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
    
    // UPDATE Operation
    private static void updatePerson() {
        Session session = null;
        Transaction tx = null;
        
        try {
            System.out.println("\n--- Update Person ---");
            
            System.out.print("Enter Person ID: ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            
            Person person = session.get(Person.class, id);
            
            if (person == null) {
                System.out.println("Person not found!");
                return;
            }
            
            System.out.println("Current: " + person.getName() + ", " + person.getEmail());
            
            System.out.print("New Name (Enter to skip): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) person.setName(name);
            
            System.out.print("New Email (Enter to skip): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) person.setEmail(email);
            
            session.update(person);
            tx.commit();
            
            System.out.println("\n✓ Person updated successfully!");
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
    
    // DELETE Operation
    private static void deletePerson() {
        Session session = null;
        Transaction tx = null;
        
        try {
            System.out.println("\n--- Delete Person ---");
            
            System.out.print("Enter Person ID: ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            
            Person person = session.get(Person.class, id);
            
            if (person == null) {
                System.out.println("Person not found!");
                return;
            }
            
            String name = person.getName();
            boolean hasPassport = person.getPassport() != null;
            
            System.out.print("Delete " + name + "? (yes/no): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("yes")) {
                session.delete(person);
                tx.commit();
                
                System.out.println("\n✓ Person deleted!");
                if (hasPassport) {
                    System.out.println("  Passport also deleted (CASCADE effect)");
                }
            } else {
                tx.rollback();
                System.out.println("Cancelled.");
            }
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
    
    // SEARCH by Passport Number
    private static void searchByPassportNumber() {
        Session session = null;
        
        try {
            System.out.println("\n--- Search by Passport Number ---");
            
            System.out.print("Enter Passport Number (e.g., PASS-A1B2C3D4): ");
            String passportNumber = scanner.nextLine();
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            // HQL query to find passport by number
            String hql = "FROM Passport WHERE passportNumber = :number";
            Passport passport = session.createQuery(hql, Passport.class)
                                      .setParameter("number", passportNumber)
                                      .uniqueResult();
            
            if (passport == null) {
                System.out.println("✗ Passport not found!");
                return;
            }
            
            System.out.println("\n✓ Passport Found!");
            System.out.println("  Passport Number: " + passport.getPassportNumber());
            System.out.println("  Country: " + passport.getCountry());
            System.out.println("  Issue Date: " + passport.getIssueDate());
            System.out.println("  Expiry Date: " + passport.getExpiryDate());
            
            if (passport.getPerson() != null) {
                Person person = passport.getPerson();
                System.out.println("\n  Person Details:");
                System.out.println("    ID: " + person.getPersonId());
                System.out.println("    Name: " + person.getName());
                System.out.println("    Email: " + person.getEmail());
                System.out.println("    Age: " + person.getAge());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}
