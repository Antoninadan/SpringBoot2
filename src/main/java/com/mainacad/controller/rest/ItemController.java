package com.mainacad.controller.rest;

import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Status;
import com.mainacad.model.User;
import com.mainacad.service.CartService;
import com.mainacad.service.ItemService;
import com.mainacad.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@Controller
//@Scope(value = "session")
@RestController
@RequestMapping("item")
@Slf4j
@Profile("rest")
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @PutMapping
    public ResponseEntity save(@RequestBody Item item) {
        Item savedItem = itemService.save(item);
        if (savedItem == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity update(@RequestBody Item item) {
        Item updatedItem = itemService.update(item);
        if (updatedItem == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(item, HttpStatus.OK);
    }

    @GetMapping({"", "{id}"})
    public ResponseEntity getItem(@PathVariable(required = false) Integer id) {
        if (id != null) {
            Item item = itemService.getById(id);
            if (item == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(item, HttpStatus.OK);
        } else {
            return new ResponseEntity(itemService.getAll(), HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody Item item) {
        try {
            itemService.delete(item);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Bad item params");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) {
        try {
            itemService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Wrong id = %d", id));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("by-cart/{cartId}")
    public ResponseEntity getAllByCart(@PathVariable Integer cartId) {
        return new ResponseEntity(itemService.getAllByCart(cartId), HttpStatus.OK);
    }

    @GetMapping("available")
    public ResponseEntity getAllAvailable() {
        return new ResponseEntity(itemService.getAllAvailable(), HttpStatus.OK);
    }

    // TODO
    @PostMapping("add-item-in-cart")
    public ResponseEntity addItemInCart(@RequestBody String body) {
        Map<String, Object> map = new JacksonJsonParser().parseMap(body);
        Integer itemId = (Integer) map.get("itemId");
        Integer userId = (Integer) map.get("userId");

        User user = userService.getById(userId);
        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Item item = itemService.getById(itemId);
        if (item == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Cart cart = cartService.addItem(userId, itemId);

        return new ResponseEntity(cart.getId(), HttpStatus.OK);
    }
}
