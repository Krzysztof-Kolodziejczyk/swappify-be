package com.example.swappify.service;

import com.example.swappify.model.entity.Item;
import com.example.swappify.model.entity.User;
import com.example.swappify.repository.ItemRepository;
import com.example.swappify.repository.UserRepository;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import com.example.swappifyapimodel.model.dto.UserDTO;
import com.example.swappifyapimodel.model.exceptions.ItemNotFoundException;
import com.example.swappifyapimodel.model.exceptions.UserNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private static final BigDecimal priceOffset = BigDecimal.valueOf(50);

    public void addItem(@NonNull String username, @NonNull ItemDTO item) {

        userRepository.findByUsername(username)
                .map(user -> buildItem(user, item))
                .ifPresent(itemRepository::save);
    }

    public List<ItemDTO> getAll(@NonNull String username) {
        return itemRepository.getAll(username).stream()
                .map(this::buildItemDTO).collect(Collectors.toList());
    }

    private Item buildItem(User user, ItemDTO itemDTO) {
        var item = new Item();
        item.setUuid(itemDTO.getUuid());
        item.setName(itemDTO.getName());
        item.setVendor(user);
        item.setUuid(itemDTO.getUuid());
        item.setPrice(itemDTO.getPrice());
        return item;
    }

    private ItemDTO buildItemDTO(Item item) {
        return new ItemDTO(item.getName(), item.getPrice(), item.getUuid());
    }

    public List<ItemDTO> getAllForLoggedUser(String name) {
        return itemRepository.getAllByVendor_Username(name).stream()
                .map(this::buildItemDTO).collect(Collectors.toList());
    }

    public List<ItemDTO> getMatchedItems(@NonNull String username, @NonNull String likeItemUuid) {
        var root = getItem(likeItemUuid);
        return itemRepository.getByUuid(likeItemUuid)
                .map(it -> itemRepository.getItemsInPriceInterval(username, it.getPrice(), priceOffset)
                        .stream().filter(item -> {
                            return !item.getLikes().contains(root);
                        })
                        .map(this::buildItemDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(ItemNotFoundException::new);
    }

    @Transactional
    public boolean likeItem(@NonNull String itemUuid, @NonNull String likeItemUuid) {
        var item = getItem(itemUuid);
        var likedItem = getItem(likeItemUuid);
        likedItem.getLikes().add(item);
        if (item.getLikes().contains(likedItem)) {
            item.getMatches().add(likedItem);
            return true;
        }
        return false;
    }

    public List<ItemDTO> getLikedItems(@NonNull final String itemUuid) {
        var item = getItem(itemUuid);
        return itemRepository.getLikedItems(item.getId()).stream().map(this::buildItemDTO)
                .collect(Collectors.toList());
    }

    public List<ItemDTO> getMatchItems(@NonNull final String itemUuid) {
        var item = getItem(itemUuid);
        return itemRepository.getSpecMatchedItem(item.getId()).stream().map(this::buildItemDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMatch(@NonNull final String itemUuid, @NonNull final String matchedItemUuid) {
        var item = getItem(itemUuid);
        var matchedItem = getItem(matchedItemUuid);
        itemRepository.deleteMatch(item.getId(), matchedItem.getId());
    }

    public UserDTO getUserDetails(@NonNull final String itemUuid) {
        var item = getItem(itemUuid);
        var user = item.getVendor();
        return new UserDTO(user.getEmail(), user.getUsername());
    }

    private Item getItem(String uuid) {
        return itemRepository.getByUuid(uuid).orElseThrow(ItemNotFoundException::new);
    }
}
