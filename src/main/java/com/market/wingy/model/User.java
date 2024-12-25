package com.market.wingy.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String gender;
    private String email;
    private String phone;
    private boolean emailVerified;
    private boolean phoneVerified;
    private LocalDateTime joined;
    private LocalDateTime lastActivity;
}