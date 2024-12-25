package com.market.wingy.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shops")
public class Shop {
    @Id
    private ObjectId id;
    private String name;
    private String cover;
    private String logo;
    private boolean isActivated;
    private Address address;
    private Location location;
    private Set<String> foodTypes = new HashSet<>();
    private Set<String> categories = new HashSet<>();
    private Double avg_rating;
    private Long count_rating;
    private List<Availability> availabilityList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Availability {
        private boolean isOpen;
        private String openingTime;
        private String closingTime;
    }
}