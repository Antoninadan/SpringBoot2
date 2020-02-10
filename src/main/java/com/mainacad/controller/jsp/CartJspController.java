package com.mainacad.controller.jsp;

import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.User;
import com.mainacad.service.CartService;
import com.mainacad.service.ItemService;
import com.mainacad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Profile("jsp")
@RequestMapping("cart")
public class CartJspController {
    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @GetMapping("all-by-user")
    public String getAllByUser(Model model,
                               @RequestParam(value = "userId") String userId) {
        Integer userIdSelected = Integer.valueOf(userId);
        User user = userService.getById(userIdSelected);
        if (user != null) {
            List<Cart> carts = cartService.getAllByUser(userIdSelected);
            model.addAttribute("cartCollection", carts);
            model.addAttribute("user", user);
            return "carts";
        } else {
            // TODO
            return null;
        }
    }

    @GetMapping("open")
    public String getAllByUser(Model model,
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

            List<OrderDTO> orderDTOS = OrderService.getAllDTOByCard(cart);
            req.setAttribute("orderDTOCollection", orderDTOS);

            return "carts";
        } else {
            // TODO
            return null;
        }
    }

}
