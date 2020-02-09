package com.mainacad.controller.jsp;

import com.mainacad.model.Item;
import com.mainacad.model.User;
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
@RequestMapping("user")
public class UserJspController {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @GetMapping("registration")
    public String save(Model model) {
        return "registration";
    }

    @PutMapping()
    public String save(Model model,
                              @RequestParam(value="login") String login,
                              @RequestParam(value="pass") String password,
                              @RequestParam(value="firstName") String firstName,
                              @RequestParam(value="lastName") String lastName) {


        User user = userService.save(new User(login, password, firstName, lastName));
        if (user != null){
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            return "user-cabinet";
        } else {
            model.addAttribute("message", "Sorry, but this login exists");
            return "registration";
        }
    }

    @PostMapping("auth")
    public String getAuthUser(Model model,
                              @RequestParam(value="login") String login,
                              @RequestParam(value="pass") String password) {

        User user = userService.getByLoginAndPassword(login, password);
        if (user != null){
            model.addAttribute("user", user);

            List<Item> items = itemService.getAllAvailable();
            model.addAttribute("itemCollection", items);
            return "user-cabinet";
        } else {
            model.addAttribute("message", "Login or password are wrong!");
            return "wrong-login";
        }
    }

}
