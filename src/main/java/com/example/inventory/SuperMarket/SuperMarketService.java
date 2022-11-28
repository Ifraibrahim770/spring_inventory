package com.example.inventory.SuperMarket;


import com.example.inventory.Item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.SignatureProperties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SuperMarketService {
    @Autowired
    private SuperMarketRepository superMarketRepository;

    public void saveItemToSupermarket(Item item, String remark){
      superMarketRepository.save(new SuperMarketItem(item,remark));
    }

    public SuperMarketItem getSuperMarketItem(Long id){
        return superMarketRepository.findSuperMarketItemBySpItemId(id);
    }

    public void removeItemFromSuperMarket(SuperMarketItem item){
        superMarketRepository.delete(item);
    }

    public List<SuperMarketItem> getItemsAddedToday() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String outFormat = formatter.format(date.getTime());
        return new ArrayList<>(superMarketRepository.findSuperMarketItemByDateAdded(formatter.parse(outFormat)));

    }

}
