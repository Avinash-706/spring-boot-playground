package org.example.entity;

public class Car {
    // Diesel d;
    // Pertrol p;

    // Loose Coupling (field injection)
    private  Engine engine;

    // setter injection
    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    // constructor injection
    public Car(){

    }

    public Car(Engine engine){
        this.engine = engine;
    }
}
