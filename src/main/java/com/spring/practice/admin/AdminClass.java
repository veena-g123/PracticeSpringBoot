package com.spring.practice.admin;

import com.spring.practice.Entity.User;
import com.spring.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminClass {
    @Autowired
    UserService service;
    @GetMapping("getAllUsers")
    public List<User> getAllUsers(){
       return service.getAllUsers();
    }

    @PostMapping("add-admin-user")
    public User addAdminUser(@RequestBody User user){
        return service.saveNewUser(user);
    }

}
