package com.example.swappify.service;

import com.example.swappify.model.entity.Item;
import com.example.swappify.model.entity.User;
import com.example.swappify.repository.ItemRepository;
import com.example.swappify.repository.UserRepository;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    public void addItem(@NonNull String username, @NonNull ItemDTO item){
        userRepository.findByUsername(username)
                .map(user -> buildItem(user, item))
                .ifPresent(itemRepository::save);
    }

    private Item buildItem(User user, ItemDTO itemDTO){
        var item = new Item();
        item.setUuid(itemDTO.getUuid());
        item.setName(itemDTO.getName());
        item.setVendor(user);
        item.setUuid(itemDTO.getUuid());
        item.setPrice(itemDTO.getPrice());
        return item;
    }

}
