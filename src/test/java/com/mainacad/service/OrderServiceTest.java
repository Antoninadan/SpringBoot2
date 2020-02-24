package com.mainacad.service;

import com.mainacad.AppRunner;
import com.mainacad.model.*;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppRunner.class)
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    void saveToMongo() {

        User user = new User("testLogin", "testPassword", "testName", "testSurname");
        Item item = new Item();
        item.setName("test name");
        item.setCode("test code");
        item.setPrice(12345);

        Cart cart = new Cart(Status.OPEN, user , 1213234L );
        Order order = new Order(item, cart, 122);
        orderService.saveToMongo(order);
    }
}