package com.example.inventory.Vendor;


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
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VendorService vendorService;


    @Operation(
            summary = "Returns item to vendor",
            description = "Allows the return of items to vendors"
    )
    @PostMapping("/return/item")
    public ResponseEntity<String> returnItemToVendor(@RequestBody HashMap<String, String> requestData){
        Long itemID = Long.valueOf(requestData.get("id"));
        String reasons = requestData.get("reason");
        Item itemToBeReturned = itemRepository.findItemByItemId(itemID);
        vendorService.returnItemToVendor(new VendorItem(itemToBeReturned,reasons));

        return ResponseEntity.status(HttpStatus.CREATED).body("item returned");


    }
    @Operation(
            summary = "Return Multiple items to vendor",
            description = "Allows the return of multiple items to vendor"
    )
    @PostMapping("/return/items/multiple")
    public ResponseEntity<String> returnItemsToVendor(@RequestBody List<HashMap<String, String>> items){
        items.forEach(item ->{
            Item itemToBeReturned = itemRepository.findItemByItemId(Long.valueOf(item.get("id")));
            String reasons = item.get("reason");
            vendorService.returnItemToVendor(new VendorItem(itemToBeReturned, reasons));
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(items.size()+" items returned");


    }

    @Operation(
            summary = "Shows items that have been returned during the last 30 days",
            description = "Shows items that have been returned during the last 30 days"
    )

    @GetMapping("return/items/last30days")
    public ResponseEntity<List<VendorItem>> itemsReturnedInLastMonth() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.getItemsReturnedInLastOneMonth());
    }
}
