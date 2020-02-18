package com.mainacad.dao;

import com.mainacad.AppRunner;
import com.mainacad.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppRunner.class)
class CartDAOTest {
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
    void getAllByUserAndPeriod() {
        User user1 = new User("testLogin1", "testPassword1", "testName1", "testSurname1");
        User user2 = new User("testLogin2", "testPassword2", "testName2", "testSurname2");
        User savedUser1 = userDAO.saveAndFlush(user1);
        User savedUser2 = userDAO.saveAndFlush(user2);
        users.add(savedUser1);
        users.add(savedUser2);

        Cart cart1 = new Cart(Status.OPEN, savedUser1, CURRENT_TIME);
        Cart cart2 = new Cart(Status.OPEN, savedUser2, CURRENT_TIME);
        Cart savedCart1 = cartDAO.saveAndFlush(cart1);
        Cart savedCart2 = cartDAO.saveAndFlush(cart2);
        carts.add(savedCart1);
        carts.add(savedCart2);

        List<Cart> foundCarts = cartDAO.getAllByUserAndPeriod(savedUser1.getId(), CURRENT_TIME, CURRENT_TIME);
        assertNotNull(foundCarts);
        assertTrue(!foundCarts.isEmpty());
        assertTrue(foundCarts.size() == 1);
        assertEquals(foundCarts.get(0).getUser().getFirstName(), savedCart1.getUser().getFirstName());
    }

    @Test
    void getAllByUser() {
        User user1 = new User("testLogin1", "testPassword1", "testName1", "testSurname1");
        User user2 = new User("testLogin2", "testPassword2", "testName2", "testSurname2");
        User savedUser1 = userDAO.saveAndFlush(user1);
        User savedUser2 = userDAO.saveAndFlush(user2);
        users.add(savedUser1);
        users.add(savedUser2);

        Cart cart1 = new Cart(Status.OPEN, savedUser1, CURRENT_TIME);
        Cart cart2 = new Cart(Status.OPEN, savedUser2, CURRENT_TIME);
        Cart savedCart1 = cartDAO.saveAndFlush(cart1);
        Cart savedCart2 = cartDAO.saveAndFlush(cart2);
        carts.add(savedCart1);
        carts.add(savedCart2);

        List<Cart> foundCarts = cartDAO.getAllByUser(savedUser1.getId());
        assertNotNull(foundCarts);
        assertTrue(!foundCarts.isEmpty());
        assertTrue(foundCarts.size() == 1);
        assertEquals(foundCarts.get(0).getUser().getFirstName(), savedCart1.getUser().getFirstName());
    }

    @Test
    void getByUserAndOpenStatus() {
        User user1 = new User("testLogin1", "testPassword1", "testName1", "testSurname1");
        User user2 = new User("testLogin2", "testPassword2", "testName2", "testSurname2");
        User savedUser1 = userDAO.saveAndFlush(user1);
        User savedUser2 = userDAO.saveAndFlush(user2);
        users.add(savedUser1);
        users.add(savedUser2);

        Cart cart1 = new Cart(Status.OPEN, savedUser1, CURRENT_TIME);
        Cart cart2 = new Cart(Status.OPEN, savedUser2, CURRENT_TIME);
        Cart cart3 = new Cart(Status.CLOSED, savedUser1, CURRENT_TIME);
        Cart savedCart1 = cartDAO.saveAndFlush(cart1);
        Cart savedCart2 = cartDAO.saveAndFlush(cart2);
        Cart savedCart3 = cartDAO.saveAndFlush(cart2);
        carts.add(savedCart1);
        carts.add(savedCart2);
        carts.add(savedCart3);

        List<Cart> foundCarts = cartDAO.getByUserAndOpenStatus(savedUser1.getId());
        assertNotNull(foundCarts);
        assertTrue(!foundCarts.isEmpty());
        assertTrue(foundCarts.size() == 1);
        assertEquals(foundCarts.get(0).getUser().getFirstName(), savedCart1.getUser().getFirstName());
    }

    @Test
    void updateStatus() {
        User user1 = new User("testLogin1", "testPassword1", "testName1", "testSurname1");
        User savedUser1 = userDAO.saveAndFlush(user1);
        users.add(savedUser1);

        Cart cart1 = new Cart(Status.OPEN, user1, CURRENT_TIME);
        Cart savedCart1 = cartDAO.saveAndFlush(cart1);
        carts.add(savedCart1);

        int updatedCartCount = cartDAO.updateStatus(savedCart1.getId(), Status.CLOSED.ordinal());
        assertEquals(updatedCartCount, 1);
    }
}