package com.example.authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseModel{
    @ManyToOne
    private User user;
    private String value;
    private Date expiryAt;
    private boolean deleted;
}
