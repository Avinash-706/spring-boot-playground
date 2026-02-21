package org.example.bidirectional;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "BiStudent")
@Table(name = "bi_students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // BIDIRECTIONAL: Student knows about Course (owning side)
    // Course also knows about Student (inverse side with mappedBy)
    @ManyToMany
    @JoinTable(
        name = "bi_student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public Student() {}

    public Student(String name) {
        this.name = name;
    }

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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
