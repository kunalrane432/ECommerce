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
        Map<Product, Integer> cartItems = getCartItems(session);
        
        // Fetch the product by ID
        Product product = productService.getProductById(productId);
        if (product == null) {
            // Handle the case where the product is not found
            throw new IllegalArgumentException("Product not found");
        }
       
        
        if (cartItems.containsKey(product)) {
            
            cartItems.put(product, cartItems.get(product) + quantity);
        } else {

            cartItems.put(product, quantity);
        }
        
        // Save the updated cart in the session
        session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
    }

    public Map<Product, Integer> getCartItems(HttpSession session) {
        Map<Product, Integer> cartItems = (Map<Product, Integer>) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cartItems == null) {
            cartItems = new HashMap<>();
            session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
        }
        return cartItems;
    }

    public void removeFromCart(Long productId, HttpSession session) {
        Map<Product, Integer> cartItems = getCartItems(session);
        Product product = productService.getProductById(productId);
        if (product != null) {
            cartItems.remove(product);
            session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
        }
    }

    public BigDecimal calculateTotalPrice(Map<Product, Integer> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            BigDecimal price = entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            totalPrice = totalPrice.add(price);
        }
        return totalPrice;
    }
}
