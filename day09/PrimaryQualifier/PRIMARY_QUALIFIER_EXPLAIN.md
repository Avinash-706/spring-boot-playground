# @Primary and @Qualifier - Simple Explanation

## The Problem

When you have **one interface** with **multiple implementations**, Spring gets confused:

```java
interface NotificationService { }

@Component
class EmailNotificationService implements NotificationService { }

@Component
class SmsNotificationService implements NotificationService { }

@Component
class PushNotificationService implements NotificationService { }
```

**Question:** Which one should Spring inject?

**Answer:** Use @Primary or @Qualifier!

---

## Solution 1: @Primary (Default Choice)

### What is @Primary?

@Primary tells Spring: **"Use this bean as the default when there are multiple options"**

### Example:

```java
@Component
@Primary  // This is the default
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendMsg(String message) {
        System.out.println("Email: " + message);
    }
}
```

### How it works:

```java
@Component
public class NotificationManager {
    private final NotificationService service;
    
    // Spring automatically injects EmailNotificationService (the @Primary one)
    public NotificationManager(NotificationService service) {
        this.service = service;  // Gets EmailNotificationService
    }
}
```

### When to use @Primary:
- You have a **preferred/default** implementation
- **Most of the time** you want to use this one
- Example: Email is the most common notification method

---

## Solution 2: @Qualifier (Specific Choice)

### What is @Qualifier?

@Qualifier tells Spring: **"Inject this specific bean by name"**

### Bean Names:
- `EmailNotificationService` class → bean name is `"emailNotificationService"`
- `SmsNotificationService` class → bean name is `"smsNotificationService"`
- `PushNotificationService` class → bean name is `"pushNotificationService"`
- **Rule:** First letter becomes lowercase

### Example:

```java
@Component
public class NotificationManager {
    private final NotificationService emailService;
    private final NotificationService smsService;
    private final NotificationService pushService;
    
    // @Qualifier tells Spring exactly which bean to inject
    public NotificationManager(
            @Qualifier("emailNotificationService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService,
            @Qualifier("pushNotificationService") NotificationService pushService) {
        
        this.emailService = emailService;  // Gets EmailNotificationService
        this.smsService = smsService;      // Gets SmsNotificationService
        this.pushService = pushService;    // Gets PushNotificationService
    }
}
```

### When to use @Qualifier:
- You need a **specific** implementation
- You want to use **multiple** implementations in the same class
- You want to **override** the @Primary choice

---

## How This Project Works

### Step 1: Interface
```java
public interface NotificationService {
    void sendMsg(String message);
}
```

### Step 2: Three Implementations

**EmailNotificationService** (with @Primary):
```java
@Component
@Primary  // Default choice
public class EmailNotificationService implements NotificationService {
    public void sendMsg(String message) {
        System.out.println("Email: " + message);
    }
}
```

**SmsNotificationService**:
```java
@Component
public class SmsNotificationService implements NotificationService {
    public void sendMsg(String message) {
        System.out.println("SMS: " + message);
    }
}
```

**PushNotificationService**:
```java
@Component
public class PushNotificationService implements NotificationService {
    public void sendMsg(String message) {
        System.out.println("Push Notification: " + message);
    }
}
```

### Step 3: NotificationManager (Uses Both @Primary and @Qualifier)

```java
@Component
public class NotificationManager {
    private final NotificationService primaryService;  // Gets @Primary
    private final NotificationService emailService;    // Gets specific
    private final NotificationService smsService;      // Gets specific
    private final NotificationService pushService;     // Gets specific
    
    public NotificationManager(
            NotificationService primaryService,  // No @Qualifier = gets @Primary
            @Qualifier("emailNotificationService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService,
            @Qualifier("pushNotificationService") NotificationService pushService) {
        
        this.primaryService = primaryService;
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushService = pushService;
    }
}
```

### Step 4: PrimaryQualifierDemo (Main Class)

```java
public class PrimaryQualifierDemo {
    public static void main(String[] args) {
        // Create Spring container
        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(PrimaryQualifierConfig.class);
        
        // Get NotificationManager bean
        NotificationManager manager = context.getBean(NotificationManager.class);
        
        // Use @Primary service (default)
        manager.sendPrimaryNotification("Welcome!");
        
        // Use @Qualifier services (specific)
        manager.sendAllNotifications("Important Update!");
        
        // Close container
        context.close();
    }
}
```

---

## Flow Diagram

```
PrimaryQualifierDemo.java (main)
    |
    v
Creates Spring Container with PrimaryQualifierConfig.class
    |
    v
Spring scans "org.example.primary_qualifier" package
    |
    v
Spring finds @Component classes:
    - EmailNotificationService (@Primary)
    - SmsNotificationService
    - PushNotificationService
    - NotificationManager
    |
    v
Spring creates all beans
    |
    v
Spring injects dependencies into NotificationManager:
    - primaryService → EmailNotificationService (@Primary)
    - emailService → EmailNotificationService (@Qualifier)
    - smsService → SmsNotificationService (@Qualifier)
    - pushService → PushNotificationService (@Qualifier)
    |
    v
PrimaryQualifierDemo gets NotificationManager and uses it
    |
    v
Container closed
```

---

## @Primary vs @Qualifier - Quick Comparison

| Feature | @Primary | @Qualifier |
|---------|----------|------------|
| **Purpose** | Mark default bean | Choose specific bean |
| **When to use** | One preferred implementation | Need specific implementation |
| **How many** | Only one @Primary per interface | Multiple @Qualifier allowed |
| **Syntax** | `@Primary` on class | `@Qualifier("beanName")` on parameter |
| **Example** | Email is default | Need SMS for this feature |

---

## Key Takeaways

1. **@Primary** = Default bean when multiple implementations exist
2. **@Qualifier** = Choose specific bean by name
3. Bean name = Class name with first letter lowercase
4. You can use both in the same class
5. @Qualifier overrides @Primary
6. Constructor injection is the best practice

---

## Running the Application

### Option 1: Using Maven
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.example.primary_qualifier.PrimaryQualifierDemo"
```

### Option 2: Using IDE
- Right-click on `PrimaryQualifierDemo.java`
- Select "Run PrimaryQualifierDemo.main()"

### Expected Output:
```
=== Spring Container Created ===

NotificationManager created with all notification services

--- Using @Primary (Default Service) ---
Email: Welcome to Spring!

--- Using @Qualifier (All Services) ---
Email: Important Update!
SMS: Important Update!
Push Notification: Important Update!

--- Using @Qualifier (SMS Only) ---
SMS: Your OTP is 123456

--- Using @Qualifier (Push Only) ---
Push Notification: New message received

=== Spring Container Closed ===
```

---

## Summary

**Without @Primary and @Qualifier:**
- Spring doesn't know which implementation to inject
- You get an error

**With @Primary:**
- Spring uses the default implementation
- Simple and clean for most cases

**With @Qualifier:**
- You choose exactly which implementation to use
- Powerful when you need multiple implementations
- More specific than @Primary

**Best Practice:**
- Use @Primary for the most common implementation
- Use @Qualifier when you need specific implementations
- Use constructor injection (not field injection)
