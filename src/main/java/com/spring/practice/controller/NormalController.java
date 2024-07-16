package com.spring.practice.controller;

import com.spring.practice.Entity.EntityClass;
import com.spring.practice.Entity.User;
import com.spring.practice.service.EntityService;
import com.spring.practice.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/url")
public class NormalController {
//    HashMap<Long, EntityClass> map = new HashMap<>();
    @Autowired
    EntityService service;
    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<EntityClass> getALL(){
//        return new ArrayList<>(map.values());
        return service.getAllEntities();
    }

    @PostMapping
    public EntityClass add(@RequestBody EntityClass e){
//        map.put(e.getId(),e);
        e.setTime(LocalDateTime.now());
        return service.saveToDB(e);
    }

    @GetMapping("id/{id}")
    public EntityClass getById(@PathVariable ObjectId id){

//        return map.get(id);
        return service.findById(id);
    }
    @DeleteMapping("id/{id}")
    public boolean delete(@PathVariable ObjectId id){

//        return map.remove(id);
        return service.deleteFromDB(id,"");
    }

    @PutMapping("id/{id}")
    public EntityClass edit(@PathVariable ObjectId id,@RequestBody EntityClass e){
//        return map.put(id,e);
        EntityClass ec = service.findById(id);
        if(ec!= null){
            ec.setContent(e.getContent());
            ec.setTitle(e.getTitle());
        }
        return service.saveToDB(ec);
    }

    @GetMapping("/getByUser")
    public List<EntityClass> getByUser(){
        String userName = getUserNameFromSession();
        return service.getByUserName(userName);

    }
    @PostMapping("/addByUser")
    public ResponseEntity getByUser( @RequestBody EntityClass e){
        System.out.println("*****************");
        String userName = getUserNameFromSession();
      service.addEntityToUser(userName, e);
        return new ResponseEntity<>(e, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteByUser(@PathVariable ObjectId id){
        String userName = getUserNameFromSession();
        service.deleteFromDB(id,userName);
        return new ResponseEntity<>("deleted succesfully", HttpStatus.NO_CONTENT);

    }

    @PutMapping("editByUser/id/{id}")
    public ResponseEntity editByUser(@PathVariable ObjectId id,@RequestBody EntityClass e){

        String userName = getUserNameFromSession();
        System.out.println("------userName-------------"+userName);
        User user = userService.findByUserName(userName);
        List<EntityClass> list = user.getE().stream().filter(i->i.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty()){
            EntityClass ec = service.findById(list.get(0).getId());
            if(ec!= null){
                ec.setContent(e.getContent() != null && !e.getContent().isEmpty() ? e.getContent():ec.getContent());
                ec.setTitle(e.getTitle() != null && !e.getTitle().isEmpty() ? e.getTitle():ec.getTitle());
                ec.setTime(LocalDateTime.now());
            }
            return new ResponseEntity(service.saveToDB(ec),HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    public String getUserNameFromSession(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userName;
    }
}
