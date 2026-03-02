package org.example.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DocumentEngine {
    
    @Autowired
    private StorageService storageService;

    private final DocumentProcessor documentProcessor;
    private AuditService auditService;

    @Autowired
    public DocumentEngine(@Qualifier("xmlDocumentProcessor") DocumentProcessor documentProcessor) {
        this.documentProcessor = documentProcessor;
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
        System.out.println("[Constructor Injection] Injected DocumentProcessor: " + documentProcessor.getClass().getSimpleName());
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
        System.out.println("[Setter Injection] Injected AuditService: " + auditService.getClass().getSimpleName());
    }

    public void processDocument(String documentName) {
        System.out.println("\n-- Document Processing Started --");
        auditService.logBeforeProcessing(documentName);
        documentProcessor.processDocument(documentName);
        storageService.storeDocument(documentName);
        System.out.println("-- Document Processing Completed --\n");
    }
    
    public DocumentProcessor getDocumentProcessor() {
        return documentProcessor;
    }
}
