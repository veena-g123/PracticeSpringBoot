package com.spring.practice.controller;

import com.spring.practice.Entity.EntityClass;
import com.spring.practice.Entity.User;
import com.spring.practice.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    UserService userService;




    @GetMapping("id/{id}")
    public User getById(@PathVariable ObjectId id){

        return userService.findById(id);
    }
    @DeleteMapping
    public ResponseEntity delete(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteFromDBByUserName(auth.getName());

        return new ResponseEntity("user deleted succesfuullyy", HttpStatus.NO_CONTENT   );
    }

    @PutMapping("editByUserName")
    public User editByUserName(@RequestBody User e){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findByUserName(userName);
        e.setUserName(userName);
        if(user!= null){
            user.setUserName(!e.getUserName().isEmpty() ? e.getUserName():user.getUserName());
            user.setPassword(!e.getPassword().isEmpty() ? new BCryptPasswordEncoder().encode(e.getPassword()):user.getPassword());
//            user

        }
        return userService.saveToDB(user);
    }

    @PostMapping("add-user")
    public User addAdminUser(@RequestBody User user){

        return userService.saveNewUser(user);
    }
}
