package com.example.SpringMvc.Controller;

import com.example.SpringMvc.Model.Student;
import com.example.SpringMvc.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model){
        studentService.saveStudent(student);
        model.addAttribute("name", student.getName());
        return "success";
    }

    @GetMapping("/register")
    public  String showForm(){
        return "register";
    }
}
