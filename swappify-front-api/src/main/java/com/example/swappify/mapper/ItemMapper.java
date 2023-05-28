package com.example.swappify.mapper;

import com.example.swappify.model.entity.Item;
import com.example.swappify.model.entity.User;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ItemMapper {

    Item map(ItemDTO item, User user);

    ItemDTO map(Item it);
}
