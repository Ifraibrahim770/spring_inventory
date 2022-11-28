package com.example.inventory.Vendor;

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
public class VendorItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spItemId;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name= "ITEMID" ,referencedColumnName = "ITEM_ID")
    private Item item;
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    private String reasonsReturned;

    public VendorItem(Item item,  String reasonsReturned) {
        this.item = item;
        this.reasonsReturned = reasonsReturned;
    }

    @PrePersist
    private void onCreate() {
        dateAdded = new Date();
    }
}
