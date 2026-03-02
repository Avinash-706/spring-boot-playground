package org.example.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class StorageService {
    
    public StorageService() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
    
    public void storeDocument(String documentName) {
        System.out.println("[STORAGE] Storing document: " + documentName + " using " + this.getClass().getSimpleName());
    }
}
