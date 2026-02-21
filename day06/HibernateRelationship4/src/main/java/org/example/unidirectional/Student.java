package org.example.unidirectional;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "UniStudent")
@Table(name = "uni_students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // UNIDIRECTIONAL: Only Student knows about Course
    // Course has NO reference back to Student
    @ManyToMany
    @JoinTable(
        name = "uni_student_course",
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
