package org.example.driver;

import org.example.entity.Car;
import org.example.entity.DieselEngine;
import org.example.entity.Engine;
import org.example.entity.PetrolEngine;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Petrol Engine");
        System.out.println("2. Diesel Engine");

        System.out.print("Enter Engine choice : ");
        byte userInput = sc.nextByte();
        sc.nextLine();

        // eager inst.
        Car car = new Car();
        Engine engine = null;

        switch (userInput){
            case 1:
                //lazy inst.
                engine = new PetrolEngine();
                break;

            case 2:
                engine = new DieselEngine();
                break;

            default:
                System.out.println("Wrong Input");
                break;
        }

        System.out.println("Great Choice !!");

        // field injection
//        car.engine = engine;
//        car.engine.run();
//        System.out.println(car.engine.getClass());

        // setter injection
//        car.setEngine(engine);
//        car.getEngine().run();
//        System.out.println(car.getEngine().getClass());

        // Constructor Injection
        car = new Car(engine);
        car.getEngine().run();
        System.out.println(car.getEngine().getClass());
    }
}
