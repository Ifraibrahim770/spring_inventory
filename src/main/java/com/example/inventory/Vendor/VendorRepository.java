package com.example.inventory.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<VendorItem, Long> {

    @Query("select a from VendorItem a where a.dateAdded  between ?1 and ?2")
    List<VendorItem> getItemsAddedInLastOneMonth(Date startDate, Date endDate);

}
