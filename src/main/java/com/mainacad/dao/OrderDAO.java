package com.mainacad.dao;

import com.mainacad.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM orders o WHERE o.cart_id= :cartId")
    List<Order> getAllByCart(@Param("cartId") Integer cartId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE orders SET amount = :amount WHERE id = :orderId")
    int updateAmount(@Param("orderId") Integer orderId, @Param("amount") Integer amount);
}


