package com.humber.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.humber.client.service.CartService;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public String addToCart(@RequestParam Long productId, @RequestParam Integer quantity, HttpSession session) {
        cartService.addToCart(productId, quantity, session);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
    	Map<Long, Integer> cartItems = cartService.getCartItems(session);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(cartItems));
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        cartService.removeFromCart(productId, session);
        return "redirect:/cart";
    }
}

