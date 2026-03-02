package org.example.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class XmlDocumentProcessor implements DocumentProcessor {
    
    public XmlDocumentProcessor() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
    
    @Override
    public void processDocument(String documentName) {
        System.out.println("Processing XML document: " + documentName + " using " + this.getClass().getSimpleName());
    }
}
