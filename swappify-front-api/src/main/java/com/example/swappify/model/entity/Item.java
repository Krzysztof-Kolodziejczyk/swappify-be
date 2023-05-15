package com.example.swappify.model.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    String name;

    @NonNull
    BigDecimal price;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="vendor")
    User vendor;

    @NonNull
    String uuid;
}
