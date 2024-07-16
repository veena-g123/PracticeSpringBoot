package com.spring.practice.service;

import com.spring.practice.Entity.User;
import com.spring.practice.dao.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepo userRepo;
    PasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
    public List<User> getAllEntities() {
        return userRepo.findAll();
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User findById(ObjectId id) {
        return userRepo.findById(id).orElseGet(null);
    }

    public void deleteFromDB(ObjectId id) {
         userRepo.deleteById(id);
    }

    public User saveToDB(User user) {

       return userRepo.save(user);
    }
    public User saveNewUser(User user){
        try{
            user.setPassword(pwdEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            return userRepo.save(user);
        } catch (Exception e){
            log.error("cant be added {} ",user.getUserName(),e);
            log.info("not adding");
            log.debug("debugging................");
            e.printStackTrace();
        }

      return null;
    }

    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public void deleteFromDBByUserName(String name) {
        userRepo.deleteByUserName(name);
//        userRepo.
    }
    public List<User> getAllUsers() {
        return userRepo.findAll();
//        userRepo.
    }
}
