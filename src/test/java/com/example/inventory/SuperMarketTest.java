package com.example.inventory;

import com.example.inventory.Item.Item;
import com.example.inventory.Item.ItemController;
import com.example.inventory.Item.ItemRepository;
import com.example.inventory.Item.ItemService;
import com.example.inventory.SuperMarket.SuperMarketItem;
import com.example.inventory.SuperMarket.SuperMarketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ItemService.class, ItemController.class,ItemRepository.class})
public class SuperMarketTest {

    @MockBean
    @Autowired
    private SuperMarketRepository superMarketRepository;

    @MockBean
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void testGetItemFromSuperMarket() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        SuperMarketItem superMarketItem = new SuperMarketItem(item,"test_remarks");
        String items = "{\"id\": \"1\"}";


        when(superMarketRepository.findSuperMarketItemBySpItemId(1L)).thenReturn(superMarketItem);

        MediaType jsonPlainUTF8 = new MediaType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.get("/supermarket/get")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(jsonPlainUTF8));
    }
    @Test
    public void testAddSuperMarketItem() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        when(itemRepository.findItemByItemId(1L)).thenReturn(item);
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String content = "{\"id\": \"1\", \"remarks\" : \"test_faults\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/supermarket/add")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }


   @Test
    public void testAddMultipleSuperMarketItems() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        when(itemRepository.findItemByItemId(1L)).thenReturn(item);
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String content = "[{\"id\": \"1\", \"remarks\" : \"test_faults\"}," +
                "{\"id\": \"1\", \"remarks\" : \"test_faults\"}]";
        mockMvc.perform(MockMvcRequestBuilders.post("/supermarket/add/multiple")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }



    @Test
    public void testRemoveSuperMarketItem() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        SuperMarketItem superMarketItem = new SuperMarketItem(item,"test_remarks");
        String content = "{\"id\": \"1\"}";

        when(superMarketRepository.findSuperMarketItemBySpItemId(1L)).thenReturn(superMarketItem);
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        mockMvc.perform(MockMvcRequestBuilders.post("/supermarket/remove")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testRemoveMultipleSuperMarketItem() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        SuperMarketItem superMarketItem = new SuperMarketItem(item,"test_remarks");
        String content = "[{\"id\": \"1\"},{\"id\": \"1\"}]";
        when(superMarketRepository.findSuperMarketItemBySpItemId(1L)).thenReturn(superMarketItem);
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        mockMvc.perform(MockMvcRequestBuilders.post("/supermarket/remove/multiple")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testGetSuperMarketItemsAddedToday() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        SuperMarketItem superMarketItem = new SuperMarketItem(item,"test_remarks");
        List<SuperMarketItem> itemList = List.of(
                new SuperMarketItem(item,"remark"),
                new SuperMarketItem(item,"remark2"),
                new SuperMarketItem(item,"remark3")
        );

        when(superMarketRepository.findSuperMarketItemByDateAdded(new Date())).thenReturn(itemList);

        MediaType jsonPlainUTF8 = new MediaType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.get("/supermarket/today")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(jsonPlainUTF8));
    }

}


