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
                              @RequestParam(value="itemId") String itemId,
                              @RequestParam(value="userId") String userId) {
        Integer userIdSelected = Integer.valueOf(userId);
        User user = userService.getById(userIdSelected);
        if (user != null){
            // TODO
            return null;
        }

        Integer itemIdSelected = Integer.valueOf(itemId);
        Item item = itemService.getById(itemIdSelected);
        if (item != null) {
            Cart cart = cartService.addItem(userIdSelected, itemIdSelected);
            model.addAttribute("cart", cart);

            List<Item> items = itemService.getAllAvailable();
            model.addAttribute("itemCollection", items);

            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());

            // TODO
            return "user-cabinet";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());

            // TODO
            return "user-cabinet";
//            dispatcher = req.getRequestDispatcher("/jsp/wrong-object-for-user.jsp");
//            req.setAttribute("errorMsg", "Item doesnot exist!");
        }




//
//        User user = userService.getByLoginAndPassword(login, password);
//        if (user != null){
//            model.addAttribute("userId", user.getId());
//            model.addAttribute("firstName", user.getFirstName());
//            model.addAttribute("lastName", user.getLastName());
//
//            List<Item> items = itemService.getAllAvailable();
//            model.addAttribute("itemCollection", items);
//            return "user-cabinet";
//        } else {
//            model.addAttribute("message", "Login or password are wrong!");
//            return "wrong-login";
//        }
    }

}
