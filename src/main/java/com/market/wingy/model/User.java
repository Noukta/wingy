package com.market.wingy.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements UserDetails {
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
    private String password;
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}