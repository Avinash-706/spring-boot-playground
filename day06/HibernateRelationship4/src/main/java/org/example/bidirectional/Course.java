package org.example.bidirectional;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "BiCourse")
@Table(name = "bi_courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    // BIDIRECTIONAL: Course has reference back to Student
    // mappedBy indicates this is the inverse side
    // Navigation is TWO-WAY (Student <-> Course)
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    public Course() {}

    public Course(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
