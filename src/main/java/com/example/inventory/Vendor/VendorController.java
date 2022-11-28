package com.example.inventory.Vendor;


import com.example.inventory.Item.Item;
import com.example.inventory.Item.ItemRepository;
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

    @PostMapping("/return/item")
    public ResponseEntity<String> returnItemToVendor(@RequestBody HashMap<String, String> requestData){
        Long itemID = Long.valueOf(requestData.get("id"));
        String reasons = requestData.get("reason");
        Item itemToBeReturned = itemRepository.findItemByItemId(itemID);
        vendorService.returnItemToVendor(new VendorItem(itemToBeReturned,reasons));

        return ResponseEntity.status(HttpStatus.CREATED).body("item returned");


    }

    @PostMapping("/return/items/multiple")
    public ResponseEntity<String> returnItemsToVendor(@RequestBody List<HashMap<String, String>> items){
        items.forEach(item ->{
            Item itemToBeReturned = itemRepository.findItemByItemId(Long.valueOf(item.get("id")));
            String reasons = item.get("reason");
            vendorService.returnItemToVendor(new VendorItem(itemToBeReturned, reasons));
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(items.size()+" items returned");


    }

    @GetMapping("return/items/last30days")
    public ResponseEntity<List<VendorItem>> itemsReturnedInLastMonth() throws ParseException {
        return ResponseEntity.status(HttpStatus.OK).body(vendorService.getItemsReturnedInLastOneMonth());
    }
}
