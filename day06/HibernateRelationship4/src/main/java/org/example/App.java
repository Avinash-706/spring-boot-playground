package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Choose relationship type:");
        System.out.println("1. Unidirectional ManyToMany");
        System.out.println("2. Bidirectional ManyToMany");
        System.out.print("Enter choice (1 or 2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            org.example.unidirectional.UnidirectionalApp.main(args);
        } else if (choice == 2) {
            org.example.bidirectional.BidirectionalApp.main(args);
        } else {
            System.out.println("Invalid choice!");
        }
        
        scanner.close();
    }
}
