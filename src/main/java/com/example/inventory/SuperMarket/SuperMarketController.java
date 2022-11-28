package com.example.inventory.SuperMarket;


import com.example.inventory.Item.Item;
import com.example.inventory.Item.ItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/supermarket")
public class SuperMarketController {
    @Autowired
    private SuperMarketService superMarketService;
    @Autowired
    private ItemRepository itemRepository;


    @Operation(
            summary = "Add Item to Supermarket",
            description = "Allows adding of an item from store to supermarket"
    )

    @PostMapping("/add")
    public ResponseEntity<String> addItemToSuperMarketStore(@RequestBody HashMap<String, String> requestData){
        Long itemID = Long.valueOf(requestData.get("id"));
        String remarks = requestData.get("remarks");
        Item itemToBeAdded = itemRepository.findItemByItemId(itemID);
         if(itemToBeAdded==null){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The item does not exist in the store");
         }
         superMarketService.saveItemToSupermarket(itemToBeAdded,remarks);
         return ResponseEntity.status(HttpStatus.CREATED).body(itemToBeAdded.getName()+ " has been moved to the supermarket");
    }

    @Operation(
            summary = "Get Item from Supermarket",
            description = "Allows retrieval of an item from store to supermarket"
    )
   @GetMapping("/get")
    public ResponseEntity<SuperMarketItem> getItemInSuperMarketStore(@RequestBody HashMap<String, String> requestData){
        Long itemID = Long.valueOf(requestData.get("id"));
        SuperMarketItem itemSearched = superMarketService.getSuperMarketItem(itemID);
        return ResponseEntity.status(HttpStatus.OK).body(itemSearched);

    }

    @Operation(
            summary = "Add Multiple to Supermarket",
            description = "Allows adding of multiple  items from store to supermarket"
    )
    @PostMapping("/add/multiple")
    public ResponseEntity<String> addListItemsToSuperMarketStore(@RequestBody List<HashMap<String, String>> items){
        items.forEach(item -> {superMarketService.saveItemToSupermarket(
                itemRepository.findItemByItemId(Long.valueOf(item.get("id"))),
                item.get("remarks")
        );});

        return ResponseEntity.status(HttpStatus.CREATED).body(items.size()+ " items added to supermarket");

    }


    @Operation(
            summary = "Removes Item from Supermarket",
            description = "Allows removal of an item from supermarket"
    )
    @PostMapping("/remove")
    public ResponseEntity<String> removeSuperMarketItem(@RequestBody HashMap<String, String> requestData){
        Long superMarketItemID = Long.valueOf(requestData.get("id"));
        SuperMarketItem itemToBeDeleted = superMarketService.getSuperMarketItem(superMarketItemID);
        superMarketService.removeItemFromSuperMarket(itemToBeDeleted);
        return ResponseEntity.status(HttpStatus.OK).body("Item removed successfully");

    }

    @Operation(
            summary = "Get Items Added to Supermarket Today",
            description = "Allows viewing items added today"
    )
    @GetMapping("/today")
    public ResponseEntity<List<SuperMarketItem>> itemsAddedToday() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK).body(superMarketService.getItemsAddedToday());

    }

    @Operation(
            summary = "Removes Multiple items from Supermarket",
            description = "Allows removal of an item from supermarket"
    )
    @PostMapping("/remove/multiple")
    public ResponseEntity<String> removeMultipleItemsFromSuperMarketStore(@RequestBody List<HashMap<String, String>> items){
        items.forEach(item -> {superMarketService.removeItemFromSuperMarket(
                superMarketService.getSuperMarketItem(Long.valueOf(item.get("id")))
        );});

        return ResponseEntity.status(HttpStatus.CREATED).body(items.size()+ " items removed from supermarket");

    }


}
