package com.example.inventory.Item;

import com.example.inventory.Vendor.VendorItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findItemByDateAdded(Date today);

    Item findItemByItemId(Long id);
    @Transactional
    @Modifying
    @Query("update Item c set c.name = ?1, c.quantity = ?2, c.unitPrice =?3 where c.itemId = ?4")
    void updateItem (String name, int quantity, BigDecimal unitPrice, Long Id);

    @Query("select a from Item a where a.dateAdded  between ?1 and ?2")
    List<Item> getItemsAddedInLastOneWeek(Date startDate, Date endDate);


}
