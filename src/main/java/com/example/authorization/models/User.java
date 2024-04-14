package com.example.authorization.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @ManyToMany
    private List<Role> roles;
    private boolean isEmailVerified;

}
