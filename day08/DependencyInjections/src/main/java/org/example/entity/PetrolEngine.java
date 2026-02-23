package org.example.entity;

public class PetrolEngine implements Engine{
    @Override
    public void run(){
        System.out.println("Running with LESS vibrations");
    }
}
