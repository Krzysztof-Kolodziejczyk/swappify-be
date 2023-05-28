package com.example.swappify.model.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "likes",
            joinColumns = { @JoinColumn(name = "item_id") },
            inverseJoinColumns = { @JoinColumn(name = "liked_item_id") }
    )
    Set<Item> likes = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "matches",
            joinColumns = { @JoinColumn(name = "item_id") },
            inverseJoinColumns = { @JoinColumn(name = "matched_item_id") }
    )
    Set<Item> matches = new HashSet<>();

}
