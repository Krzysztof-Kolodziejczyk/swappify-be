package com.example.swappify.controller;

import com.example.swappify.service.ItemService;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import com.example.swappifyapimodel.model.dto.UserDTO;
import com.example.swappifyauthconnector.connector.ItemConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "item")
@RequiredArgsConstructor
public class ItemController implements ItemConnector {

    private final ItemService service;

    @Override
    public ResponseEntity<Void> addItemMetadata(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token, @NonNull ItemDTO item) {
        service.addItem(SecurityContextHolder.getContext().getAuthentication().getName(), item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<ItemDTO> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token) {
        return service.getAll(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/logged-user")
    public List<ItemDTO> getAllForLoggedUser(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token) {
        return service.getAllForLoggedUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/matched/{itemUuid}")
    public List<ItemDTO> getMatchedItems(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token,
                                         @PathVariable @NonNull String itemUuid) {
        return service.getMatchedItems(SecurityContextHolder.getContext().getAuthentication().getName(), itemUuid);
    }

    @GetMapping("/like/{itemUuid}/{likedItemUuid}")
    public Boolean likeItem(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token,
                            @PathVariable("itemUuid") @NonNull String itemUuid,
                            @PathVariable("likedItemUuid") @NonNull String likedItemUuid) {
        return service.likeItem(itemUuid, likedItemUuid);
    }

    @GetMapping("/like/{itemUuid}")
    public List<ItemDTO> getLikedItems(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token,
                                       @PathVariable("itemUuid") @NonNull String itemUuid) {
        return service.getLikedItems(itemUuid);
    }

    @GetMapping("/match/{itemUuid}")
    public List<ItemDTO> getMatchedItemsForSpecific(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token,
                                                    @PathVariable("itemUuid") @NonNull String itemUuid) {
        return service.getMatchItems(itemUuid);
    }

    @DeleteMapping("/match/{itemUuid}/{matchedItemId}")
    public void deleteMatch(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token,
                            @PathVariable("itemUuid") @NonNull String itemUuid,
                            @PathVariable("matchedItemId") @NonNull String matchedItemId) {
        service.deleteMatch(itemUuid, matchedItemId);
    }

    @GetMapping("/user/{itemUuid}")
    public UserDTO getUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) @NonNull String token,
                                  @PathVariable("itemUuid") @NonNull String itemUuid) {
        return service.getUserDetails(itemUuid);
    }
}
