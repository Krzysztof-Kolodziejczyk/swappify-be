package com.example.swappify.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {
    @Id
    private String username;

    private String email;

    private String password;

    private int enabled = 1;
}
