package com.market.wingy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "customers")
public class Customer extends User {
    private Address defaultDeliveryAddress;
    private Location currentLocation;
//    private List<ObjectId> favoriteShops = new ArrayList<>();
//    private List<Address> savedAddresses = new ArrayList<>();
}