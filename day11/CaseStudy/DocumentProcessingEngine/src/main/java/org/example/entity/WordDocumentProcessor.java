package org.example.entity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class WordDocumentProcessor implements DocumentProcessor {
    
    public WordDocumentProcessor() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
    
    @Override
    public void processDocument(String documentName) {
        System.out.println("Processing Word document: " + documentName + " using " + this.getClass().getSimpleName());
    }
}
