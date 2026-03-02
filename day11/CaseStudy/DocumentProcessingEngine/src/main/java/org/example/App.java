package org.example;

import org.example.config.AppConfig;
import org.example.entity.DocumentEngine;
import org.example.entity.DocumentProcessor;
import org.example.entity.WordDocumentProcessor;
import org.example.entity.XmlDocumentProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        System.out.println("--- Enterprise Document Processing Engine ---\n");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        //qualifier XML
        System.out.println("\n-- @qualifoer with XmlDocumentProcessor --");
        DocumentEngine engine = context.getBean(DocumentEngine.class);
        System.out.println("DocumentEngine is using: " + engine.getDocumentProcessor().getClass().getSimpleName());
        engine.processDocument("contract.xml");

        // primary PDF
        System.out.println("\n-- @primary (PdfDocumentProcessor) --");
        DocumentProcessor defaultProcessor = context.getBean(DocumentProcessor.class);
        System.out.println("Default processor (without qualifier) is: " + defaultProcessor.getClass().getSimpleName());
        defaultProcessor.processDocument("report.pdf");

        // word lazy
        System.out.println("\n-- @Lazy (WordDocumentProcessor) --");
        WordDocumentProcessor wordProcessor = context.getBean(WordDocumentProcessor.class);
        wordProcessor.processDocument("document.docx");



        System.out.println("\n-- Closing Application Context --");
        ((AnnotationConfigApplicationContext) context).close();
        
        System.out.println("\n-- Application Completed --");
    }
}
