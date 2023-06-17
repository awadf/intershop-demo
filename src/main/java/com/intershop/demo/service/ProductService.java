package com.intershop.demo.service;

import com.intershop.demo.entity.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    Product getProductById(long productId);
}
