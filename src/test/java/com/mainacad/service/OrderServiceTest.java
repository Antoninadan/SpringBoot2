package com.mainacad.service;

import com.mainacad.AppRunner;
import com.mainacad.dao.CartDAO;
import com.mainacad.dao.ItemDAO;
import com.mainacad.dao.OrderDAO;
import com.mainacad.dao.UserDAO;
import com.mainacad.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(AppRunner.class)
class OrderServiceTest {
    private static final Long CURRENT_TIME = new Date().getTime();

    @MockBean
    UserDAO userDAO;

    @MockBean
    ItemDAO itemDAO;

    @MockBean
    CartDAO cartDAO;

    @MockBean
    OrderDAO orderDAO;

    @Autowired
    OrderService orderService;

    @Test
    void getByCartWithItem() {
        User user1 = new User(1, "testLogin1", "testPassword1", "testName1", "testSurname1");
        User user2 = new User(2, "testLogin2", "testPassword2", "testName2", "testSurname2");
        when(userDAO.saveAndFlush(user1)).thenReturn(user1);
        when(userDAO.saveAndFlush(user2)).thenReturn(user2);

        Cart cart1 = new Cart(1, Status.OPEN, user1, CURRENT_TIME);
        Cart cart2 = new Cart(2, Status.OPEN, user2, CURRENT_TIME);
        when(cartDAO.saveAndFlush(cart1)).thenReturn(cart1);
        when(cartDAO.saveAndFlush(cart2)).thenReturn(cart2);

        Item item1 = new Item(1, "name_1", "code_1", 10, 1);
        Item item2 = new Item(2, "name_2", "code_2", 20, 2);
        when(itemDAO.saveAndFlush(item1)).thenReturn(item1);
        when(itemDAO.saveAndFlush(item2)).thenReturn(item2);

        Order order1 = new Order(1, item1, cart1, 50);
        Order order2 = new Order(2, item1, cart2, 50);
        when(orderDAO.saveAndFlush(order1)).thenReturn(order1);
        when(orderDAO.saveAndFlush(order2)).thenReturn(order2);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        when(orderDAO.getAllByCart(cart1.getId())).thenReturn(orders);

        Order foundOrder = orderService.getByCartWithItem(cart1.getId(), item1.getId());
        assertNotNull(foundOrder);
        assertEquals(foundOrder.getId(), order1.getId());

//        verify(userDAO, times(1)).saveAndFlush(user1);
//        verify(userDAO, times(1)).saveAndFlush(user2);
//        verify(cartDAO, times(1)).saveAndFlush(cart1);
//        verify(cartDAO, times(1)).saveAndFlush(cart2);
//        verify(itemDAO, times(1)).saveAndFlush(item1);
//        verify(itemDAO, times(1)).saveAndFlush(item2);
//        verify(orderDAO, times(1)).saveAndFlush(order1);
//        verify(orderDAO, times(1)).saveAndFlush(order2);
    }
}