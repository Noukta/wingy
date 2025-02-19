package com.market.wingy.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private ObjectId id;
    @DocumentReference
    private Shop shop;

    private String name;
    private String image;
    private String description;
    private List<Choice> sizes;
    private String category;
    private boolean isAvailable;
    private List<Option> options;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private String title;
        private List<Choice> choices = new ArrayList<>();
        //private int maxSelect = 1;
        private boolean isRequired = false;
    }
}