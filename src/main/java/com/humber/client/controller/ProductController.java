package com.humber.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.humber.client.model.Product;
import com.humber.client.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

 @Autowired
 private ProductService productService;

 @GetMapping("/")
 public String index(Model model) {
     List<Product> products = productService.getAllProducts();
     model.addAttribute("products", products);
     return "index";
 }

 @GetMapping("/product/{id}")
 public String productDetail(@PathVariable Long id, Model model) {
     Product product = productService.getProductById(id);
     model.addAttribute("product", product);
     return "product";
 }
}

