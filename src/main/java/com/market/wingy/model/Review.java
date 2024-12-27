package com.market.wingy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
public class Review {
    @Id
    private ObjectId id;
    @DocumentReference
    private Shop shop;
    @DocumentReference
    private Customer customer;
    private int rating;
    private String content;
    private long helpful;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}