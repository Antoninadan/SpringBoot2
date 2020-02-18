package com.mainacad.dao;

import com.mainacad.AppRunner;
import com.mainacad.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppRunner.class)
class OrderDAOTest {
    private static final Long CURRENT_TIME = new Date().getTime();
    private static List<User> users;
    private static List<Item> items;
    private static List<Cart> carts;
    private static List<Order> orders;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ItemDAO itemDAO;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        items = new ArrayList<>();
        carts = new ArrayList<>();
        orders = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        orders.forEach(it -> orderDAO.delete(it));
        carts.forEach(it -> cartDAO.delete(it));
        users.forEach(it -> userDAO.delete(it));
        items.forEach(it -> itemDAO.delete(it));
    }

    @Test
    void getAllByCart() {
        User user1 = new User("testLogin1", "testPassword1", "testName1", "testSurname1");
        User user2 = new User("testLogin2", "testPassword2", "testName2", "testSurname2");
        User savedUser1 = userDAO.saveAndFlush(user1);
        User savedUser2 = userDAO.saveAndFlush(user2);
        users.add(savedUser1);
        users.add(savedUser2);

        Cart cart1 = new Cart(Status.OPEN, user1, CURRENT_TIME);
        Cart cart2 = new Cart(Status.OPEN, user2, CURRENT_TIME);
        Cart savedCart1 = cartDAO.saveAndFlush(cart1);
        Cart savedCart2 = cartDAO.saveAndFlush(cart2);
        carts.add(savedCart1);
        carts.add(savedCart2);

        Item item1 = new Item("name_1", "code_1", 10, 1);
        Item item2 = new Item("name_2", "code_2", 20, 2);
        Item savedItem1 = itemDAO.saveAndFlush(item1);
        Item savedItem2 = itemDAO.saveAndFlush(item2);
        items.add(savedItem1);
        items.add(savedItem2);

        Order order1 = new Order(savedItem1, savedCart1, 50);
        Order order2 = new Order(savedItem1, savedCart2, 50);
        Order savedOrder1 = orderDAO.saveAndFlush(order1);
        Order savedOrder2 = orderDAO.saveAndFlush(order2);
        orders.add(savedOrder1);
        orders.add(savedOrder2);

        List<Order> foundOrders = orderDAO.getAllByCart(savedCart1.getId());
        assertNotNull(foundOrders);
        assertTrue(!foundOrders.isEmpty());
        assertTrue(foundOrders.size() == 1);
        assertEquals(foundOrders.get(0).getId(), savedOrder1.getId());
    }

    @Test
    void updateAmount() {
        User user1 = new User("testLogin1", "testPassword1", "testName1", "testSurname1");
        User savedUser1 = userDAO.saveAndFlush(user1);
        users.add(savedUser1);

        Cart cart1 = new Cart(Status.OPEN, user1, CURRENT_TIME);
        Cart savedCart1 = cartDAO.saveAndFlush(cart1);
        carts.add(savedCart1);

        Item item1 = new Item("name_1", "code_1", 10, 1);
        Item savedItem1 = itemDAO.saveAndFlush(item1);
        items.add(savedItem1);

        Order order1 = new Order(savedItem1, savedCart1, 50);
        Order savedOrder1 = orderDAO.saveAndFlush(order1);
        orders.add(savedOrder1);

        int updatedOrderCount = orderDAO.updateAmount(savedOrder1.getId(), 100);
        assertEquals(updatedOrderCount, 1);
    }
}