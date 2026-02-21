package org.example.unidirectional;

import javax.persistence.*;

@Entity
@Table(name = "department_uni")
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dept_seq")
    @SequenceGenerator(name = "dept_seq", sequenceName = "department_sequence", initialValue = 10, allocationSize = 10)
    private Long id;
    
    @Column(name = "deptname")
    private String deptname;
    
    public Department() {
    }
    
    public Department(String deptname) {
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
    
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptname='" + deptname + '\'' +
                '}';
    }
}
