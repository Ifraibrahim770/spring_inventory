package com.example.inventory.Vendor;


import com.example.inventory.SuperMarket.SuperMarketItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;
    // Returns items to vendor
    public void returnItemToVendor(VendorItem vendorItem){
        vendorRepository.save(vendorItem);
    }

    public List<VendorItem> getItemsReturnedInLastOneMonth() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -30);
        Date oneMonthAgo = cal.getTime();

        String todayFormat = formatter.format(today.getTime());
        String  oneMonthAgoFormat = formatter.format(oneMonthAgo.getTime());

        return new ArrayList<>(vendorRepository.getItemsAddedInLastOneMonth(
                formatter.parse(oneMonthAgoFormat),
                formatter.parse(todayFormat)
        ));







    }
}
