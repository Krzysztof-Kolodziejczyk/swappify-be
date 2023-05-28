package com.example.swappify.repository;


import com.example.swappify.model.entity.Item;
import com.example.swappify.model.entity.Token;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.OpaqueTokenDsl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.vendor.username <> :username")
    List<Item> getAll(String username);

    List<Item> getAllByVendor_Username(String username);

    Optional<Item> getByUuid(String uuid);

    @Query("select i from Item i join i.vendor u " +
            "WHERE i.price > (:price - :priceOffset) and i.price < (:price + :priceOffset) " +
            "and u.username <> :username")
    List<Item> getItemsInPriceInterval(String username, BigDecimal price, BigDecimal priceOffset);

    @Query(value = "SELECT i FROM Item i WHERE i.id IN (" +
            "SELECT li.id FROM Item li INNER JOIN li.likes l WHERE l.id = ?1)")
    List<Item> getLikedItems(Long itemId);

    @Query(value = "select * from items i where i.id in (\n" +
            "                SELECT CASE\n" +
            "                          WHEN m.item_id <> ?1 THEN m.item_id\n" +
            "                          ELSE m.matched_item_id\n" +
            "                END AS item_or_matched_item_id\n" +
            "               from items i inner join matches m on i.id = m.item_id\n" +
            "               where m.item_id = ?1 or m.matched_item_id = ?1)", nativeQuery = true)
    List<Item> getSpecMatchedItem(Long itemId);

    @Modifying
    @Query(value = "delete from matches m where (m.item_id = ?1 and m.matched_item_id = ?2) " +
            "or (m.item_id = ?2 and m.matched_item_id = ?1)",
        nativeQuery = true
    )
    void deleteMatch(Long itemId, Long matchedItemId);


}
