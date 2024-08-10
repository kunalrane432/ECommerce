package com.humber.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.humber.client.model.Order;
import com.humber.client.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService; 

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        try {
            orderService.createOrder(order);
            return ResponseEntity.ok("Order placed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to place order: " + e.getMessage());
        }
    }
}



