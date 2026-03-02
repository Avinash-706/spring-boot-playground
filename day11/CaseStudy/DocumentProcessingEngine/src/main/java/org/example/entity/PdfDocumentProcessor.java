package org.example.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PdfDocumentProcessor implements DocumentProcessor {
    
    public PdfDocumentProcessor() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
    
    @Override
    public void processDocument(String documentName) {
        System.out.println("Processing PDF document: " + documentName + " using " + this.getClass().getSimpleName());
    }
}
