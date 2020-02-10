package com.mainacad.controller.jsp;

import com.mainacad.dao.dto.OrderDTO;
import com.mainacad.model.Cart;
import com.mainacad.model.User;
import com.mainacad.service.CartService;
import com.mainacad.service.OrderService;
import com.mainacad.service.UserService;
import com.mainacad.util.MapperOrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Profile("jsp")
@RequestMapping("order")
public class OrderJspController {
    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    MapperOrderUtil mapperOrderUtil;

    @GetMapping("items-by-cart")
    public String getAllDTOByCard(Model model,
                                          @RequestParam(value = "userId") String userId,
                                          @RequestParam(value = "cartId") String cartId) {
        Integer userIdSelected = Integer.valueOf(userId);
        User user = userService.getById(userIdSelected);
        if (user == null) {
            // TODO
            return null;
        }

        Integer cartIdSelected = Integer.valueOf(cartId);
        Cart cart = cartService.getById(cartIdSelected);
        if (cart != null) {
            model.addAttribute("cart", cart);
            model.addAttribute("user", user);
            List<OrderDTO> orderDTOS = mapperOrderUtil.toOrderDTOListFromOrderList(orderService.getAllByCart(cartIdSelected));
            model.addAttribute("orderDTOCollection", orderDTOS);

            return "cart";
        } else {
            // TODO
            return null;
        }
    }
}
