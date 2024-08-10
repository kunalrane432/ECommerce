package com.humber.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humber.client.model.Product;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private static final String CART_SESSION_ATTRIBUTE = "cartItems";
    
    @Autowired
    private ProductService productService;

    public void addToCart(Long productId, Integer quantity, HttpSession session) {
        Map<Long, Integer> cartItems = getCartItems(session);
        cartItems.put(productId, cartItems.getOrDefault(productId, 0) + quantity);
        session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
    }

    public Map<Long, Integer> getCartItems(HttpSession session) {
        Map<Long, Integer> cartItems = (Map<Long, Integer>) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cartItems == null) {
            cartItems = new HashMap<>();
            session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
        }
        return cartItems;
    }

    public void removeFromCart(Long productId, HttpSession session) {
        Map<Long, Integer> cartItems = getCartItems(session);
        cartItems.remove(productId);
        session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
    }

    public BigDecimal calculateTotalPrice(Map<Long, Integer> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            Product product = findProductById(entry.getKey()); // Replace with actual product fetching logic
            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            totalPrice = totalPrice.add(price);
        }
        return totalPrice;
    }

    private Map<Product, Integer> convertToProductMap(Map<Long, Integer> cartItems) {
        Map<Product, Integer> productCartItems = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            Product product = findProductById(entry.getKey()); // Replace with actual product fetching logic
            productCartItems.put(product, entry.getValue());
        }
        return productCartItems;
    }

    private Product findProductById(Long productId) {
        return productService.getProductById(productId);
    }
}

