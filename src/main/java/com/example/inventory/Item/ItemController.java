package com.example.inventory.Item;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
import java.util.Map;

@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Operation(
            summary = "Add",
            description = "Add Items to Store Recieved From Vendor"
    )
    @PostMapping("/add")
    public ResponseEntity<String> saveItem(@Valid @RequestBody Item item){
        itemService.saveItemToStore(item);
        String message = item.getName() + " ID:" + item.getItemId() + " added successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Operation(
            summary = "Add Multiple",
            description = "Adds Multiple Items to Store"
    )
    @PostMapping("/add/multiple")
    public ResponseEntity<String> saveListOfItems(@Valid @RequestBody List<Item> itemList){
        itemList.forEach(item -> itemService.saveItemToStore(item));
        String message = itemList.size() + " items have been saved";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Operation(
            summary = "List All Items",
            description = "Returns a List of all items Added to Store"
    )
    @GetMapping("/all")
    public ResponseEntity<List<Item>>getAllItems(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.retrieveAllItems());
    }


    @Operation(
            summary = "Edit Item",
            description = "Allows Editing of Items Already Passed to Store"
    )
    @PostMapping("/edit")
    public ResponseEntity<String> editItem(@Valid @RequestBody Item item){

        if(item.getItemId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provide a valid itemID");
        }

        if (itemService.getItem(item.getItemId())==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The item" +item.getItemId()+ "not found");

        }
        itemService.updateStoreItem(item);
        String message = item.getName() + " ID:" + item.getItemId() + " updated successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    @Operation(
            summary = "Edit Multiple",
            description = "Allows Editing of multiple Items to Store"
    )

    @PostMapping("/edit/multiple")
    public ResponseEntity<String> editListOfItems(@Valid @RequestBody List<Item> itemList){

        int updateCount = 0;
        int items = itemList.size();

        for (Item currentItem : itemList) {

            if (currentItem.getItemId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provide a valid itemID for "+currentItem.getName());
            }
            if (itemService.getItem(currentItem.getItemId()) == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The item" + currentItem.getItemId() + "not found");
            }
            itemService.updateStoreItem(currentItem);
            updateCount++;

        }
        int failedItems = items - updateCount;

        return ResponseEntity.status(HttpStatus.CREATED).body(updateCount+ " items updated "+failedItems +" failed");
    }
    @Operation(
            summary = "Delete Items",
            description = "Allows Deletion of Single Items"
    )
    @PostMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody HashMap<String, String> requestData){
        Long itemID = Long.valueOf(requestData.get("id"));
        Item itemToBeDeleted = itemService.getItem(itemID);
        if (itemToBeDeleted == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The item " + itemID + " not found");
        }
        itemService.deleteStoreItem(itemToBeDeleted);
        String message = itemToBeDeleted.getName() + " ID:" + itemToBeDeleted.getItemId() + " deleted successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @Operation(
            summary = "Delete Multiple Items",
            description = "Allows Deletion of Multiple Items"
    )
    @PostMapping("/delete/multiple")
    public ResponseEntity<String> deleteMultipleItems(@RequestBody List<HashMap<String, String>> requestData){
        int deleteCount = 0;
        int itemsSize = requestData.size();
        for (Map<String, String> currentItem : requestData) {
            Long itemID = Long.valueOf(currentItem.get("id"));

            Item itemToBeDeleted = itemService.getItem(itemID);

            if (itemToBeDeleted == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The item" + itemID+ "not found");
            }
            itemService.deleteStoreItem(itemToBeDeleted);
            deleteCount++;

        }
        int failedItems = itemsSize - deleteCount;

        return ResponseEntity.status(HttpStatus.OK).body(deleteCount+ " items deleted "+failedItems +" failed");
    }
    @Operation(
            summary = "Today",
            description = "Returns All Items Added Today"
    )
    @GetMapping("/today")
    public ResponseEntity<List<Item>> getItemsAddedToday() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.retrieveAllItemsCreatedToday());
    }
    @Operation(
            summary = "Last Week",
            description = "Returns All Items Added Within the Last Week"
    )
    @GetMapping("/last/week")
    public ResponseEntity<List<Item>> getItemsAddedLastOneWeek() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.retrieveAllItemsCreatedLastOneWeek());
    }

}
