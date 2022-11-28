package com.example.inventory.Item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

//Shows all items added to the store
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ITEM_ID")
    private Long itemId;
    @NotBlank(message = "The name is required.")
    private String name;
    @NotNull(message = "The unit price is required.")
    private BigDecimal unitPrice;

    @NotNull(message = "The quantity  is required.")
    private int quantity;
    @Temporal(TemporalType.DATE)
    private Date dateAdded;



    public Item(String name, BigDecimal unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.dateAdded = new Date();
    }
    @PrePersist
    private void onCreate() {
        dateAdded = new Date();
    }

}

