package com.spring.practice.dao;

import com.spring.practice.Entity.EntityClass;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityRepo extends MongoRepository<EntityClass, ObjectId> {
}
