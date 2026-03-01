# Spring Dependency Injection - Banking Loan System

## What is Dependency Injection (DI)?

Dependency Injection is when Spring creates objects for you and automatically connects them together. You don't use `new` keyword - Spring does it.

**Without DI (Manual way):**
```java
LoanValidator validator = new IncomeValidator();
AuditService audit = new AuditService();
LoanService service = new LoanService(validator);
service.setAuditService(audit);
```

**With DI (Spring way):**
```java
LoanService service = context.getBean(LoanService.class);
// Spring already created everything and connected it!
```

## Why Use Interface (LoanValidator) Instead of Concrete Class?

### The Problem Without Interface

If you directly use concrete classes:
```java
@Component
public class LoanService {
    private IncomeValidator validator;  // Locked to IncomeValidator only!
    
    public LoanService(IncomeValidator validator) {
        this.validator = validator;
    }
}
```

Now you're stuck with IncomeValidator. To switch to CreditScoreValidator, you must:
1. Change the field type
2. Change the constructor parameter
3. Recompile the code

### The Solution With Interface

```java
@Component
public class LoanService {
    private LoanValidator validator;  // Can be ANY validator!
    
    public LoanService(LoanValidator validator) {
        this.validator = validator;
    }
}
```

Now you can switch validators by:
1. Just changing `@Qualifier` annotation
2. No code changes needed
3. More flexible and testable

**This is called "Programming to Interface, not Implementation"**

## Why Interface Doesn't Need @Component?

```java
public interface LoanValidator {
    void validateLoan(double amount);
}
```

Interfaces are just contracts - they can't be instantiated. You can't do `new LoanValidator()`.

Spring only creates beans from concrete classes:
- `@Component` on CreditScoreValidator → Spring creates CreditScoreValidator object
- `@Component` on IncomeValidator → Spring creates IncomeValidator object
- No `@Component` on interface → Nothing to create, it's just a contract

When Spring sees:
```java
public LoanService(LoanValidator validator)
```

It looks for all classes that:
1. Implement LoanValidator
2. Have @Component (or @Service, @Repository, etc.)
3. Then picks one based on @Primary or @Qualifier

## How Spring Container Works

### Step 1: Component Scanning
```java
@Configuration
@ComponentScan(basePackages = "org.example")
public class BankAppConfig { }
```

Spring scans `org.example` package and finds:
- CreditScoreValidator (@Component) ✓
- IncomeValidator (@Component) ✓
- LoanService (@Component) ✓
- AuditService (@Component) ✓
- LoanValidator (no @Component) ✗ Skip

### Step 2: Bean Creation Order

Spring creates beans in dependency order:

1. **CreditScoreValidator** - no dependencies, create immediately
2. **IncomeValidator** - no dependencies, create immediately
3. **AuditService** - no dependencies, but @Lazy, skip for now
4. **LoanService** - needs LoanValidator, which one?
   - Sees `@Qualifier("incomeValidator")` → inject IncomeValidator
   - Needs AuditService → create it now (lazy initialization triggered)

### Step 3: Dependency Resolution

```java
@Component
public class LoanService {
    private final LoanValidator loanValidator;
    private AuditService auditService;

    @Autowired
    public LoanService(@Qualifier("incomeValidator") LoanValidator loanValidator) {
        this.loanValidator = loanValidator;  // Spring injects IncomeValidator here
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;  // Spring calls this method with AuditService
    }
}
```

## @Primary vs @Qualifier - The Complete Picture

### @Primary - The Default Choice

```java
@Component
@Primary
public class CreditScoreValidator implements LoanValidator { }

@Component
public class IncomeValidator implements LoanValidator { }
```

When you ask for `LoanValidator` without specifying which one:
```java
LoanValidator validator = context.getBean(LoanValidator.class);
// Spring returns CreditScoreValidator (the @Primary one)
```

### @Qualifier - The Override

```java
@Autowired
public LoanService(@Qualifier("incomeValidator") LoanValidator validator) {
    // @Qualifier overrides @Primary
    // Spring injects IncomeValidator, not CreditScoreValidator
}
```

### How @Qualifier Overrides @Primary - The Mechanism

**Spring's Bean Selection Priority:**
1. **@Qualifier specified?** → Use that bean (highest priority)
2. **@Primary exists?** → Use the @Primary bean
3. **Only one bean?** → Use that bean
4. **Multiple beans, no @Primary, no @Qualifier?** → ERROR

**Example in our code:**

```java
// In App.java - Direct getBean (no @Qualifier)
LoanValidator validator = context.getBean(LoanValidator.class);
// Result: CreditScoreValidator (because @Primary)

// In LoanService - Constructor injection with @Qualifier
@Autowired
public LoanService(@Qualifier("incomeValidator") LoanValidator validator) {
    this.loanValidator = validator;
}
// Result: IncomeValidator (because @Qualifier overrides @Primary)
```

**Why this matters:**
- `@Primary` sets the default for the entire application
- `@Qualifier` lets specific classes override that default
- This gives you flexibility: most places use the default, but specific places can use alternatives

**Real scenario:**
```java
// 90% of app uses CreditScoreValidator (@Primary)
LoanValidator v1 = context.getBean(LoanValidator.class);  // CreditScoreValidator

// But LoanService specifically needs IncomeValidator
@Component
public class LoanService {
    @Autowired
    public LoanService(@Qualifier("incomeValidator") LoanValidator validator) {
        // Gets IncomeValidator despite @Primary on CreditScoreValidator
    }
}
```

### Bean Names

By default, Spring creates bean names from class names:
- `CreditScoreValidator` → bean name: `creditScoreValidator` (first letter lowercase)
- `IncomeValidator` → bean name: `incomeValidator`

You use these names in `@Qualifier`.

### Multiple Ways to Get Beans

```java
// Way 1: Get @Primary bean
LoanValidator v1 = context.getBean(LoanValidator.class);
// Returns: CreditScoreValidator

// Way 2: Get specific bean by name
LoanValidator v2 = context.getBean("incomeValidator", LoanValidator.class);
// Returns: IncomeValidator

// Way 3: Get specific bean by name (alternative)
LoanValidator v3 = context.getBean("creditScoreValidator", LoanValidator.class);
// Returns: CreditScoreValidator

// Way 4: In dependency injection with @Qualifier
@Autowired
public LoanService(@Qualifier("incomeValidator") LoanValidator validator) {
    // Gets: IncomeValidator (overrides @Primary)
}
```

## Constructor vs Setter Injection

### Constructor Injection (Recommended)
```java
private final LoanValidator loanValidator;  // final = immutable

@Autowired
public LoanService(@Qualifier("incomeValidator") LoanValidator loanValidator) {
    this.loanValidator = loanValidator;
}
```

**Pros:**
- Field is `final` - can't be changed after creation
- Required dependency - Spring fails if not available
- Better for testing

### Setter Injection
```java
private AuditService auditService;  // not final

@Autowired
public void setAuditService(AuditService auditService) {
    this.auditService = auditService;
}
```

**Pros:**
- Optional dependency - can be null
- Can be changed later
- Useful for circular dependencies

## Bean Scopes

### Singleton (Default)
```java
@Component
public class CreditScoreValidator implements LoanValidator { }
```

Spring creates ONE instance for entire application:
```java
LoanValidator v1 = context.getBean(LoanValidator.class);
LoanValidator v2 = context.getBean(LoanValidator.class);
System.out.println(v1 == v2);  // true - same object
```

### Prototype
```java
@Component
@Scope("prototype")
public class IncomeValidator implements LoanValidator { }
```

Spring creates NEW instance every time:
```java
LoanValidator v1 = context.getBean("incomeValidator", LoanValidator.class);
LoanValidator v2 = context.getBean("incomeValidator", LoanValidator.class);
System.out.println(v1 == v2);  // false - different objects
```

## Bean Lifecycle Callbacks

```java
@Component
@Lazy
public class AuditService {
    
    @PostConstruct
    public void init() {
        System.out.println("Bean created, dependencies injected");
        // Initialize resources, open connections, etc.
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Bean about to be destroyed");
        // Cleanup resources, close connections, etc.
    }
}
```

**Lifecycle:**
1. Constructor called
2. Dependencies injected (via constructor/setter)
3. `@PostConstruct` method called
4. Bean ready to use
5. Application shutting down
6. `@PreDestroy` method called
7. Bean destroyed

## @Lazy Loading

### Without @Lazy (Eager)
```java
@Component
public class AuditService { }
```
Spring creates bean at startup, even if never used.

### With @Lazy
```java
@Component
@Lazy
public class AuditService { }
```
Spring creates bean only when first accessed:
```java
// AuditService NOT created yet
LoanService service = context.getBean(LoanService.class);
// NOW AuditService is created (because LoanService needs it)
```

## When to Change @Qualifier Manually?

You change `@Qualifier` when you want different validation logic:

### Scenario 1: Income-Based Validation
```java
@Autowired
public LoanService(@Qualifier("incomeValidator") LoanValidator validator) {
    // Uses income validation rules
}
```

### Scenario 2: Credit-Based Validation
```java
@Autowired
public LoanService(@Qualifier("creditScoreValidator") LoanValidator validator) {
    // Uses credit score validation rules
}
```

### Scenario 3: Use Default (@Primary)
```java
@Autowired
public LoanService(LoanValidator validator) {
    // No @Qualifier → uses @Primary (CreditScoreValidator)
}
```

**Real-world example:**
- Development environment → use IncomeValidator (simpler rules)
- Production environment → use CreditScoreValidator (strict rules)

You can even use Spring Profiles to switch automatically:
```java
@Profile("dev")
@Component
public class IncomeValidator implements LoanValidator { }

@Profile("prod")
@Component
public class CreditScoreValidator implements LoanValidator { }
```

## Complete Flow Diagram

```
Application Start
    ↓
@ComponentScan finds all @Component classes
    ↓
Spring creates beans:
    ├─ CreditScoreValidator (singleton, @Primary)
    ├─ IncomeValidator (prototype)
    └─ LoanService needs:
        ├─ LoanValidator → @Qualifier says "incomeValidator" → inject IncomeValidator
        └─ AuditService → @Lazy, create now → @PostConstruct called
    ↓
context.getBean(LoanService.class)
    ↓
LoanService.processLoan(50000)
    ├─ AuditService.logLoanRequest() called
    └─ IncomeValidator.validateLoan() called
    ↓
context.getBean(LoanValidator.class)
    └─ Returns CreditScoreValidator (@Primary)
    ↓
context.close()
    └─ AuditService @PreDestroy called
```

## Key Takeaways

1. **Interfaces define contracts** - concrete classes implement them
2. **@Component makes Spring manage the class** - creates and injects it
3. **@Primary is the default** - used when no @Qualifier specified
4. **@Qualifier overrides @Primary** - explicitly choose which bean
5. **Constructor injection for required dependencies** - use final
6. **Setter injection for optional dependencies** - can be null
7. **Singleton = one instance** - shared across application
8. **Prototype = new instance** - created every time
9. **@Lazy delays creation** - until first use
10. **@PostConstruct/@PreDestroy** - lifecycle hooks

## Common Questions

### Q: Why not just use `new IncomeValidator()`?
A: Then you lose all Spring benefits - no DI, no lifecycle management, no easy switching between implementations.

### Q: Can I have multiple @Primary beans?
A: No, Spring will throw an error. Only one @Primary per interface/type.

### Q: What if I don't use @Qualifier and there's no @Primary?
A: Spring throws an error - it doesn't know which bean to inject.

### Q: Can I inject by concrete class instead of interface?
A: Yes, but you lose flexibility. You can't easily switch implementations.

### Q: When is @Autowired optional?
A: Since Spring 4.3, if a class has only one constructor, @Autowired is optional.

### Q: What's the difference between @Component, @Service, @Repository?
A: Semantically different, but technically same. Use @Service for business logic, @Repository for data access, @Component for general beans.
