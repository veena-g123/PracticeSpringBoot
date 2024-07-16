package com.spring.practice.controller;

import com.spring.practice.Entity.User;
import com.spring.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthCheckController {
    @Autowired
    UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }
    @PostMapping
    public User add(@RequestBody User user){
        System.out.println("************");
        return userService.saveNewUser(user);
    }
    @GetMapping
    public List<User> getALL(){
        return userService.getAllEntities();
    }
}
