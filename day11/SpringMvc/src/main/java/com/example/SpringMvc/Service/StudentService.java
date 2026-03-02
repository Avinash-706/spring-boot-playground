package com.example.SpringMvc.Service;


import com.example.SpringMvc.Model.Student;
import com.example.SpringMvc.Repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public void saveStudent(Student student){
        studentDao.save(student);
    }
}