package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/home")
//    @RequestMapping(name = "home", method = RequestMethod.GET)
    public  String home(){
        return "home";
    }

    @GetMapping("/users")
    public String listUser(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "userList";
    }

    @GetMapping("/addUser")
    public String showAddUserForm(){
        return "addUser";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userDetail";
    }


    @PostMapping("/addUser")
    public String addUser(@RequestParam("name") String name, @RequestParam("email") String email) {
        Long newId = (long) (Math.random() * 1000);
        userService.addUser(new User(newId, name, email));
        return "redirect:/users";
    }
}
