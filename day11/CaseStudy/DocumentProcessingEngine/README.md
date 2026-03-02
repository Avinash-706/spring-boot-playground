# Spring Framework: Document Processing Engine - Deep Dive

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring_7.0.3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Design Patterns](https://img.shields.io/badge/Design_Patterns-FF6B6B?style=for-the-badge&logo=&logoColor=white)

</div>

<hr style="border: 1px solid rgb(98, 117, 187)">

<div align="center">
<table>
<tr>
<td align="center">
<br />
<img src="../../favicon.png" width="150" height="150" alt="Document Processing">
<h3>© 2026 Avinash Dhanuka</h3>
<p>Master Guide: Spring Dependency Injection & Bean Lifecycle</p>
<p><em>Crafted with â¤ï¸ for Enterprise Architecture Mastery</em></p>

<a href="https://github.com/Avinash-706" target="_blank">
<img src="https://img.shields.io/badge/GitHub-Avinash--706-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Profile">
</a>
<br />
<a href="https://mail.google.com/mail/?view=cm&fs=1&to=avunashdhanuka@gmail.com&su=Spring%20DI%20Query&body=â˜•%20Hello%20Avinash,%0D%0A%0D%0AMy%20name%20is%20[Your%20Name]%20and%20I%20have%20a%20doubt%20regarding%20Spring%20DI.%0D%0A%0D%0AðŸ”¹%20Topic:%20[DI/Scopes/Lifecycle]%0D%0A%0D%0AThank%20you!" target="_blank">
<img src="https://img.shields.io/badge/ðŸ“§_Contact_Me_via_Gmail-2563EB?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail">
</a>
<br />
<br />
</td>
</tr>
</table>
</div>

> **Learning Note:** This guide demonstrates Spring Framework's core concepts through a real-world Document Processing Engine. Master dependency injection patterns, bean scopes, lifecycle management, and annotation-based configuration.

> **Prerequisites:** 
> - Understanding of Java OOP concepts
> - Basic knowledge of Spring IoC Container
> - Familiarity with annotations
> - Maven basics

---

## Table of Contents
1. [What is Document Processing Engine?](#1-what-is-document-processing-engine)
2. [Architecture Overview](#2-architecture-overview)
3. [Dependency Injection Patterns](#3-dependency-injection-patterns)
4. [Bean Scopes Deep Dive](#4-bean-scopes-deep-dive)
5. [@Primary vs @Qualifier](#5-primary-vs-qualifier)
6. [Lazy Initialization](#6-lazy-initialization)
7. [Bean Lifecycle Management](#7-bean-lifecycle-management)
8. [Complete Execution Flow](#8-complete-execution-flow)
9. [Internal Working](#9-internal-working)
10. [Real-World Production Patterns](#10-real-world-production-patterns)
11. [Common Pitfalls & Solutions](#11-common-pitfalls--solutions)
12. [Interview Questions](#12-top-interview-questions)

---

## 1. WHAT IS DOCUMENT PROCESSING ENGINE?

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/File%20Folder.png" width="80" height="80" alt="Document">
</div>

> **ðŸ“ Author:** Avinash Dhanuka | [GitHub](https://github.com/Avinash-706)

### ðŸ“Œ Definition

**Document Processing Engine** is an enterprise-grade application demonstrating Spring Framework's core features through a practical document processing system that handles PDF, Word, and XML documents.

**Key Features:**
- Multiple document processors (PDF, Word, XML)
- Audit logging system
- Storage management
- Flexible dependency injection
- Bean lifecycle management

### Why This Case Study?

| Concept | Demonstration |
|:--------|:-------------|
| **Dependency Injection** | 3 types: Constructor, Setter, Field |
| **Bean Scopes** | Singleton vs Prototype |
| **@Primary** | Default bean selection |
| **@Qualifier** | Explicit bean selection |
| **@Lazy** | Delayed initialization |
| **Lifecycle Callbacks** | @PostConstruct, @PreDestroy |

---

## 2. ARCHITECTURE OVERVIEW

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Gear.png" width="80" height="80" alt="Architecture">
</div>

### Component Architecture

```mermaid
graph TD
    A[App.java] --> B[ApplicationContext]
    B --> C[AppConfig]
    C --> D["@ComponentScan"]
    D --> E[DocumentEngine]
    
    E --> F[DocumentProcessor Interface]
    E --> G[AuditService]
    E --> H[StorageService]
    
    F --> I[PdfDocumentProcessor @Primary]
    F --> J[WordDocumentProcessor @Lazy]
    F --> K[XmlDocumentProcessor @Prototype]
    
    style E fill:#fff9c4,stroke:#f57f17,color:#000
    style I fill:#c8e6c9,stroke:#2e7d32,color:#000
    style J fill:#bbdefb,stroke:#1565c0,color:#000
    style K fill:#f3e5f5,stroke:#6a1b9a,color:#000
```

### Class Diagram

```mermaid
classDiagram
    class DocumentProcessor {
        <<interface>>
        +processDocument(String)
    }
    
    class PdfDocumentProcessor {
        @Primary
        +processDocument(String)
    }
    
    class WordDocumentProcessor {
        @Lazy
        +processDocument(String)
    }
    
    class XmlDocumentProcessor {
        @Prototype
        +processDocument(String)
    }
    
    class DocumentEngine {
        -DocumentProcessor processor
        -AuditService auditService
        -StorageService storageService
        +processDocument(String)
    }
    
    class AuditService {
        +init() @PostConstruct
        +destroy() @PreDestroy
        +logBeforeProcessing(String)
    }
    
    class StorageService {
        @Singleton
        +storeDocument(String)
    }
    
    DocumentProcessor <|.. PdfDocumentProcessor
    DocumentProcessor <|.. WordDocumentProcessor
    DocumentProcessor <|.. XmlDocumentProcessor
    DocumentEngine --> DocumentProcessor
    DocumentEngine --> AuditService
    DocumentEngine --> StorageService
```

---

## 3. DEPENDENCY INJECTION PATTERNS

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Link.png" width="80" height="80" alt="DI">
</div>

### ðŸ“Œ Three Types of Dependency Injection

```mermaid
graph TD
    A[DocumentEngine] --> B[Constructor Injection]
    A --> C[Setter Injection]
    A --> D[Field Injection]
    
    B --> E[DocumentProcessor @Qualifier]
    C --> F[AuditService]
    D --> G[StorageService]
    
    style B fill:#c8e6c9,stroke:#2e7d32,color:#000
    style C fill:#bbdefb,stroke:#1565c0,color:#000
    style D fill:#fff9c4,stroke:#f57f17,color:#000
```

###  1. Constructor Injection (Recommended)

```java
@Component
public class DocumentEngine {
    private final DocumentProcessor documentProcessor;
    
    @Autowired
    public DocumentEngine(@Qualifier("xmlDocumentProcessor") DocumentProcessor documentProcessor) {
        this.documentProcessor = documentProcessor;
        System.out.println("[Constructor Injection] Injected: " + documentProcessor.getClass().getSimpleName());
    }
}
```

**Advantages:**
- Immutable dependencies (final fields)
- Required dependencies enforced
- Easy to test (no reflection needed)
- Null-safe

**When to Use:**
- Required dependencies
- Immutable objects
- Unit testing scenarios

---

###  2. Setter Injection

```java
@Component
public class DocumentEngine {
    private AuditService auditService;
    
    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
        System.out.println("[Setter Injection] Injected: " + auditService.getClass().getSimpleName());
    }
}
```

**Advantages:**
- Optional dependencies
- Allows reconfiguration
- Circular dependency resolution

**When to Use:**
- Optional dependencies
- Reconfigurable beans
- Breaking circular dependencies

---

###  3. Field Injection

```java
@Component
public class DocumentEngine {
    @Autowired
    private StorageService storageService;
}
```

**Advantages:**
- Simplest syntax
- Less boilerplate code

**Disadvantages:**
- Cannot use final fields
- Harder to test (requires reflection)
- Hidden dependencies

**When to Use:**
- Rapid prototyping
- Simple applications
- Not recommended for production

---

### ðŸ“Š Injection Pattern Comparison

| Feature | Constructor | Setter | Field |
|:--------|:-----------|:-------|:------|
| **Immutability** | âœ… Yes (final) | âŒ No | âŒ No |
| **Required Dependencies** | âœ… Enforced | âŒ Optional | âŒ Optional |
| **Testability** | âœ… Easy | âš ï¸ Medium | âŒ Hard |
| **Circular Dependencies** | âŒ Fails | âœ… Resolves | âœ… Resolves |
| **Null Safety** | âœ… Guaranteed | âš ï¸ Possible | âš ï¸ Possible |
| **Code Clarity** | âœ… Explicit | âœ… Explicit | âŒ Hidden |
| **Spring Recommendation** | âœ… Preferred | âš ï¸ Acceptable | âŒ Discouraged |

---

## 4. BEAN SCOPES DEEP DIVE

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Package.png" width="80" height="80" alt="Scopes">
</div>

### ðŸ“Œ Singleton vs Prototype

```mermaid
graph TD
    A[Bean Scopes] --> B[Singleton Default]
    A --> C[Prototype]
    
    B --> D[One Instance per Container]
    B --> E[StorageService Example]
    
    C --> F[New Instance per Request]
    C --> G[XmlDocumentProcessor Example]
    
    style B fill:#c8e6c9,stroke:#2e7d32,color:#000
    style C fill:#bbdefb,stroke:#1565c0,color:#000
```

###  Singleton Scope (Default)

```java
@Component
@Scope("singleton")  // Default, can be omitted
public class StorageService {
    public StorageService() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
}
```

**Behavior:**
```
Container starts â†’ StorageService created ONCE
Request 1 â†’ Same instance
Request 2 â†’ Same instance
Request 3 â†’ Same instance
```

**Characteristics:**
- One instance per Spring container
- Created at container startup (unless @Lazy)
- Thread-safe if stateless
- Memory efficient

**Use Cases:**
- Stateless services
- Shared resources (connection pools)
- Utility classes
- Configuration beans

---

###  Prototype Scope

```java
@Component
@Scope("prototype")
public class XmlDocumentProcessor implements DocumentProcessor {
    public XmlDocumentProcessor() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
}
```

**Behavior:**
```
Container starts â†’ No instance created
Request 1 â†’ New instance created
Request 2 â†’ New instance created
Request 3 â†’ New instance created
```

**Characteristics:**
- New instance per getBean() call
- Not created at startup
- Spring doesn't manage destruction
- Higher memory usage

**Use Cases:**
- Stateful objects
- Per-request processing
- Thread-specific instances
- Temporary objects

---

### ðŸ“Š Scope Comparison

| Feature | Singleton | Prototype |
|:--------|:----------|:----------|
| **Instances** | One per container | Many |
| **Creation Time** | Startup (unless @Lazy) | On demand |
| **Memory** | Low | Higher |
| **Thread Safety** | Must be stateless | Each thread gets own |
| **Destruction** | Spring manages | Developer manages |
| **@PreDestroy** | âœ… Called | âŒ Not called |
| **Performance** | âœ… Fast | âš ï¸ Slower |

###  Scope Demonstration

```java
// Singleton behavior
StorageService storage1 = context.getBean(StorageService.class);
StorageService storage2 = context.getBean(StorageService.class);
System.out.println(storage1 == storage2);  // true (same instance)

// Prototype behavior
XmlDocumentProcessor xml1 = context.getBean(XmlDocumentProcessor.class);
XmlDocumentProcessor xml2 = context.getBean(XmlDocumentProcessor.class);
System.out.println(xml1 == xml2);  // false (different instances)
```

---

## 5. @PRIMARY VS @QUALIFIER

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Pushpin.png" width="80" height="80" alt="Primary">
</div>

### ðŸ“Œ The Problem: Multiple Implementations

```mermaid
graph TD
    A[DocumentProcessor Interface] --> B[PdfDocumentProcessor]
    A --> C[WordDocumentProcessor]
    A --> D[XmlDocumentProcessor]
    
    E[DocumentEngine needs one] --> F{Which one?}
    F -->|No annotation| G[âŒ Error: Multiple beans]
    F -->|"@Primary"| H[âœ… PdfDocumentProcessor]
    F -->|"@Qualifier"| I[âœ… Specified bean]
    
    style G fill:#ffccbc,stroke:#d84315,color:#000
    style H fill:#c8e6c9,stroke:#2e7d32,color:#000
    style I fill:#bbdefb,stroke:#1565c0,color:#000
```

###  @Primary: Default Selection

```java
@Component
@Primary  // This is the default choice
public class PdfDocumentProcessor implements DocumentProcessor {
    @Override
    public void processDocument(String documentName) {
        System.out.println("Processing PDF: " + documentName);
    }
}
```

**Usage:**
```java
// Without @Qualifier, gets @Primary bean
DocumentProcessor processor = context.getBean(DocumentProcessor.class);
// Returns: PdfDocumentProcessor
```

**When to Use @Primary:**
- One implementation is used 80% of the time
- Default behavior for most cases
- Reduces @Qualifier usage

---

###  @Qualifier: Explicit Selection

```java
@Component
public class DocumentEngine {
    private final DocumentProcessor documentProcessor;
    
    @Autowired
    public DocumentEngine(@Qualifier("xmlDocumentProcessor") DocumentProcessor documentProcessor) {
        this.documentProcessor = documentProcessor;
        // Explicitly gets XmlDocumentProcessor, ignoring @Primary
    }
}
```

**Bean Naming:**
```java
@Component  // Bean name: "pdfDocumentProcessor" (camelCase)
public class PdfDocumentProcessor { }

@Component("customName")  // Bean name: "customName"
public class WordDocumentProcessor { }
```

**When to Use @Qualifier:**
- Need specific implementation
- Override @Primary behavior
- Multiple injection points need different beans

---

### ðŸ“Š @Primary vs @Qualifier Decision Tree

```mermaid
graph TD
    A[Need DocumentProcessor] --> B{Specific implementation needed?}
    B -->|No| C{"@Primary exists?"}
    B -->|Yes| D[Use @Qualifier]
    
    C -->|Yes| E[Gets @Primary bean]
    C -->|No| F[âŒ Error: Multiple beans]
    
    D --> G[Gets specified bean]
    
    style E fill:#c8e6c9,stroke:#2e7d32,color:#000
    style F fill:#ffccbc,stroke:#d84315,color:#000
    style G fill:#bbdefb,stroke:#1565c0,color:#000
```

###  Real-World Example

```java
// Scenario: Payment Processing System

@Component
@Primary
public class CreditCardPayment implements PaymentProcessor {
    // Default payment method (80% of transactions)
}

@Component
public class PayPalPayment implements PaymentProcessor {
    // Alternative payment method
}

@Component
public class CryptoPayment implements PaymentProcessor {
    // Rare payment method
}

// Usage
@Service
public class OrderService {
    private final PaymentProcessor defaultPayment;
    private final PaymentProcessor cryptoPayment;
    
    @Autowired
    public OrderService(
            PaymentProcessor defaultPayment,  // Gets @Primary (CreditCard)
            @Qualifier("cryptoPayment") PaymentProcessor cryptoPayment) {
        this.defaultPayment = defaultPayment;
        this.cryptoPayment = cryptoPayment;
    }
}
```


---

## 6. LAZY INITIALIZATION


### ðŸ“Œ Eager vs Lazy Loading

```mermaid
sequenceDiagram
    participant App
    participant Context
    participant Eager as PdfProcessor (Eager)
    participant Lazy as WordProcessor (@Lazy)
    
    App->>Context: Create ApplicationContext
    Context->>Eager: Create instance
    Eager->>Context: Instance created
    Note over Lazy: NOT created yet
    Context->>App: Context ready
    
    App->>Context: getBean(WordProcessor)
    Context->>Lazy: Create instance NOW
    Lazy->>Context: Instance created
    Context->>App: Return instance
```

###  @Lazy Implementation

```java
@Component
@Lazy  // Not created at startup
public class WordDocumentProcessor implements DocumentProcessor {
    public WordDocumentProcessor() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
}
```

**Execution Timeline:**
```
Time 0: Container starts
  âœ… PdfDocumentProcessor created
  âœ… XmlDocumentProcessor definition registered
  âœ… AuditService created
  âœ… StorageService created
  âŒ WordDocumentProcessor NOT created

Time 5: First getBean(WordDocumentProcessor.class)
  âœ… WordDocumentProcessor created NOW
```

### ðŸ“Š Lazy vs Eager Comparison

| Feature | Eager (Default) | Lazy (@Lazy) |
|:--------|:---------------|:-------------|
| **Creation Time** | Startup | First access |
| **Startup Speed** | âŒ Slower | âœ… Faster |
| **First Access** | âœ… Fast | âŒ Slower |
| **Error Detection** | âœ… Immediate | âŒ Delayed |
| **Memory at Startup** | âŒ Higher | âœ… Lower |
| **Use Case** | Always-used beans | Rarely-used beans |

###  When to Use @Lazy

**âœ… Use @Lazy When:**
- Bean is rarely used
- Expensive initialization (database connections, file loading)
- Optional features
- Conditional beans

**âŒ Don't Use @Lazy When:**
- Bean is always needed
- Fast initialization
- Want fail-fast behavior
- Critical startup validation

---

## 7. BEAN LIFECYCLE MANAGEMENT

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Gear.png" width="80" height="80" alt="Lifecycle">
</div>

### ðŸ“Œ Complete Bean Lifecycle

```mermaid
graph TD
    A[Container Starts] --> B[Bean Instantiation]
    B --> C[Dependency Injection]
    C --> D["@PostConstruct"]
    D --> E[Bean Ready]
    E --> F[Bean in Use]
    F --> G[Container Shutdown]
    G --> H["@PreDestroy"]
    H --> I[Bean Destroyed]
    
    style D fill:#c8e6c9,stroke:#2e7d32,color:#000
    style H fill:#ffccbc,stroke:#d84315,color:#000
```

###  @PostConstruct: Initialization

```java
@Component
public class AuditService {
    
    public AuditService() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
    
    @PostConstruct
    public void init() {
        System.out.println("[PostConstruct] Initializing audit configuration");
        // Initialize resources
        // Load configuration
        // Connect to audit database
    }
}
```

**Execution Order:**
```
1. Constructor called
2. Dependencies injected
3. @PostConstruct called  â† Initialization logic here
4. Bean ready for use
```

**Use Cases:**
- Initialize resources (connections, caches)
- Load configuration
- Validate dependencies
- Start background threads

---

###  @PreDestroy: Cleanup

```java
@Component
public class AuditService {
    
    @PreDestroy
    public void destroy() {
        System.out.println("[PreDestroy] Releasing audit resources");
        // Close connections
        // Flush buffers
        // Release resources
    }
}
```

**Execution Order:**
```
1. Container shutdown initiated
2. @PreDestroy called  â† Cleanup logic here
3. Bean destroyed
```

**Use Cases:**
- Close database connections
- Flush buffers
- Release file handles
- Stop background threads

---

### ðŸ“Š Lifecycle Callbacks Comparison

| Callback | Purpose | When Called | Scope Support |
|:---------|:--------|:-----------|:-------------|
| **Constructor** | Create instance | Bean instantiation | All |
| **@PostConstruct** | Initialize | After DI complete | Singleton, Prototype |
| **@PreDestroy** | Cleanup | Before destruction | Singleton only |

**Important:** @PreDestroy is NOT called for prototype beans!

---

## 8. COMPLETE EXECUTION FLOW

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Rocket.png" width="80" height="80" alt="Flow">
</div>

### ðŸ“Œ Application Execution Sequence

```mermaid
sequenceDiagram
    participant App
    participant Context as ApplicationContext
    participant Config as AppConfig
    participant PDF as PdfProcessor
    participant Audit as AuditService
    participant Storage as StorageService
    participant Engine as DocumentEngine
    participant XML as XmlProcessor
    participant Word as WordProcessor
    
    App->>Context: new AnnotationConfigApplicationContext(AppConfig.class)
    Context->>Config: Load configuration
    Config->>Context: @ComponentScan("org.example")
    
    Note over Context: Bean Creation Phase
    Context->>PDF: Create (Eager + @Primary)
    Context->>Audit: Create (Eager)
    Audit->>Audit: @PostConstruct init()
    Context->>Storage: Create (Singleton)
    Context->>XML: Register definition (Prototype)
    Note over Word: NOT created (@Lazy)
    
    Context->>Engine: Create DocumentEngine
    Engine->>Engine: Constructor Injection (XML)
    Engine->>Engine: Setter Injection (Audit)
    Engine->>Engine: Field Injection (Storage)
    
    Context->>App: Context ready
    
    Note over App: Usage Phase
    App->>Engine: processDocument("contract.xml")
    Engine->>Audit: logBeforeProcessing()
    Engine->>XML: processDocument()
    Engine->>Storage: storeDocument()
    
    App->>Context: getBean(DocumentProcessor.class)
    Context->>App: Return PDF (@Primary)
    
    App->>Context: getBean(WordProcessor.class)
    Context->>Word: Create NOW (@Lazy)
    Context->>App: Return Word
    
    Note over App: Shutdown Phase
    App->>Context: close()
    Context->>Audit: @PreDestroy destroy()
    Context->>App: Shutdown complete
```

###  Console Output Analysis

```
--- Enterprise Document Processing Engine ---

Creating instance of: PdfDocumentProcessor
Creating instance of: AuditService
[PostConstruct] Initializing audit configuration for AuditService
Creating instance of: StorageService
Creating instance of: XmlDocumentProcessor
Creating instance of: DocumentEngine
[Constructor Injection] Injected DocumentProcessor: XmlDocumentProcessor
[Setter Injection] Injected AuditService: AuditService

-- @qualifier with XmlDocumentProcessor --
DocumentEngine is using: XmlDocumentProcessor

-- Document Processing Started --
[AUDIT LOG] Starting to process document: contract.xml
Processing XML document: contract.xml using XmlDocumentProcessor
[STORAGE] Storing document: contract.xml using StorageService
-- Document Processing Completed --

-- @primary (PdfDocumentProcessor) --
Default processor (without qualifier) is: PdfDocumentProcessor
Processing PDF document: report.pdf using PdfDocumentProcessor

-- @Lazy (WordDocumentProcessor) --
Creating instance of: WordDocumentProcessor
Processing Word document: document.docx using WordDocumentProcessor

-- Closing Application Context --
[PreDestroy] Releasing audit resources for AuditService

-- Application Completed --
```

**Key Observations:**
1. PdfDocumentProcessor created first (eager + @Primary)
2. AuditService @PostConstruct called after creation
3. XmlDocumentProcessor created for DocumentEngine (prototype)
4. WordDocumentProcessor created only when requested (@Lazy)
5. @PreDestroy called during shutdown

---

## 9. INTERNAL WORKING

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Microscope.png" width="80" height="80" alt="Internal">
</div>

### ðŸ“Œ Spring Container Internals

```mermaid
graph TD
    A[AnnotationConfigApplicationContext] --> B[BeanDefinitionReader]
    B --> C[ComponentScanner]
    C --> D[BeanDefinitionRegistry]
    
    D --> E[BeanFactory]
    E --> F[BeanPostProcessor]
    F --> G[Bean Instances]
    
    H["@Component"] -.-> C
    I["@Primary"] -.-> D
    J["@Qualifier"] -.-> E
    K["@Lazy"] -.-> E
    L["@Scope"] -.-> E
    
    style E fill:#fff9c4,stroke:#f57f17,color:#000
    style G fill:#c8e6c9,stroke:#2e7d32,color:#000
```

###  Bean Creation Process

**Step 1: Component Scanning**
```java
@ComponentScan(basePackages = "org.example")
// Spring scans all classes in org.example package
// Finds classes with @Component, @Service, @Repository, @Controller
```

**Step 2: Bean Definition Registration**
```java
// Spring internally creates BeanDefinition for each component
BeanDefinition pdfDef = new GenericBeanDefinition();
pdfDef.setBeanClassName("org.example.entity.PdfDocumentProcessor");
pdfDef.setPrimary(true);  // @Primary annotation
pdfDef.setLazyInit(false);  // Eager by default

BeanDefinition wordDef = new GenericBeanDefinition();
wordDef.setBeanClassName("org.example.entity.WordDocumentProcessor");
wordDef.setLazyInit(true);  // @Lazy annotation

BeanDefinition xmlDef = new GenericBeanDefinition();
xmlDef.setBeanClassName("org.example.entity.XmlDocumentProcessor");
xmlDef.setScope("prototype");  // @Scope annotation
```

**Step 3: Dependency Resolution**
```java
// Spring analyzes DocumentEngine dependencies
@Autowired
public DocumentEngine(@Qualifier("xmlDocumentProcessor") DocumentProcessor processor) {
    // Spring looks for bean named "xmlDocumentProcessor"
    // Ignores @Primary because @Qualifier is explicit
}
```

**Step 4: Bean Instantiation**
```java
// Spring uses reflection to create instances
Class<?> clazz = Class.forName("org.example.entity.PdfDocumentProcessor");
Constructor<?> constructor = clazz.getConstructor();
Object instance = constructor.newInstance();
```

**Step 5: Dependency Injection**
```java
// Constructor injection
Constructor<?> constructor = DocumentEngine.class.getConstructor(DocumentProcessor.class);
Object engine = constructor.newInstance(xmlProcessor);

// Setter injection
Method setter = DocumentEngine.class.getMethod("setAuditService", AuditService.class);
setter.invoke(engine, auditService);

// Field injection
Field field = DocumentEngine.class.getDeclaredField("storageService");
field.setAccessible(true);
field.set(engine, storageService);
```

**Step 6: Lifecycle Callbacks**
```java
// @PostConstruct
Method postConstruct = AuditService.class.getMethod("init");
postConstruct.invoke(auditServiceInstance);

// @PreDestroy (on shutdown)
Method preDestroy = AuditService.class.getMethod("destroy");
preDestroy.invoke(auditServiceInstance);
```


---

## 10. REAL-WORLD PRODUCTION PATTERNS

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Globe%20with%20Meridians.png" width="80" height="80" alt="Production">
</div>

###  Pattern 1: Strategy Pattern with DI

```java
// Payment Processing System
public interface PaymentStrategy {
    void processPayment(double amount);
}

@Component
@Primary
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment: $" + amount);
    }
}

@Component
public class PayPalPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
    }
}

@Service
public class PaymentService {
    private final Map<String, PaymentStrategy> strategies;
    
    @Autowired
    public PaymentService(List<PaymentStrategy> strategyList) {
        // Spring injects ALL implementations
        this.strategies = strategyList.stream()
            .collect(Collectors.toMap(
                s -> s.getClass().getSimpleName(),
                s -> s
            ));
    }
    
    public void pay(String method, double amount) {
        PaymentStrategy strategy = strategies.get(method + "Payment");
        strategy.processPayment(amount);
    }
}
```

---

###  Pattern 2: Factory Pattern with @Qualifier

```java
@Component
public class DocumentProcessorFactory {
    private final Map<String, DocumentProcessor> processors;
    
    @Autowired
    public DocumentProcessorFactory(
            @Qualifier("pdfDocumentProcessor") DocumentProcessor pdfProcessor,
            @Qualifier("wordDocumentProcessor") DocumentProcessor wordProcessor,
            @Qualifier("xmlDocumentProcessor") DocumentProcessor xmlProcessor) {
        
        processors = Map.of(
            "pdf", pdfProcessor,
            "docx", wordProcessor,
            "xml", xmlProcessor
        );
    }
    
    public DocumentProcessor getProcessor(String fileExtension) {
        return processors.getOrDefault(fileExtension, processors.get("pdf"));
    }
}
```

---

###  Pattern 3: Resource Pool with Prototype

```java
@Component
@Scope("prototype")
public class DatabaseConnection {
    private final String connectionId;
    
    public DatabaseConnection() {
        this.connectionId = UUID.randomUUID().toString();
        System.out.println("Created connection: " + connectionId);
    }
    
    public void execute(String query) {
        System.out.println("Executing on " + connectionId + ": " + query);
    }
}

@Component
public class ConnectionPool {
    @Autowired
    private ApplicationContext context;
    
    private final Queue<DatabaseConnection> pool = new ConcurrentLinkedQueue<>();
    
    public DatabaseConnection getConnection() {
        if (pool.isEmpty()) {
            return context.getBean(DatabaseConnection.class);  // New instance
        }
        return pool.poll();
    }
    
    public void releaseConnection(DatabaseConnection conn) {
        pool.offer(conn);
    }
}
```

---

###  Pattern 4: Conditional Beans

```java
@Component
@Profile("dev")
public class DevDocumentProcessor implements DocumentProcessor {
    @Override
    public void processDocument(String documentName) {
        System.out.println("[DEV] Processing: " + documentName);
    }
}

@Component
@Profile("prod")
public class ProdDocumentProcessor implements DocumentProcessor {
    @Override
    public void processDocument(String documentName) {
        System.out.println("[PROD] Processing: " + documentName);
        // Add production logging, monitoring, etc.
    }
}
```

---

## 11. COMMON PITFALLS & SOLUTIONS

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Symbols/Warning.png" width="80" height="80" alt="Warning">
</div>

### ❌ Pitfall 1: Field Injection in Final Fields

**Problem:**
```java
@Component
public class DocumentEngine {
    @Autowired
    private final DocumentProcessor processor;  // Compilation error!
}
```

**Solution:**
```java
@Component
public class DocumentEngine {
    private final DocumentProcessor processor;
    
    @Autowired  // Constructor injection for final fields
    public DocumentEngine(DocumentProcessor processor) {
        this.processor = processor;
    }
}
```

---

### ❌ Pitfall 2: Multiple Beans Without @Primary or @Qualifier

**Problem:**
```java
@Component
public class PdfDocumentProcessor implements DocumentProcessor { }

@Component
public class WordDocumentProcessor implements DocumentProcessor { }

@Component
public class DocumentEngine {
    @Autowired
    private DocumentProcessor processor;  // Error: Multiple beans found!
}
```

**Solution:**
```java
// Option 1: Use @Primary
@Component
@Primary
public class PdfDocumentProcessor implements DocumentProcessor { }

// Option 2: Use @Qualifier
@Component
public class DocumentEngine {
    @Autowired
    @Qualifier("wordDocumentProcessor")
    private DocumentProcessor processor;
}
```

---

### ❌ Pitfall 3: Circular Dependency

**Problem:**
```java
@Component
public class ServiceA {
    @Autowired
    private ServiceB serviceB;  // Circular!
}

@Component
public class ServiceB {
    @Autowired
    private ServiceA serviceA;  // Circular!
}
```

**Solution:**
```java
@Component
public class ServiceA {
    @Autowired
    @Lazy  // Break circular dependency
    private ServiceB serviceB;
}
```

---

### ❌ Pitfall 4: Prototype Bean in Singleton

**Problem:**
```java
@Component
@Scope("singleton")
public class DocumentEngine {
    @Autowired
    private DocumentProcessor processor;  // Prototype bean injected once!
}

@Component
@Scope("prototype")
public class DocumentProcessor { }
```

**Solution:**
```java
@Component
public class DocumentEngine {
    @Autowired
    private ApplicationContext context;
    
    public void process() {
        DocumentProcessor processor = context.getBean(DocumentProcessor.class);
        // New instance every time
    }
}
```

---

### ❌ Pitfall 5: @PostConstruct with Uninitialized Dependencies

**Problem:**
```java
@Component
public class AuditService {
    @Autowired
    private DatabaseService dbService;
    
    @PostConstruct
    public void init() {
        dbService.connect();  // May fail if dbService not fully initialized
    }
}
```

**Solution:**
```java
@Component
public class AuditService {
    private final DatabaseService dbService;
    
    @Autowired
    public AuditService(DatabaseService dbService) {
        this.dbService = dbService;  // Guaranteed initialized
    }
    
    @PostConstruct
    public void init() {
        dbService.connect();  // Safe now
    }
}
```

---

## 12. TOP INTERVIEW QUESTIONS

<div align="center">
<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Light%20Bulb.png" width="80" height="80" alt="Interview">
</div>

### Q1: What happens if you inject a Prototype bean into a Singleton bean?

**Answer:**

**Scenario:**
```java
@Component
@Scope("singleton")
public class DocumentEngine {
    @Autowired
    private DocumentProcessor processor;  // Prototype bean
}

@Component
@Scope("prototype")
public class DocumentProcessor { }
```

**Problem:**
The prototype bean is injected ONLY ONCE when the singleton is created. You get the same instance every time, defeating the purpose of prototype scope.

**Why?**
```mermaid
sequenceDiagram
    participant Spring
    participant Engine as DocumentEngine (Singleton)
    participant Processor as DocumentProcessor (Prototype)
    
    Spring->>Engine: 1. Create singleton instance
    Spring->>Processor: 2. Create prototype instance
    Spring->>Engine: 3. Inject processor (once)
    Note over Engine: Processor reference cached
    
    Engine->>Processor: 4. Use processor (same instance)
    Engine->>Processor: 5. Use processor (same instance)
    Engine->>Processor: 6. Use processor (same instance)
```

**Solutions:**

**Option 1: ApplicationContext Lookup**
```java
@Component
public class DocumentEngine {
    @Autowired
    private ApplicationContext context;
    
    public void process() {
        DocumentProcessor processor = context.getBean(DocumentProcessor.class);
        processor.process();  // New instance every time
    }
}
```

**Option 2: ObjectFactory**
```java
@Component
public class DocumentEngine {
    @Autowired
    private ObjectFactory<DocumentProcessor> processorFactory;
    
    public void process() {
        DocumentProcessor processor = processorFactory.getObject();
        processor.process();  // New instance every time
    }
}
```

**Option 3: @Lookup (Method Injection)**
```java
@Component
public abstract class DocumentEngine {
    
    @Lookup
    protected abstract DocumentProcessor getProcessor();
    
    public void process() {
        DocumentProcessor processor = getProcessor();
        processor.process();  // New instance every time
    }
}
```

---

### Q2: Explain the order of bean initialization when using @PostConstruct

**Answer:**

**Complete Lifecycle:**

```mermaid
graph TD
    A[1. Constructor Called] --> B[2. Dependencies Injected]
    B --> C[3. @PostConstruct Called]
    C --> D[4. Bean Ready]
    D --> E[5. Bean Used]
    E --> F[6. @PreDestroy Called]
    F --> G[7. Bean Destroyed]
    
    style A fill:#fff9c4,stroke:#f57f17,color:#000
    style C fill:#c8e6c9,stroke:#2e7d32,color:#000
    style F fill:#ffcdd2,stroke:#c62828,color:#000
```

**Example:**
```java
@Component
public class AuditService {
    @Autowired
    private StorageService storageService;
    
    public AuditService() {
        System.out.println("1. Constructor called");
        // storageService is NULL here!
    }
    
    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct called");
        // storageService is AVAILABLE here!
        storageService.initialize();
    }
    
    @PreDestroy
    public void cleanup() {
        System.out.println("3. @PreDestroy called");
        storageService.cleanup();
    }
}
```

**Output:**
```
1. Constructor called
2. @PostConstruct called
3. @PreDestroy called
```

**Key Points:**
- Constructor runs BEFORE dependency injection
- @PostConstruct runs AFTER all dependencies injected
- @PreDestroy runs BEFORE bean destruction
- Use @PostConstruct for initialization logic that needs dependencies

---

### Q3: What's the difference between @Autowired and @Inject?

**Answer:**

**Comparison:**

| Feature | @Autowired (Spring) | @Inject (JSR-330) |
|:--------|:-------------------|:-----------------|
| **Standard** | Spring-specific | Java standard |
| **Required** | @Autowired(required=false) | No equivalent |
| **Qualifier** | @Qualifier | @Named |
| **Provider** | No | Provider<T> |
| **Portability** | Spring only | Any DI framework |

**Example:**

**Spring @Autowired:**
```java
@Component
public class OrderService {
    @Autowired
    @Qualifier("creditCardPayment")
    private PaymentService paymentService;
    
    @Autowired(required = false)
    private Optional<EmailService> emailService;
}
```

**JSR-330 @Inject:**
```java
@Component
public class OrderService {
    @Inject
    @Named("creditCardPayment")
    private PaymentService paymentService;
    
    @Inject
    private Provider<EmailService> emailProvider;
}
```

**When to use:**
- **@Autowired:** Most common (Spring projects)
- **@Inject:** Need portability across DI frameworks

**Both do the same thing, but @Inject is a Java standard.**

---

### Q4: How does Spring resolve circular dependencies?

**Answer:**

**Scenario:**
```java
@Component
public class ServiceA {
    @Autowired
    private ServiceB serviceB;
}

@Component
public class ServiceB {
    @Autowired
    private ServiceA serviceA;  // Circular!
}
```

**Spring's Resolution Process:**

```mermaid
sequenceDiagram
    participant Spring
    participant A as ServiceA instance
    participant B as ServiceB instance
    
    Spring->>A: 1. Create ServiceA (constructor)
    Note over A: Partially initialized
    Spring->>Spring: 2. Store early reference
    Spring->>B: 3. Create ServiceB instance
    Spring->>B: 4. Need ServiceA
    B->>Spring: 5. Inject early reference
    Note over B: Fully initialized
    Spring->>A: 6. Inject ServiceB
    Note over A: Fully initialized
```

**Step-by-Step:**
```
1. Spring creates ServiceA instance (constructor called)
2. ServiceA tries to inject ServiceB
3. Spring creates ServiceB instance
4. ServiceB needs ServiceA
5. Spring injects early reference of ServiceA (not fully initialized)
6. ServiceB fully initialized
7. Spring injects ServiceB into ServiceA
8. ServiceA fully initialized
9. Both beans ready
```

**Why Constructor Injection Fails:**
```java
@Component
public class ServiceA {
    private final ServiceB serviceB;
    
    @Autowired
    public ServiceA(ServiceB serviceB) {  // Circular dependency error!
        this.serviceB = serviceB;
    }
}
```

**Solution:**
```java
// Option 1: Use @Lazy
@Component
public class ServiceA {
    private final ServiceB serviceB;
    
    @Autowired
    public ServiceA(@Lazy ServiceB serviceB) {
        this.serviceB = serviceB;  // Proxy injected
    }
}

// Option 2: Redesign (best)
// Extract common logic to ServiceC
```

---

### Q5: Explain bean naming conventions in Spring

**Answer:**

**Default Naming:**
```
Spring uses class name as bean name (camelCase)
```

**Examples:**

```java
@Component
public class UserService { }
// Bean name: "userService"

@Component
public class XMLParser { }
// Bean name: "XMLParser" (preserves capitals)

@Component("myService")
public class UserService { }
// Bean name: "myService"

@Component
@Qualifier("customName")
public class UserService { }
// Bean name: "userService"
// Qualifier: "customName"
```

**Accessing Beans:**

```java
// By type
UserService service = context.getBean(UserService.class);

// By name
UserService service = (UserService) context.getBean("userService");

// By name and type
UserService service = context.getBean("userService", UserService.class);

// Get all beans of type
Map<String, DocumentProcessor> processors = 
    context.getBeansOfType(DocumentProcessor.class);
// Returns: {"pdfDocumentProcessor": PdfDocumentProcessor, 
//           "wordDocumentProcessor": WordDocumentProcessor}
```

**Multiple Beans:**
```java
@Component("pdfProcessor")
public class PdfDocumentProcessor implements DocumentProcessor { }

@Component("wordProcessor")
public class WordDocumentProcessor implements DocumentProcessor { }

// Get specific bean
DocumentProcessor pdf = context.getBean("pdfProcessor", DocumentProcessor.class);
DocumentProcessor word = context.getBean("wordProcessor", DocumentProcessor.class);
```

---

### Q6: What happens if @PostConstruct method throws an exception?

**Answer:**

**Scenario:**
```java
@Component
public class AuditService {
    @PostConstruct
    public void init() {
        throw new RuntimeException("Initialization failed!");
    }
}
```

**Result:**
- Bean creation FAILS
- Application context fails to start
- Spring throws BeanCreationException
- Application DOES NOT START

**Example Output:**
```
Error creating bean with name 'auditService': 
Invocation of init method failed; 
nested exception is java.lang.RuntimeException: Initialization failed!
```

**Best Practice:**
```java
@Component
public class AuditService {
    @PostConstruct
    public void init() {
        try {
            // Risky initialization
            connectToDatabase();
        } catch (Exception e) {
            // Log error but don't fail application
            logger.error("Failed to initialize audit service", e);
            // Or rethrow if critical
            throw new BeanCreationException("Critical initialization failed", e);
        }
    }
}
```

**Lifecycle Impact:**

```mermaid
graph TD
    A[Constructor] --> B["@PostConstruct"]
    B -->|Success| C[Bean Ready]
    B -->|Exception| D[Bean Creation Failed]
    D --> E[Application Fails to Start]
    
    style C fill:#c8e6c9,stroke:#2e7d32,color:#000
    style E fill:#ffcdd2,stroke:#c62828,color:#000
```

---

### Q7: Why use Constructor Injection over Field Injection?

**Answer:**

**Field Injection (Not Recommended):**
```java
@Component
public class DocumentEngine {
    @Autowired
    private DocumentProcessor processor;  // Hard to test
    @Autowired
    private StorageService storage;
}
```

**Constructor Injection (Recommended):**
```java
@Component
public class DocumentEngine {
    private final DocumentProcessor processor;
    private final StorageService storage;
    
    @Autowired
    public DocumentEngine(DocumentProcessor processor, StorageService storage) {
        this.processor = processor;
        this.storage = storage;
    }
}
```

**Advantages:**

| Aspect | Field Injection | Constructor Injection |
|:-------|:---------------|:---------------------|
| **Immutability** | No (mutable) | Yes (final fields) |
| **Testing** | Hard (need Spring) | Easy (plain Java) |
| **Null Safety** | Can be null | Never null |
| **Circular Dependency** | Hidden | Detected early |
| **Required Dependencies** | Unclear | Explicit |

**Testing Comparison:**

**Field Injection (Hard):**
```java
@Test
public void testDocumentEngine() {
    DocumentEngine engine = new DocumentEngine();
    // processor is NULL! Need reflection or Spring context
}
```

**Constructor Injection (Easy):**
```java
@Test
public void testDocumentEngine() {
    DocumentProcessor mockProcessor = mock(DocumentProcessor.class);
    StorageService mockStorage = mock(StorageService.class);
    
    DocumentEngine engine = new DocumentEngine(mockProcessor, mockStorage);
    // Easy to test without Spring!
}
```

**Circular Dependency Detection:**
```java
// Field injection: Fails at runtime
@Component
public class ServiceA {
    @Autowired
    private ServiceB serviceB;  // Runtime error
}

// Constructor injection: Fails at startup
@Component
public class ServiceA {
    @Autowired
    public ServiceA(ServiceB serviceB) {  // Immediate error
        this.serviceB = serviceB;
    }
}
```

---

### Q8: Explain @Primary vs @Qualifier with multiple beans

**Answer:**

**Scenario:**
```java
@Component
public class PdfDocumentProcessor implements DocumentProcessor { }

@Component
public class WordDocumentProcessor implements DocumentProcessor { }

@Component
public class XmlDocumentProcessor implements DocumentProcessor { }
```

**Problem:**
```java
@Component
public class DocumentEngine {
    @Autowired
    private DocumentProcessor processor;  // Error: 3 beans found!
}
```

**Solution 1: @Primary (Default Choice)**
```java
@Component
@Primary  // This will be injected by default
public class PdfDocumentProcessor implements DocumentProcessor { }

@Component
public class DocumentEngine {
    @Autowired
    private DocumentProcessor processor;  // PdfDocumentProcessor injected
}
```

**Solution 2: @Qualifier (Specific Choice)**
```java
@Component
public class DocumentEngine {
    @Autowired
    @Qualifier("xmlDocumentProcessor")  // Specific bean
    private DocumentProcessor processor;
}
```

**Combination:**
```java
@Component
@Primary
public class PdfDocumentProcessor implements DocumentProcessor { }

@Component
public class Engine1 {
    @Autowired
    private DocumentProcessor processor;  // PdfDocumentProcessor (@Primary)
}

@Component
public class Engine2 {
    @Autowired
    @Qualifier("wordDocumentProcessor")
    private DocumentProcessor processor;  // WordDocumentProcessor (@Qualifier wins)
}
```

**Priority:**
```
@Qualifier > @Primary > Bean Name
```

**Decision Tree:**

```mermaid
graph TD
    A[Multiple Beans Found] --> B{"@Qualifier specified?"}
    B -->|Yes| C["Use @Qualifier bean"]
    B -->|No| D{"@Primary exists?"}
    D -->|Yes| E[Use @Primary bean]
    D -->|No| F{Match by name?}
    F -->|Yes| G[Use matching bean]
    F -->|No| H[Error: NoUniqueBeanDefinitionException]
    
    style C fill:#c8e6c9,stroke:#2e7d32,color:#000
    style E fill:#fff9c4,stroke:#f57f17,color:#000
    style H fill:#ffcdd2,stroke:#c62828,color:#000
```

---

<div align="center">

<table>
<tr>
<td align="center">

## 🎓 End of Document Processing Engine Guide

<br>

<img src="../../favicon.png" width="150" height="150" alt="Document Processing">

<br>

**Created with dedication by Avinash Dhanuka**

<br>

[![GitHub](https://img.shields.io/badge/GitHub-Avinash--706-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Avinash-706)
[![Email](https://img.shields.io/badge/Email-Contact_Me-EA4335?style=for-the-badge&logo=gmail&logoColor=white)](mailto:avunashdhanuka@gmail.com)

<br>

---

**Happy Learning! 🚀**

*"Master Dependency Injection, Build Scalable Systems!"* - Avinash Dhanuka

<br>

<img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Waving%20Hand.png" width="60" height="60" alt="Wave">

---

**© 2026 Avinash Dhanuka | All Rights Reserved**

</td>
</tr>
</table>
</div>
