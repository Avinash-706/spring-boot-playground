package org.example.unidirectional;

import javax.persistence.*;

@Entity
@Table(name = "employee_uni")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @SequenceGenerator(name = "emp_seq", sequenceName = "employee_sequence", initialValue = 101, allocationSize = 1)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    public Employee() {
    }
    
    public Employee(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + (department != null ? department.getDeptname() : "null") +
                '}';
    }
}
