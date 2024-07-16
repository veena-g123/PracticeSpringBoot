package com.spring.practice.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entity")
@Data
public class EntityClass {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime time;



}
