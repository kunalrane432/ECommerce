package com.humber.client.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.humber.client.model.Order;

@Service
public class OrderService {

 @Value("${order.service.url}")
 private String orderServiceUrl;

 private final RestTemplate restTemplate = new RestTemplate();

 public Order createOrder(Order order) {
     return restTemplate.postForObject(orderServiceUrl, order, Order.class);
 }
}

