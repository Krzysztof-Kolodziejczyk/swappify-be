package com.example.swappify.repository;


import com.example.swappify.model.entity.Item;
import com.example.swappify.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
