package com.spring.practice.dao;


import com.spring.practice.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    public User findByUserName(String userName);
    public User deleteByUserName(String userName);
}
