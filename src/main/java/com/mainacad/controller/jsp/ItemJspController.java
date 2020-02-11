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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Profile("jsp")
@RequestMapping("item")
public class ItemJspController {
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @PostMapping("add-item-in-cart")
    public String addItemInCart(Model model,
                                @RequestParam(value = "itemId") String itemId,
                                @RequestParam(value = "userId") String userId) {
        Integer userIdSelected = Integer.valueOf(userId);
        User user = userService.getById(userIdSelected);
        if (user == null) {
            model.addAttribute("message", "User is wrong! Relogin, pls");
            return "authorization";
        }

        Integer itemIdSelected = Integer.valueOf(itemId);
        Item item = itemService.getById(itemIdSelected);
        if (item == null) {
            model.addAttribute("user", user);
            List<Item> items = itemService.getAllAvailable();
            model.addAttribute("itemCollection", items);
            model.addAttribute("message", "Item is wrong!");
            return "user-cabinet";
        }

        Cart cart = cartService.addItem(userIdSelected, itemIdSelected);

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        List<Item> items = itemService.getAllAvailable();
        model.addAttribute("itemCollection", items);
        return "user-cabinet";
    }
}

