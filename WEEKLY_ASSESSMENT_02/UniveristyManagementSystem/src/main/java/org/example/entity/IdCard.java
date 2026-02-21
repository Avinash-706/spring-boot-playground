package org.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "idcard")
public class IdCard {
    
    @Id
    @GeneratedValue(generator = "idcard-id-generator")
    @GenericGenerator(name = "idcard-id-generator", strategy = "increment")
    @Column(name = "id")
    private Long id;
    
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @OneToOne(mappedBy = "idCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Student student;
    
    public IdCard() {
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
}
