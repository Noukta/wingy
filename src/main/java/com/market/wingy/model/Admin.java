package com.market.wingy.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "admins")
public class Admin extends User {
    private boolean isSuperAdmin;
}