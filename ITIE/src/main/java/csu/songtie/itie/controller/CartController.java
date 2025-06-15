package csu.songtie.itie.controller;

import csu.songtie.itie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CartService cartService;


}
