package com.humber.client.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.humber.client.model.Product;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

 @Value("${product.service.url}")
 private String productServiceUrl;

 private final RestTemplate restTemplate = new RestTemplate();

 public List<Product> getAllProducts() {
	 System.out.println(productServiceUrl+"productServiceUrl");
     return Arrays.asList(restTemplate.getForObject(productServiceUrl, Product[].class));
 }

 public Product getProductById(Long id) {
     return restTemplate.getForObject(productServiceUrl + "/" + id, Product.class);
 }
}

