package com.example.swappify.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "Authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User username;

}
