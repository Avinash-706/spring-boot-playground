# 🍕 Online Food Delivery System - Spring Dependency Injection Demo

A comprehensive Spring Framework application demonstrating advanced dependency injection concepts including `@Primary`, `@Qualifier`, `@Lazy`, multiple DI types, bean scopes, and lifecycle management.

## 📋 Project Overview

This project simulates a food delivery system with the following services:
- **Restaurant Service** - Processes orders
- **Delivery Service** - Handles deliveries
- **Notification Service** - Sends notifications (Email & SMS)
- **Order Service** - Coordinates the entire order flow

## 🎯 Concepts Demonstrated

### 1. Multiple Implementations with @Primary
- `NotificationService` interface has two implementations
- `EmailNotification` is marked as `@Primary` (default)
- `SmsNotification` is a regular component

### 2. Dependency Injection Types
- **Constructor Injection**: `OrderService` → `NotificationService`
- **Setter Injection**: `RestaurantService` → `DeliveryService`
- **Field Injection**: `OrderService` → `RestaurantService`

### 3. @Qualifier Override
- `OrderService` uses `@Qualifier("smsNotification")` to override the `@Primary` bean
- Demonstrates explicit bean selection

### 4. @Lazy Loading
- `SmsNotification` is marked as `@Lazy`
- Bean is created only when first requested, not at startup

### 5. Bean Scope
- `DeliveryService` uses `@Scope("singleton")`
- Single instance shared across the application

### 6. Lifecycle Management
- `DeliveryService` has `@PostConstruct` and `@PreDestroy` methods
- Demonstrates bean initialization and cleanup

## 📁 Project Structure

```
src/main/java/org/example/
├── App.java                          # Main application with tests
├── notification/
│   ├── NotificationService.java      # Interface
│   ├── EmailNotification.java        # @Primary implementation
│   └── SmsNotification.java          # @Lazy implementation
└── utility/
    ├── AppConfig.java                # @Configuration with @ComponentScan
    ├── DeliveryService.java          # @Scope("singleton") with lifecycle
    ├── ResturantService.java         # Uses Setter Injection
    └── OrderService.java             # Uses Constructor + Field Injection
```

## 🔧 Implementation Details

### NotificationService Interface
```java
public interface NotificationService {
    void sendNotification(String message);
}
```

### EmailNotification (Default - @Primary)
```java
@Primary
@Component
public class EmailNotification implements NotificationService {
    // Default notification service
}
```

### SmsNotification (@Lazy)
```java
@Component
@Lazy
public class SmsNotification implements NotificationService {
    // Loaded only when explicitly requested
}
```

### DeliveryService (Lifecycle + Scope)
```java
@Component
@Scope("singleton")
public class DeliveryService {
    @PostConstruct
    public void init() {
        System.out.println("Delivery Service Ready");
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("Delivery Service Closed");
    }
}
```

### RestaurantService (Setter Injection)
```java
@Component
public class ResturantService {
    private DeliveryService deliveryService;
    
    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
}
```

### OrderService (Constructor + Field Injection + @Qualifier)
```java
@Component
public class OrderService {
    private final NotificationService notificationService;
    
    @Autowired
    private ResturantService resturantService;
    
    public OrderService(@Qualifier("smsNotification") NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

### AppConfig (Configuration)
```java
@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {
}
```

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application

1. **Using Maven:**
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="org.example.App"
   ```

2. **Using Maven Wrapper (if available):**
   ```bash
   ./mvnw clean compile exec:java -Dexec.mainClass="org.example.App"
   ```

3. **Using IDE:**
   - Open the project in IntelliJ IDEA or Eclipse
   - Run `App.java` as a Java Application

## 🧪 Testing Scenarios

The application demonstrates:

1. **Bean Creation Timing**
   - EmailNotification loads eagerly at startup
   - SmsNotification loads lazily when first requested

2. **Default vs Qualifier Resolution**
   - Default bean resolution uses `@Primary` (EmailNotification)
   - `@Qualifier` overrides `@Primary` in OrderService

3. **Dependency Injection Types**
   - All three DI types working together
   - Proper initialization order

4. **Lifecycle Management**
   - `@PostConstruct` called after bean creation
   - `@PreDestroy` called before context shutdown

5. **Bean Scope**
   - Singleton scope ensures single instance

## 📚 Key Annotations Used

| Annotation | Purpose | Location |
|------------|---------|----------|
| `@Component` | Mark class as Spring bean | All service classes |
| `@Configuration` | Define configuration class | AppConfig |
| `@ComponentScan` | Enable component scanning | AppConfig |
| `@Primary` | Mark default implementation | EmailNotification |
| `@Qualifier` | Specify bean by name | OrderService constructor |
| `@Lazy` | Lazy bean initialization | SmsNotification |
| `@Scope` | Define bean scope | DeliveryService |
| `@Autowired` | Enable dependency injection | Setter and field injection |
| `@PostConstruct` | Post-initialization callback | DeliveryService |
| `@PreDestroy` | Pre-destruction callback | DeliveryService |

## 🎓 Learning Outcomes

After studying this project, you'll understand:
- How to handle multiple implementations of the same interface
- When and how to use `@Primary` vs `@Qualifier`
- The differences between Constructor, Setter, and Field injection
- How `@Lazy` affects bean creation timing
- Bean scopes and their implications
- Lifecycle management with `@PostConstruct` and `@PreDestroy`
- Spring's component scanning and auto-configuration

## 📝 Dependencies

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>7.0.3</version>
</dependency>

<dependency>
    <groupId>jakarta.annotation</groupId>
    <artifactId>jakarta.annotation-api</artifactId>
    <version>3.0.0</version>
</dependency>
```

## 🤝 Contributing

This is an educational project. Feel free to fork and experiment with different Spring concepts!

## 📄 License

This project is created for educational purposes.
