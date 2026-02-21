package org.example.unidirectional;

import jakarta.persistence.*;

@Entity(name = "UniCourse")
@Table(name = "uni_courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;

    // UNIDIRECTIONAL: Course does NOT have any reference to Student
    // Navigation is ONE-WAY only (Student -> Course)

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
}
