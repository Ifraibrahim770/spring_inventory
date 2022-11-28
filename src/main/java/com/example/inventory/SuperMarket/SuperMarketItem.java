package com.example.inventory.SuperMarket;

import com.example.inventory.Item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SuperMarketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spItemId;

    @OneToOne
    @JoinColumn(name= "ITEMID" ,referencedColumnName = "ITEM_ID")
    private Item item;
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    private String remarks;


    public SuperMarketItem(Item item , String remarks) {
        this.item = item;
        this.remarks = remarks;
    }
    @PrePersist
    private void onCreate() {
        dateAdded = new Date();
    }
}
