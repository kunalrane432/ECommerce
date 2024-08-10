package com.humber.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.humber.client.model.Order;
import com.humber.client.model.OrderItem;
import com.humber.client.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class OrderController {

 @Autowired
 private OrderService orderService;

 @PostMapping("/order")
 public String placeOrder(Model model, List<OrderItem> items) {
     Order order = new Order();
     order.setOrderItems(items);
     order.setTotalPrice(calculateTotalPrice(items));
     Order createdOrder = orderService.createOrder(order);
     model.addAttribute("order", createdOrder);
     return "order";
 }

 private BigDecimal calculateTotalPrice(List<OrderItem> items) {
     return BigDecimal.ZERO;
 }
}

