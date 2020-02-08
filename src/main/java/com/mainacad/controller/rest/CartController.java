package com.mainacad.controller.rest;

import com.mainacad.model.User;
import com.mainacad.model.dto.CartDTO;
import com.mainacad.service.CartService;
import com.mainacad.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@Slf4j
@Profile("rest")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    MapperUtil mapperUtil;

    @PutMapping
    public ResponseEntity save(@RequestBody String request) {

        cartService.save(mapperUtil.toCart(mapperUtil.toCartDTO(request)));

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping({"", "{id}"})
    public ResponseEntity getCart(@PathVariable(required = false) Integer id) {
        if (id != null) {

            return new ResponseEntity(HttpStatus.OK);
        } else {

        }

        return new ResponseEntity(HttpStatus.OK);
    }


}
