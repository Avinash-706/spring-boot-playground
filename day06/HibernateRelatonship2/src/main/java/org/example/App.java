package org.example;

import org.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.example.entity.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // -------- CUSTOMER INPUT --------
        System.out.println("Enter Customer Name:");
        String name = sc.nextLine();

        System.out.println("Enter Customer Age:");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("Enter Customer Email:");
        String email = sc.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAge(age);
        customer.setEmail(email);

        // -------- ORDERS INPUT --------
        System.out.println("How many orders you want to add?");
        int orderCount = sc.nextInt();
        sc.nextLine();

        List<Orders> orderList = new ArrayList<>();

        for (int i = 1; i <= orderCount; i++) {

            System.out.println("Enter Order " + i + " Product Name:");
            String productName = sc.nextLine();

            System.out.println("Enter Order " + i + " Price:");
            double price = sc.nextDouble();
            sc.nextLine();

            Orders order = new Orders();
            order.setProductName(productName);
            order.setPrice(price);

            order.setCustomer(customer);
            orderList.add(order);
        }

        customer.setOrders(orderList);

        // Save Customer (Cascade will save Orders)
        session.persist(customer);

        tx.commit();

        session.close();
        factory.close();
        sc.close();

        System.out.println("Data Saved Successfully ✅");
    }
}
