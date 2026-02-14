package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "passport")
public class Passport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Long passportId;
    
    // Passport number will be auto-generated
    @Column(name = "passport_number", unique = true, nullable = false, length = 50)
    private String passportNumber;
    
    @Column(name = "country", nullable = false, length = 100)
    private String country;
    
    @Column(name = "issue_date")
    private LocalDate issueDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    // One-to-One relationship with Person (owning side)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private Person person;

    // BI_DIRRECTIONAL THING
//    @OneToOne(mappedBy = "passport")
//    private Person person;

    // Constructors
    public Passport() {
    }
    
    public Passport(String country, LocalDate issueDate, LocalDate expiryDate) {
        this.country = country;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }
    
    // Getters and Setters
    public Long getPassportId() {
        return passportId;
    }
    
    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }
    
    public String getPassportNumber() {
        return passportNumber;
    }
    
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
    
    // Auto-generate passport number before saving
    @PrePersist
    public void generatePassportNumber() {
        if (this.passportNumber == null) {
            // Generate with prefix PASS- and UUID
            this.passportNumber = "PASS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
    
    @Override
    public String toString() {
        return "Passport{" +
                "passportId=" + passportId +
                ", passportNumber='" + passportNumber + '\'' +
                ", country='" + country + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
