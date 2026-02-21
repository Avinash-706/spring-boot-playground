package org.example;

import org.example.driver.Driver;
import org.example.utility.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class App 
{
    static{
        System.out.println("-- Welcome to UMS --");
    }
    public static void main( String[] args ) {
        Driver driver = new Driver();
        driver.takeInput();
    }

}
