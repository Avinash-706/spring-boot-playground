package org.example.bidirectional;

import javax.persistence.*;

@Entity
@Table(name = "employee_bi")
public class EmployeeBi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_bi_seq")
    @SequenceGenerator(name = "emp_bi_seq", sequenceName = "employee_bi_sequence", initialValue = 101, allocationSize = 1)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    // Many-to-One: Many employees belong to one department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentBi department;
    
    // Constructors
    public EmployeeBi() {
    }
    
    public EmployeeBi(String name) {
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
    
    public DepartmentBi getDepartment() {
        return department;
    }
    
    public void setDepartment(DepartmentBi department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return "EmployeeBi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + (department != null ? department.getDeptname() : "null") +
                '}';
    }
}
