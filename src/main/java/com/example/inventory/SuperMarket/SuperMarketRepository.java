package com.example.inventory.SuperMarket;

import com.example.inventory.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SuperMarketRepository extends JpaRepository<SuperMarketItem, Long> {

    SuperMarketItem findSuperMarketItemBySpItemId(Long id);

    List<SuperMarketItem> findSuperMarketItemByDateAdded(Date date);


}
