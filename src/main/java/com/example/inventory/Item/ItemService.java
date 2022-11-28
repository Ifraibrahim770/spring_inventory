package com.example.inventory.Item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


    public void saveItemToStore(Item item){
        itemRepository.save(item);

    }

    public Item getItem(Long id){
        return itemRepository.findItemByItemId(id);
    }

    public void updateStoreItem(Item item){
        itemRepository.updateItem(item.getName(),item.getQuantity(),item.getUnitPrice(),item.getItemId());

    }

    public void deleteStoreItem(Item item){
        itemRepository.delete(item);
    }

    public List<Item> retrieveAllItems(){
        return new ArrayList<>(itemRepository.findAll());
    }

    public List<Item> retrieveAllItemsCreatedToday() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String outformat = formatter.format(date.getTime());
        return new ArrayList<>(itemRepository.findItemByDateAdded(formatter.parse(outformat)));
    }

    public List<Item> retrieveAllItemsCreatedLastOneWeek() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date oneMonthAgo = cal.getTime();

        String todayFormat = formatter.format(today.getTime());
        String  oneMonthAgoFormat = formatter.format(oneMonthAgo.getTime());
        return new ArrayList<>(itemRepository.getItemsAddedInLastOneWeek(
                formatter.parse(oneMonthAgoFormat),
                formatter.parse(todayFormat)
        ));
    }
}
