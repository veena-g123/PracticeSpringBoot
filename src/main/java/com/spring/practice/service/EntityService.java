package com.spring.practice.service;

import com.spring.practice.Entity.EntityClass;
import com.spring.practice.Entity.User;
import com.spring.practice.dao.EntityRepo;
import com.spring.practice.dao.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class EntityService {
    @Autowired
    EntityRepo e;
    @Autowired
    UserService userService;

    public List<EntityClass> getAllEntities() {
        return e.findAll();
    }

    public EntityClass saveToDB(EntityClass emtity) {
        return e.save(emtity);
    }

    public EntityClass findById(ObjectId id) {
        return e.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteFromDB(ObjectId id,String userName) {
//        e.deleteById(id);
        if(!userName.isEmpty()){
            User user = userService.findByUserName(userName);
            boolean removed= user.getE().removeIf(x->x.getId().equals(id));
            System.out.println("--------------removed-------------->"+removed);
            if(removed){
                System.out.println("---------------------------->");
                userService.saveToDB(user);
                e.deleteById(id);
            }
        }


        return true;
    }

    public List<EntityClass> getByUserName(String userName) {
        User user = userService.findByUserName(userName);
        return user.getE();
    }

    @Transactional
    public void addEntityToUser(String userName, EntityClass entotyClass) {
        try{
            User user = userService.findByUserName(userName);
            entotyClass.setTime(LocalDateTime.now());
            boolean isThere = user.getE().stream().filter(Objects::nonNull).noneMatch(id->id.getId().equals(entotyClass.getId()));
                EntityClass saved = e.save(entotyClass);
            if(!isThere){
//            user.setUserName(null);
                if(user != null){
                    user.getE().add(saved);
                }
                userService.saveToDB(user);
            }

        } catch (Exception e){
            System.out.println("message : "+e.getMessage());
//            e.printStackTrace();
        }

    }
}
