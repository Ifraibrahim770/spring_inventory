package com.example.inventory;

import com.example.inventory.Item.Item;
import com.example.inventory.Item.ItemController;
import com.example.inventory.Item.ItemRepository;
import com.example.inventory.Item.ItemService;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
public class StoreTest {

    @MockBean
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    ItemController itemController;

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void testAddItem() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String items = "{\"name\": \"Colgate\", \"quantity\" : \"3\", \"unitPrice\":\"3\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/items/add")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testAddItemList() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String items = "[{\"name\": \"Colgate\", \"quantity\" : \"3\", \"unitPrice\":\"3\"}," +
                "{\"name\": \"Toothpaste\", \"quantity\" : \"4\", \"unitPrice\":\"3\"}]";
        mockMvc.perform(MockMvcRequestBuilders.post("/items/add/multiple")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testEditItem() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        when(itemRepository.findItemByItemId(1L)).thenReturn(item);

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String items = "{\"itemId\": \"1\", \"quantity\" : \"3\", \"name\" : \"Nameeeee\",\"unitPrice\":\"3\"}" ;
        mockMvc.perform(MockMvcRequestBuilders.post("/items/edit")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testEditMultipleItems() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        when(itemRepository.findItemByItemId(1L)).thenReturn(item);

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String items = "[{\"itemId\": \"1\", \"quantity\" : \"3\", \"name\" : \"Nameeeee\",\"unitPrice\":\"3\"}," +
                "{\"itemId\": \"1\", \"quantity\" : \"3\", \"name\" : \"Nameeeee\",\"unitPrice\":\"3\"}]" ;
        mockMvc.perform(MockMvcRequestBuilders.post("/items/edit/multiple")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testDeleteItems() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        when(itemRepository.findItemByItemId(1L)).thenReturn(item);

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String items = "{\"id\": \"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/items/delete")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testDeleteMultipleItems() throws Exception {
        Item item = new Item("name",BigDecimal.valueOf(20),23);
        when(itemRepository.findItemByItemId(1L)).thenReturn(item);

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String items = "[{\"id\": \"1\"},{\"id\": \"1\"}]";
        mockMvc.perform(MockMvcRequestBuilders.post("/items/delete/multiple")
                        .content(items)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void testGetItemsAddedToday() throws Exception {
        Date date = new Date();
        List<Item> itemList = List.of(
                new Item("name",BigDecimal.valueOf(20),23),
                new Item("name2",BigDecimal.valueOf(20),23),
                new Item("name3",BigDecimal.valueOf(20),23)

        );
        when(itemRepository.findItemByDateAdded(date)).thenReturn(itemList);
        MediaType jsonPlainUTF8 = new MediaType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.get("/items/today")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(jsonPlainUTF8));
    }
    @Test
    public void testGetItemsAddedLastOneWeek() throws Exception {
        Date date = new Date();
        List<Item> itemList = List.of(
                new Item("name",BigDecimal.valueOf(20),23),
                new Item("name2",BigDecimal.valueOf(20),23),
                new Item("name3",BigDecimal.valueOf(20),23)

        );
        when(itemRepository.getItemsAddedInLastOneWeek(date,date)).thenReturn(itemList);
        MediaType jsonPlainUTF8 = new MediaType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.get("/items/last/week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(jsonPlainUTF8));
    }


    }

