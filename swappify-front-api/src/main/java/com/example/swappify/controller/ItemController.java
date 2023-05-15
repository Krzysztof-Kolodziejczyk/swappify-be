package com.example.swappify.controller;

import com.example.swappify.service.ItemService;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import com.example.swappifyauthconnector.connector.ItemConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "item")
@RequiredArgsConstructor
public class ItemController implements ItemConnector {

    private final ItemService service;

    @Override
    public ResponseEntity<Void> addItemMetadata(@NonNull ItemDTO item) {
        service.addItem(SecurityContextHolder.getContext().getAuthentication().getName(), item);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
