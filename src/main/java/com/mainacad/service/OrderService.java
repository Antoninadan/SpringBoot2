package com.mainacad.service;

import com.mainacad.dao.CartDAO;
import com.mainacad.dao.OrderDAO;
import com.mainacad.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;


    public Order getById(Integer id) {
        Optional<Order> order = orderDAO.findById(id);
        if (order.isEmpty()) {
            return null;
        }
        return order.get();
    }

    public List<Order> getAllByCart(Integer cartId) {
        return orderDAO.getAllByCart(cartId);
    }

    public int updateAmount(Integer orderId, Integer amount) {
        return orderDAO.updateAmount(orderId, amount);
    }

    public Order getByCartWithItem(Integer cartId, Integer itemId) {
        List<Order> orders = getAllByCart(cartId);

        if (orders.isEmpty()) {
            return null;
        }
        for (Order each : orders) {
            if (each.getItem().getId().equals(itemId)) {
                return each;
            }
        }
        return null;
    }

    public Order addItemInCart(Integer userId, Integer itemId) {
        User user = userService.getById(userId);
        if(user == null) {
            return null;
        }

        Item item = itemService.getById(itemId);
        if(item == null) {
            return null;
        }

        Order order;
        List<Cart> carts = cartDAO.getByUserAndOpenStatus(userId);
        Cart openCart;
        if (carts.size() == 0) {
            Long currentTime = new Date().getTime();
            openCart = new Cart(Status.OPEN, user, currentTime);
            cartService.save(openCart);
            order = new Order(item, openCart, 1);
            save(order);
        } else {
            openCart = carts.get(0);
            Order targetOrder = getByCartWithItem(openCart.getId(), itemId);
            if (targetOrder == null) {
                order = new Order(item, openCart, 1);
                save(order);
            } else {
                order = targetOrder;
                updateAmount(targetOrder.getId(),
                        getById(targetOrder.getId()).getAmount() + 1);
            }
        }
        return order;
    }

    // CRUD
    public Order save(Order order) {
        if (order.getId() == null) {
            return orderDAO.save(order);
        }
        return null;
    }

    public Order update(Order order) {
        if (order.getId() != null && orderDAO.getOne(order.getId()) != null) {
            return orderDAO.save(order);
        }
        return null;
    }

    public List<Order> getAll() {
        return orderDAO.findAll();
    }

    public void delete(Order order) {
        orderDAO.delete(order);
    }

    public void deleteById(Integer id) throws RuntimeException {
        orderDAO.deleteById(id);
    }
}
