package org.example.bidirectional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department_bi")
public class DepartmentBi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dept_bi_seq")
    @SequenceGenerator(name = "dept_bi_seq", sequenceName = "department_bi_sequence", initialValue = 10, allocationSize = 10)
    private Long id;
    
    @Column(name = "deptname")
    private String deptname;
    
    // One-to-Many: One department has many employees (bidirectional)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<EmployeeBi> employees = new ArrayList<>();
    
    // Constructors
    public DepartmentBi() {
    }
    
    public DepartmentBi(String deptname) {
        this.deptname = deptname;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDeptname() {
        return deptname;
    }
    
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    
    public List<EmployeeBi> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<EmployeeBi> employees) {
        this.employees = employees;
    }
    
    // Helper method to add employee
    public void addEmployee(EmployeeBi employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
    
    @Override
    public String toString() {
        return "DepartmentBi{" +
                "id=" + id +
                ", deptname='" + deptname + '\'' +
                ", employeeCount=" + employees.size() +
                '}';
    }
}
